package com.nectar.doodle.core

import com.nectar.doodle.drawing.Canvas
import com.nectar.doodle.geometry.Point
import com.nectar.doodle.geometry.Size


/**
 * Represents graphical data within the boundaries of
 * a Gizmo.  An example is the icon used by a button to
 * indicate its function.
 *
 * @author Nicholas Eddy
 */

interface Icon<in T: Gizmo> {

    /** The icon's size */
    val size: Size

    /**
     * Renders the icon onto the surface of the given Gizmo,
     * with the icon's top-left at the point indicated.  The top-left
     * location is that of the icon's bounding-box.
     *
     * @param gizmo  The Gizmo this icon is to represent
     * @param canvas The Gizmo's Canvas
     * @param at     Where the top-left corner of the icon should be rendered
     */

    fun render(gizmo: T, canvas: Canvas, at: Point)
}
