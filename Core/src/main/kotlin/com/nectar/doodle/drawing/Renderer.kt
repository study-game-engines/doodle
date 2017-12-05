package com.nectar.doodle.drawing

import com.nectar.doodle.geometry.Path
import com.nectar.doodle.geometry.Point
import com.nectar.doodle.geometry.Polygon

/**
 * Created by Nicholas Eddy on 10/23/17.
 */
interface Renderer {
    fun clear()
    fun flush()

    fun line(point1: Point, point2: Point, pen: Pen)

    fun path(points: List<Point>, pen: Pen)

    fun path(path: Path,           brush: Brush,         fillRule: FillRule? = null)
    fun path(path: Path, pen: Pen, brush: Brush? = null, fillRule: FillRule? = null)

    fun poly(polygon: Polygon,           brush: Brush)
    fun poly(polygon: Polygon, pen: Pen, brush: Brush? = null)

    fun arc(center: Point, radius: Double, sweep: Double, rotation: Double,           brush: Brush)
    fun arc(center: Point, radius: Double, sweep: Double, rotation: Double, pen: Pen, brush: Brush? = null)

    enum class Optimization {
        Speed,
        Quality
    }

    enum class FillRule {
        NonZero,
        EvenOdd
    }
}