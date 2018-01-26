package com.nectar.doodle.drawing

import com.nectar.doodle.dom.Display
import com.nectar.doodle.dom.FontStyle
import com.nectar.doodle.dom.HtmlFactory
import com.nectar.doodle.dom.Position
import com.nectar.doodle.dom.add
import com.nectar.doodle.dom.childAt
import com.nectar.doodle.dom.numChildren
import com.nectar.doodle.dom.setBackgroundColor
import com.nectar.doodle.dom.setColor
import com.nectar.doodle.dom.setDisplay
import com.nectar.doodle.dom.setFontFamily
import com.nectar.doodle.dom.setFontSize
import com.nectar.doodle.dom.setFontStyle
import com.nectar.doodle.dom.setFontWeight
import com.nectar.doodle.dom.setPosition
import com.nectar.doodle.dom.setTextIndent
import com.nectar.doodle.dom.setWidth
import com.nectar.doodle.text.Style
import com.nectar.doodle.text.StyledText
import org.w3c.dom.HTMLElement
import kotlin.dom.clear


internal class TextFactoryImpl(private val htmlFactory: HtmlFactory): TextFactory {

    override fun create(text: String, font: Font?, possible: HTMLElement?): HTMLElement {
        val element = htmlFactory.createOrUse("PRE", possible)

        if (element.innerHTML != text) {
            element.innerHTML = ""
            element.add(htmlFactory.createText(text))
        }

        font?.let {
            element.run {
                style.setFontSize  (it.size  )
                style.setFontFamily(it.family)
                style.setFontWeight(it.weight)
            }

            if (it.isItalic) {
                element.style.setFontStyle(FontStyle.Italic)
            }
        }

        return element
    }

    override fun create(text: StyledText, possible: HTMLElement?): HTMLElement {
        if (text.count == 1) {
            text.first().also { (text, style) ->
                return create(text, style.font, possible).also {
                    applyStyle(it, style)
                }
            }
        }

        val element = htmlFactory.createOrUse("B", possible)

        element.clear()

        text.forEach { (text, style) ->
            element.add(create(text, style.font).also { element ->
                element.style.setDisplay (Display.Inline   )
                element.style.setPosition(Position.Relative)

                applyStyle(element, style)
            })
        }

        return element
    }

    // FIXME: Portability
    override fun wrapped(text: String, font: Font?, width: Double, indent: Double, possible: HTMLElement?) = create(text, font, possible).also {
        applyWrap(it, indent)
    }

    override fun wrapped(text: StyledText, width: Double, indent: Double, possible: HTMLElement?) = create(text, possible).also {
        if (it.nodeName.equals("PRE", ignoreCase = true)) {
            applyWrap(it, indent)
        } else {
            (0 until it.numChildren).map { i -> it.childAt(i) }.filterIsInstance<HTMLElement>().forEach { applyWrap(it, indent) }

            it.style.setWidth(width)
        }
    }

    private fun applyWrap(element: HTMLElement, indent: Double) {
        element.style.whiteSpace = "pre-wrap"
        element.style.setTextIndent(indent)
    }

    private fun applyStyle(element: HTMLElement, style: Style) {
        style.foreground?.let {
            element.style.setColor(it)
        }
        style.background?.let {
            element.style.setBackgroundColor(it)
        }
    }
}