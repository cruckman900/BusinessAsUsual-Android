package work.businessasusual.data.remote

import work.businessasusual.data.BuildConfig

/**
 * Resolves the ordered list of candidate base URLs for a given backend service.
 *
 * Order is driven by the build type via [BuildConfig.AWS_FIRST]:
 *  - debug   -> LOCAL first, then AWS
 *  - release -> AWS first, then LOCAL
 *
 * The [FailoverInterceptor] walks these candidates at runtime and sticks to the
 * first one that responds, so the app transparently falls back to local services
 * when AWS is unreachable (and vice-versa).
 */
object EndpointConfig {

	/** Candidate base URLs for the HR / mobile-UI API, in preferred order. */
	val hrCandidates: List<String> = orderCandidates(
		aws = BuildConfig.AWS_HR_URL,
		local = BuildConfig.LOCAL_HR_URL,
	)

	/** Candidate base URLs for the ModuleRegistry API, in preferred order. */
	val registryCandidates: List<String> = orderCandidates(
		aws = BuildConfig.AWS_REGISTRY_URL,
		local = BuildConfig.LOCAL_REGISTRY_URL,
	)

	private fun orderCandidates(aws: String, local: String): List<String> {
		val ordered = if (BuildConfig.AWS_FIRST) listOf(aws, local) else listOf(local, aws)
		// Drop unconfigured/placeholder AWS URLs so we never waste a probe on them.
		return ordered
			.map { it.trim() }
			.filter { it.isNotEmpty() && !it.contains("REPLACE_WITH_ALB_DNS") }
			.distinct()
	}
}
