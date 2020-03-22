package com.nectar.doodle

/**
 * Created by Nicholas Eddy on 8/9/19.
 */
actual typealias CSSStyleSheet = org.w3c.dom.css.CSSStyleSheet

actual val CSSStyleSheet.numStyles: Int get() = this.cssRules.length

actual typealias CSSStyleDeclaration = org.w3c.dom.css.CSSStyleDeclaration

actual var CSSStyleDeclaration.clipPath: String get() = this.asDynamic()["clip-path"] as String
    set(new) { this.asDynamic()["clip-path"] = new }

actual var CSSStyleDeclaration.willChange: String get() = this.asDynamic()["will-change"] as String
    set(new) { this.asDynamic()["will-change"] = new }

actual typealias ElementCSSInlineStyle = org.w3c.dom.css.ElementCSSInlineStyle

actual typealias DOMRect   = org.w3c.dom.DOMRect
actual typealias Element   = org.w3c.dom.Element
actual typealias DragEvent = org.w3c.dom.DragEvent

actual typealias HTMLElement = org.w3c.dom.HTMLElement

actual typealias ElementCreationOptions = org.w3c.dom.ElementCreationOptions

actual typealias Document = org.w3c.dom.Document

actual typealias Text              = org.w3c.dom.Text
actual typealias CharacterData     = org.w3c.dom.CharacterData
actual typealias HTMLHeadElement   = org.w3c.dom.HTMLHeadElement
actual typealias HTMLImageElement  = org.w3c.dom.HTMLImageElement
actual typealias HTMLInputElement  = org.w3c.dom.HTMLInputElement
actual typealias HTMLButtonElement = org.w3c.dom.HTMLButtonElement

actual typealias StyleSheet = org.w3c.dom.css.StyleSheet

actual inline operator fun StyleSheetList.get(index: Int): StyleSheet? = item(index)

actual typealias StyleSheetList = org.w3c.dom.css.StyleSheetList

actual typealias HTMLStyleElement = org.w3c.dom.HTMLStyleElement