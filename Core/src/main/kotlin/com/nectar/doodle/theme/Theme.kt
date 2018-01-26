/**
 * Created by Nicholas Eddy on 1/24/18.
 */
package com.nectar.doodle.theme

import com.nectar.doodle.core.Display
import com.nectar.doodle.core.Gizmo
import com.nectar.doodle.drawing.Canvas
import com.nectar.doodle.utils.BreadthFirstTreeIterator
import com.nectar.doodle.utils.Node
import com.nectar.doodle.utils.ObservableList


/**
 * Themes are able to visually style [Gizmo]s within the [Display].  Installing one will trigger an update and provide the full set of [Gizmo]s
 * to the [Theme.install] method, allowing the theme to update any subset of [Gizmo]s it chooses.
 */
interface Theme {
    /**
     * Called whenever a Theme is set as [ThemeManager.selected].  This allows the theme to update any of the [Gizmo]s present in the [Display].
     *
     * @param display
     * @param all the Gizmos (recursively) within the Display
     */
    fun install(display: Display, all: Sequence<Gizmo>)
}

/**
 * A Renderer can be used by [Gizmo]s and [Theme]s to allow delegation of the [Gizmo.render] call.  This way, a Gizmo can have it's visual style
 * controlled by a Theme.
 */
interface Renderer<in T: Gizmo> {
    /**
     * Invoked to render the given Gizmo
     *
     * @param canvas the Canvas given to the Gizmo during a system call to [Gizmo.render]
     * @param gizmo  the Gizmo being rendered
     */
    fun render(canvas: Canvas, gizmo: T)
}

/**
 * This manager keeps track of available [Theme]s and manages the application of new ones via [ThemeManager.selected].
 */
interface ThemeManager {
    /** Convenient set of [Theme]s that an application can manage */
    val themes  : MutableSet<Theme>

    /** The currently selected [Theme].  Setting this will cause the new Theme to update the [Display] and [Gizmo]s therein. */
    var selected: Theme?
}

abstract class InternalThemeManager: ThemeManager {
    internal abstract fun update(gizmo: Gizmo)
}

class ThemeManagerImpl(private val display: Display): InternalThemeManager() {
    override val themes   = mutableSetOf<Theme>()
    override var selected = null as Theme?
        set(new) {
            field = new

            val iterator = BreadthFirstTreeIterator<Gizmo>(NodeAdapter(DummyRoot(display.children)))
            val sequence = Sequence { iterator }.drop(1)

            field?.install(display, sequence)
        }

    override fun update(gizmo: Gizmo) {
        selected?.install(display, sequenceOf(gizmo))
    }
}

private class DummyRoot(override val children: ObservableList<Gizmo, Gizmo>): Gizmo()

private class NodeAdapter(override val value: Gizmo): Node<Gizmo> {
    override val children get() = value.children_.map { NodeAdapter(it) }
}