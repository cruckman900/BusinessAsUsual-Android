package work.businessasusual.data.remote

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.atomic.AtomicReference

/**
 * Retrofit/OkHttp interceptor that provides live -> local failover.
 *
 * Retrofit is configured with a placeholder base URL. At request time this
 * interceptor rewrites the host/scheme/port to one of the [candidates] (ordered
 * by build type in [EndpointConfig]). It probes candidates in order until one
 * responds, then "pins" that base for subsequent requests to avoid re-probing.
 *
 * If the pinned base later fails, it transparently re-probes the remaining
 * candidates, so the app self-heals when a backend comes back or goes away.
 */
class FailoverInterceptor(
	private val candidates: List<String>,
) : Interceptor {

	// Last known-good base URL; shared across requests to avoid repeated probing.
	private val pinned = AtomicReference<String?>(null)

	override fun intercept(chain: Interceptor.Chain): Response {
		if (candidates.isEmpty()) return chain.proceed(chain.request())

		// Build a probe order: the pinned base first (if any), then the rest.
		val current = pinned.get()
		val order = buildList {
			current?.let { add(it) }
			addAll(candidates.filter { it != current })
		}

		var lastError: IOException? = null
		for (base in order) {
			val request = chain.request().rewriteTo(base) ?: continue
			try {
				val response = chain.proceed(request)
				pinned.set(base)          // remember the winner
				return response
			} catch (e: IOException) {
				lastError = e            // this base is down; try the next candidate
			}
		}
		// Nothing responded; surface the last transport error.
		throw lastError ?: IOException("No reachable endpoint among: $candidates")
	}

	private fun okhttp3.Request.rewriteTo(base: String): okhttp3.Request? {
		val baseUrl = try {
			base.toHttpUrl()
		} catch (_: IllegalArgumentException) {
			return null
		}
		val newUrl = url.newBuilder()
			.scheme(baseUrl.scheme)
			.host(baseUrl.host)
			.port(baseUrl.port)
			.build()
		return newBuilder().url(newUrl).build()
	}
}
