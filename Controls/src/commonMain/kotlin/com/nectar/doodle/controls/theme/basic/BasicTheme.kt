package com.nectar.doodle.controls.theme.basic

import com.nectar.doodle.controls.ProgressBar
import com.nectar.doodle.controls.ProgressIndicator
import com.nectar.doodle.controls.Slider
import com.nectar.doodle.controls.buttons.Button
import com.nectar.doodle.controls.list.List
import com.nectar.doodle.controls.panels.SplitPanel
import com.nectar.doodle.controls.spinner.Spinner
import com.nectar.doodle.controls.text.LabelFactory
import com.nectar.doodle.controls.tree.Tree
import com.nectar.doodle.core.Display
import com.nectar.doodle.core.Gizmo
import com.nectar.doodle.drawing.Color
import com.nectar.doodle.drawing.Color.Companion.black
import com.nectar.doodle.drawing.TextMetrics
import com.nectar.doodle.theme.Renderer
import com.nectar.doodle.theme.Theme

/**
 * Created by Nicholas Eddy on 2/12/18.
 */

private val borderColor            = Color(0x888888u)
private val foregroundColor        = black
private val backgroundColor        = Color(0xccccccu)
private val darkBackgroundColor    = Color(0xaaaaaau)
private val defaultBackgroundColor = backgroundColor


typealias ListModel<T>    = com.nectar.doodle.controls.list.Model<T>
typealias SpinnerModel<T> = com.nectar.doodle.controls.spinner.Model<T>

@Suppress("UNCHECKED_CAST")
class BasicTheme(private val labelFactory: LabelFactory, private val textMetrics: TextMetrics): Theme {

    private val progressBarUI by lazy { BasicProgressBarUI(defaultBackgroundColor = defaultBackgroundColor, darkBackgroundColor = darkBackgroundColor)}

    override fun install(display: Display, all: Sequence<Gizmo>) = all.forEach {
        when (it) {
            is ProgressBar   -> { it.renderer?.uninstall(it); it.renderer = (progressBarUI as Renderer<ProgressIndicator>).apply { install(it) } }
            is Slider        -> { it.renderer?.uninstall(it); it.renderer = BasicSliderUI(it, defaultBackgroundColor = defaultBackgroundColor, darkBackgroundColor = darkBackgroundColor).apply { install(it) } }
            is SplitPanel    -> { it.renderer?.uninstall(it); it.renderer = BasicSplitPanelUI(darkBackgroundColor = darkBackgroundColor).apply { install(it) } }
            is Button        -> { it.renderer?.uninstall(it); it.renderer = BasicButtonUI(textMetrics, backgroundColor = backgroundColor, borderColor = borderColor, darkBackgroundColor = darkBackgroundColor, foregroundColor = foregroundColor).apply { install(it) } }
            is Spinner<*, *> -> (it as Spinner<Any, SpinnerModel<Any>>).let { it.renderer?.uninstall(it); it.renderer = BasicSpinnerUI(borderColor = borderColor, backgroundColor = backgroundColor, labelFactory = labelFactory).apply { install(it) } }
            is List<*, *>    -> (it as List<Any, ListModel<Any>>      ).let { it.renderer?.uninstall(it); it.renderer = BasicListUI<Any>(textMetrics).apply { install(it) } }
            is Tree<*>       -> (it as Tree<Any>                      ).let { it.renderer?.uninstall(it); it.renderer = AbstractTreeUI<Any>(labelFactory).apply { install(it) } }
        }
    }

    override fun toString() = this::class.simpleName ?: ""
}

//val basicThemeModule = Kodein.Module {
//    bind<BasicTheme>() with singleton { BasicTheme() }
//}