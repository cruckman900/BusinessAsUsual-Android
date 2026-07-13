package work.businessasusual.ui.mobileui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlin.math.max
import work.businessasusual.domain.model.ChartSeries
import work.businessasusual.domain.model.ChartSpec
import work.businessasusual.domain.model.ChartTypes

/** Renders a full analytics screen: a stack of titled chart cards. */
@Composable
fun ChartDashboard(charts: List<ChartSpec>, emptyMessage: String) {
	if (charts.isEmpty()) {
		Text(emptyMessage, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
		return
	}
	Column(
		Modifier.fillMaxWidth().padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		charts.forEach { ChartCard(it) }
	}
}

@Composable
private fun ChartCard(chart: ChartSpec) {
	ElevatedCard(Modifier.fillMaxWidth()) {
		Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
			Text(chart.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
			chart.subtitle?.let {
				Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
			}
			Spacer(Modifier.height(4.dp))
			when (chart.chartType) {
				ChartTypes.LINE -> LineChart(chart)
				ChartTypes.BAR -> BarChart(chart)
				ChartTypes.PIE -> PieChart(chart, donut = false)
				ChartTypes.DONUT -> PieChart(chart, donut = true)
				ChartTypes.SPARKLINE -> Sparkline(chart)
				else -> Text("Unsupported chart type: ${chart.chartType}", style = MaterialTheme.typography.bodySmall)
			}
		}
	}
}

/* ---------------- VICO: line + bar ---------------- */

@Composable
private fun LineChart(chart: ChartSpec) {
	val modelProducer = remember { CartesianChartModelProducer() }
	androidx.compose.runtime.LaunchedEffect(chart.id) {
		modelProducer.runTransaction {
			lineSeries {
				chart.series.forEach { s -> series(s.points.map { it.value }) }
			}
		}
	}
	CartesianChartHost(
		chart = rememberCartesianChart(
			rememberLineCartesianLayer(),
			startAxis = VerticalAxis.rememberStart(),
			bottomAxis = HorizontalAxis.rememberBottom(),
		),
		modelProducer = modelProducer,
		modifier = Modifier.fillMaxWidth().height(200.dp),
	)
	SeriesLegend(chart.series)
}

@Composable
private fun BarChart(chart: ChartSpec) {
	val modelProducer = remember { CartesianChartModelProducer() }
	androidx.compose.runtime.LaunchedEffect(chart.id) {
		modelProducer.runTransaction {
			columnSeries {
				chart.series.forEach { s -> series(s.points.map { it.value }) }
			}
		}
	}
	CartesianChartHost(
		chart = rememberCartesianChart(
			rememberColumnCartesianLayer(),
			startAxis = VerticalAxis.rememberStart(),
			bottomAxis = HorizontalAxis.rememberBottom(),
		),
		modelProducer = modelProducer,
		modifier = Modifier.fillMaxWidth().height(200.dp),
	)
	SeriesLegend(chart.series)
}

/* ---------------- CANVAS: pie / donut / sparkline ---------------- */

@Composable
private fun PieChart(chart: ChartSpec, donut: Boolean) {
	val points = chart.series.firstOrNull()?.points.orEmpty()
	val total = points.sumOf { it.value }.let { if (it <= 0.0) 1.0 else it }
	val fallback = defaultPalette()

	Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
		Canvas(Modifier.size(140.dp).padding(8.dp)) {
			var startAngle = -90f
			val stroke = if (donut) size.minDimension * 0.22f else 0f
			points.forEachIndexed { index, p ->
				val sweep = (p.value / total * 360.0).toFloat()
				val color = parseColor(p.color) ?: fallback[index % fallback.size]
				if (donut) {
					drawArc(
						color = color,
						startAngle = startAngle,
						sweepAngle = sweep,
						useCenter = false,
						topLeft = Offset(stroke / 2, stroke / 2),
						size = Size(size.width - stroke, size.height - stroke),
						style = Stroke(width = stroke),
					)
				} else {
					drawArc(
						color = color,
						startAngle = startAngle,
						sweepAngle = sweep,
						useCenter = true,
						style = Fill,
					)
				}
				startAngle += sweep
			}
		}
		Spacer(Modifier.width(12.dp))
		Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
			points.forEachIndexed { index, p ->
				val color = parseColor(p.color) ?: fallback[index % fallback.size]
				val pct = (p.value / total * 100.0)
				Row(verticalAlignment = Alignment.CenterVertically) {
					Box(Modifier.size(12.dp).clip(CircleShape).background(color))
					Spacer(Modifier.width(8.dp))
					Text(
						"${p.label}  ${"%.0f".format(pct)}%",
						style = MaterialTheme.typography.bodySmall,
					)
				}
			}
		}
	}
}

@Composable
private fun Sparkline(chart: ChartSpec) {
	val series = chart.series.firstOrNull()
	val values = series?.points?.map { it.value }.orEmpty()
	val lineColor = parseColor(series?.color) ?: MaterialTheme.colorScheme.primary
	val fillColor = lineColor.copy(alpha = 0.15f)
	val latest = values.lastOrNull()

	Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
		latest?.let {
			Text("%.0f".format(it), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
		}
		Canvas(Modifier.fillMaxWidth().height(64.dp)) {
			if (values.size < 2) return@Canvas
			val minV = values.min()
			val maxV = values.max()
			val range = max((maxV - minV), 1.0)
			val stepX = size.width / (values.size - 1)
			fun yFor(v: Double) = (size.height * (1f - ((v - minV) / range).toFloat())).coerceIn(0f, size.height)

			val linePath = Path().apply {
				values.forEachIndexed { i, v ->
					val x = stepX * i
					val y = yFor(v)
					if (i == 0) moveTo(x, y) else lineTo(x, y)
				}
			}
			val fillPath = Path().apply {
				addPath(linePath)
				lineTo(size.width, size.height)
				lineTo(0f, size.height)
				close()
			}
			drawPath(fillPath, color = fillColor)
			drawPath(linePath, color = lineColor, style = Stroke(width = 4f))
		}
	}
}

/* ---------------- shared helpers ---------------- */

@Composable
private fun SeriesLegend(series: List<ChartSeries>) {
	if (series.size <= 1) return
	val fallback = defaultPalette()
	Row(
		Modifier.fillMaxWidth().padding(top = 4.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp),
	) {
		series.forEachIndexed { index, s ->
			val color = parseColor(s.color) ?: fallback[index % fallback.size]
			Row(verticalAlignment = Alignment.CenterVertically) {
				Box(Modifier.size(12.dp).clip(CircleShape).background(color))
				Spacer(Modifier.width(6.dp))
				Text(s.name, style = MaterialTheme.typography.bodySmall)
			}
		}
	}
}

@Composable
private fun defaultPalette(): List<Color> = listOf(
	MaterialTheme.colorScheme.primary,
	MaterialTheme.colorScheme.tertiary,
	MaterialTheme.colorScheme.secondary,
	MaterialTheme.colorScheme.error,
	MaterialTheme.colorScheme.primaryContainer,
)

/** Parses "#RRGGBB" / "#AARRGGBB" hex strings; returns null on failure. */
private fun parseColor(hex: String?): Color? {
	if (hex.isNullOrBlank()) return null
	return try {
		val clean = hex.removePrefix("#")
		val value = clean.toLong(16)
		when (clean.length) {
			6 -> Color(0xFF000000 or value)
			8 -> Color(value)
			else -> null
		}
	} catch (_: Exception) {
		null
	}
}
