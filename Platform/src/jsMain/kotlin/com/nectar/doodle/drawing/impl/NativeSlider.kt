package com.nectar.doodle.drawing.impl

import com.nectar.doodle.controls.Slider
import com.nectar.doodle.core.View
import com.nectar.doodle.dom.ElementRuler
import com.nectar.doodle.dom.HtmlFactory
import com.nectar.doodle.dom.add
import com.nectar.doodle.dom.setHeight
import com.nectar.doodle.dom.setWidth
import com.nectar.doodle.drawing.Canvas
import com.nectar.doodle.focus.FocusManager
import com.nectar.doodle.geometry.Rectangle
import com.nectar.doodle.geometry.Size
import com.nectar.doodle.utils.size
import org.w3c.dom.HTMLElement

/**
 * Created by Nicholas Eddy on 11/20/18.
 */
interface NativeSliderFactory {
    operator fun invoke(slider: Slider): NativeSlider
}

class NativeSliderFactoryImpl internal constructor(
        private val htmlFactory              : HtmlFactory,
        private val elementRuler             : ElementRuler,
        private val nativeEventHandlerFactory: NativeEventHandlerFactory,
        private val focusManager             : FocusManager?): NativeSliderFactory {

    private val sizeDifference: Size by lazy {
        val slider = htmlFactory.createInput().apply {
            style.position = "initial"
            type           = "range"
        }

        val holder = htmlFactory.create<HTMLElement>().apply {
            add(slider)
        }

        elementRuler.size(holder).let {
            Size(it.width - defaultSize.width, it.height - defaultSize.height)
        }
    }

    private val defaultSize: Size by lazy {
        elementRuler.size(htmlFactory.createInput().apply {
            type = "range"
        })
    }

    override fun invoke(slider: Slider) = NativeSlider(htmlFactory, nativeEventHandlerFactory, focusManager, defaultSize, sizeDifference, slider)
}

class NativeSlider internal constructor(
                    htmlFactory   : HtmlFactory,
                    handlerFactory: NativeEventHandlerFactory,
        private val focusManager  : FocusManager?,
        private val defaultSize   : Size,
        private val marginSize    : Size,
        private val slider        : Slider): NativeEventListener {

    private val oldSliderHeight = slider.height

    private val nativeEventHandler: NativeEventHandler

    private val sliderElement = htmlFactory.createInput().apply {
        type  = "range"
        step  = "any"
        value = slider.value.toString()
//        style.cursor = "inherit"
    }

    private val changed: (Slider, Double, Double) -> Unit = { it,_,_ ->
        sliderElement.value = "${it.value / slider.model.limits.size * 100}"
    }

    private val focusChanged: (View, Boolean, Boolean) -> Unit = { _,_,new ->
        when (new) {
            true -> sliderElement.focus()
            else -> sliderElement.blur ()
        }
    }

    private val enabledChanged: (View, Boolean, Boolean) -> Unit = { _,_,new ->
        sliderElement.disabled = !new
    }

    private val focusableChanged: (View, Boolean, Boolean) -> Unit = { _,_,new ->
        sliderElement.tabIndex = if (new) -1 else 0
    }

    private val boundsChanged: (View, Rectangle, Rectangle) -> Unit = { _,_,_ ->
        sliderElement.style.setWidth (slider.width  - marginSize.width )
        sliderElement.style.setHeight(slider.height - marginSize.height)
    }

    init {
        nativeEventHandler = handlerFactory(sliderElement, this).apply {
            registerFocusListener()
            registerClickListener()
            registerInputListener()
        }

        slider.apply {
            changed             += this@NativeSlider.changed
            focusChanged        += this@NativeSlider.focusChanged
            boundsChanged       += this@NativeSlider.boundsChanged
            enabledChanged      += this@NativeSlider.enabledChanged
            focusabilityChanged += this@NativeSlider.focusableChanged

            changed      (this, 0.0,             value )
            boundsChanged(this, Rectangle.Empty, bounds)

            height = this@NativeSlider.defaultSize.height + this@NativeSlider.marginSize.height
        }
    }

    fun discard() {
        slider.apply {
            changed             -= this@NativeSlider.changed
            focusChanged        -= this@NativeSlider.focusChanged
            boundsChanged       -= this@NativeSlider.boundsChanged
            enabledChanged      -= this@NativeSlider.enabledChanged
            focusabilityChanged -= this@NativeSlider.focusableChanged

            height = oldSliderHeight
        }
    }

    fun render(canvas: Canvas) {
        if (canvas is CanvasImpl) {

            canvas.addData(listOf(sliderElement))

            if (slider.hasFocus) {
                sliderElement.focus()
            }
        }
    }

    override fun onFocusGained(): Boolean {
        if (!slider.focusable) {
            return false
        }

        focusManager?.requestFocus(slider)

        return true
    }

    override fun onFocusLost(): Boolean {
        if (slider === focusManager?.focusOwner) {
            focusManager.clearFocus()
        }

        return true
    }

    override fun onInput(): Boolean {
        sliderElement.value.toDoubleOrNull()?.let {
            slider.value = slider.model.limits.start + (it / 100 * slider.model.limits.size)
        }

        return true
    }
}