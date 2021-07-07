package io.nacular.doodle.drawing.impl

import io.nacular.doodle.drawing.Font
import io.nacular.doodle.drawing.TextMetrics
import io.nacular.doodle.text.StyledText
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.paragraph.BaselineMode
import org.jetbrains.skija.paragraph.FontCollection
import org.jetbrains.skija.paragraph.Paragraph
import org.jetbrains.skija.paragraph.ParagraphBuilder
import org.jetbrains.skija.paragraph.ParagraphStyle
import org.jetbrains.skija.paragraph.PlaceholderAlignment
import org.jetbrains.skija.paragraph.PlaceholderStyle
import org.jetbrains.skija.paragraph.TextStyle
import kotlin.math.max
import org.jetbrains.skija.Font as SkijaFont

/**
 * Created by Nicholas Eddy on 6/8/21.
 */
internal class TextMetricsImpl(private val defaultFont: SkijaFont, private val fontCollection: FontCollection): TextMetrics {
    private val Font?.skia get() = when (this) {
        is FontImpl -> skiaFont
        else        -> defaultFont
    }

    private fun StyledText.paragraph(): Paragraph {
        val builder = ParagraphBuilder(ParagraphStyle(), fontCollection).also { builder ->
            this.forEach { (text, style) ->
                builder.pushStyle(TextStyle().apply {
                    typeface   = style.font.skia.typeface
                    fontSize   = style.font.skia.size
                    fontStyle  = style.font.skia.typeface?.fontStyle
                })
                builder.addText(text)
                builder.popStyle()
            }
        }

        return builder.build().apply { layout(Float.POSITIVE_INFINITY) }
    }

    private fun String.paragraph(font: Font?): Paragraph {
        val builder = ParagraphBuilder(ParagraphStyle(), fontCollection).also { builder ->
            builder.pushStyle(TextStyle().apply {
                typeface   = font.skia.typeface
                fontSize   = font.skia.size
                fontStyle  = font.skia.typeface?.fontStyle
            })
            builder.addText(this)
        }

        return builder.build().apply { layout(Float.POSITIVE_INFINITY) }
    }

    override fun width(text: String, font: Font?) = max(0.0, text.paragraph(font).longestLine.toDouble())

    // FIXME
    override fun width(text: String, width: Double, indent: Double, font: Font?) = width

    override fun width(text: StyledText) = max(0.0, text.paragraph().longestLine.toDouble())

    override fun width(text: StyledText, width: Double, indent: Double): Double {
        TODO("Not yet implemented")
    }

    override fun height(text: String, font: Font?) = max(0.0, font.skia.measureText(text).height.toDouble())

    override fun height(text: String, width: Double, indent: Double, font: Font?): Double {
        val style = ParagraphStyle().apply {
            textStyle = TextStyle().apply {
                typeface = font.skia.typeface
                fontSize = font.skia.size
            }
        }

        val fontCollection = FontCollection().apply {
            setDefaultFontManager(FontMgr.getDefault())
        }

        val builder = ParagraphBuilder(style, fontCollection).
        addPlaceholder(PlaceholderStyle((indent).toFloat(), 0f, PlaceholderAlignment.BASELINE, BaselineMode.ALPHABETIC, 0f)).
        addText(text)

        val paragraph = builder.build()

        paragraph.layout(width.toFloat())

        return max(0.0, paragraph.height.toDouble())
    }

    override fun height(text: StyledText) = text.paragraph().height.toDouble()

    override fun height(text: StyledText, width: Double, indent: Double): Double {
        TODO("Not yet implemented")
    }
}