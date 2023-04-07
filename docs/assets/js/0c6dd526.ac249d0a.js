"use strict";(self.webpackChunkdoodle_docs=self.webpackChunkdoodle_docs||[]).push([[544],{8260:(e,i,t)=>{t.r(i),t.d(i,{assets:()=>k,contentTitle:()=>m,default:()=>f,frontMatter:()=>y,metadata:()=>h,toc:()=>w});var n=t(7462),o=(t(7294),t(3905)),a=(t(8209),t(4903),t(1912)),s=t(1166),l=t(7691);const r="package accessibility.module\n\nimport io.nacular.doodle.application.Modules.Companion.AccessibilityModule\nimport io.nacular.doodle.application.application\nimport org.kodein.di.instance\nimport rendering.MyApp\n\nfun main() {\n//sampleStart\n    application(modules = listOf(AccessibilityModule)) {\n        MyApp(instance())\n    }\n//sampleEnd\n}",c="package accessibility\n\nimport io.nacular.doodle.application.Application\nimport io.nacular.doodle.application.Modules.Companion.AccessibilityModule\nimport io.nacular.doodle.application.application\n\n//sampleStart\nclass MyApp(/*...*/): Application {\n    init {\n        // use accessibility features\n    }\n\n    override fun shutdown() {}\n}\n\nfun main () {\n    // Include the AccessibilityModule to enable features\n    application(modules = listOf(AccessibilityModule)) {\n        MyApp(/*...*/)\n    }\n}\n//sampleEnd",d='package accessibility\n\nimport io.nacular.doodle.controls.buttons.PushButton\n\n//sampleStart\nval button = PushButton("x").apply {\n    accessibilityLabel = "Close the window"\n}\n//sampleEnd',p='package accessibility\n\nimport io.nacular.doodle.controls.text.Label\nimport io.nacular.doodle.controls.text.TextField\n\n//sampleStart\nval label     = Label("Enter your name")\nval textField = TextField().apply {\n    accessibilityLabelProvider = label\n}\n//sampleEnd',u="package accessibility\n\nimport io.nacular.doodle.accessibility.ButtonRole\nimport io.nacular.doodle.core.View\n\n//sampleStart\nclass CustomButton: View(accessibilityRole = ButtonRole()) {\n    // ...\n}\n//sampleEnd",b="package accessibility\n\nimport io.nacular.doodle.accessibility.ToggleButtonRole\nimport io.nacular.doodle.core.View\nimport io.nacular.doodle.utils.observable\n\n//sampleStart\nclass CustomToggle(private val role: ToggleButtonRole = ToggleButtonRole()): View(accessibilityRole = role) {\n    var selected by observable(false) { old, new ->\n        role.pressed = new\n    }\n}\n//sampleEnd",y={hide_title:!0},m="Accessibility",h={unversionedId:"accessibility",id:"accessibility",title:"Accessibility",description:"Making truly accessible apps is complex and requires familiarity with a wide range of concepts. The",source:"@site/docs/accessibility.mdx",sourceDirName:".",slug:"/accessibility",permalink:"/doodle/docs/accessibility",draft:!1,tags:[],version:"current",frontMatter:{hide_title:!0},sidebar:"tutorialSidebar",previous:{title:"Form Controls",permalink:"/doodle/docs/ui_components/form_controls"}},k={},w=[{value:"Descriptive Text",id:"descriptive-text",level:2},{value:"<api.ViewAccessibilityLabel><code>accessibilityLabel</code></api.ViewAccessibilityLabel>",id:"accessibilitylabel",level:3},{value:"<api.ViewAccessibilityLabelProvider><code>accessibilityLabelProvider</code></api.ViewAccessibilityLabelProvider>",id:"accessibilitylabelprovider",level:3},{value:"<api.ViewAccessibilityDescriptionProvider><code>accessibilityDescriptionProvider</code></api.ViewAccessibilityDescriptionProvider>",id:"accessibilitydescriptionprovider",level:3},{value:"Widget Roles",id:"widget-roles",level:2}],v={toc:w},g="wrapper";function f(e){let{components:i,...t}=e;return(0,o.kt)(g,(0,n.Z)({},v,t,{components:i,mdxType:"MDXLayout"}),(0,o.kt)("h1",{id:"accessibility"},"Accessibility"),(0,o.kt)("p",null,"Making truly accessible apps is complex and requires familiarity with a wide range of concepts. The\n",(0,o.kt)("a",{parentName:"p",href:"https://www.w3.org/WAI/intro/wcag"},"Web Content Accessibility Guidelines")," provide recommendations for web apps."),(0,o.kt)(l.ZP,{link:(0,o.kt)(s.jKR,null),module:r,mdxType:"ModuleRequired"}),(0,o.kt)("admonition",{type:"caution"},(0,o.kt)("p",{parentName:"admonition"},"Only Web apps support this feature currently, as Desktop is still in alpha.")),(0,o.kt)(a.O,{mdxType:"KPlayground"},c),(0,o.kt)("h2",{id:"descriptive-text"},"Descriptive Text"),(0,o.kt)("h3",{id:"accessibilitylabel"},(0,o.kt)(s.IDK,null,(0,o.kt)("inlineCode",{parentName:"h3"},"accessibilityLabel"))),(0,o.kt)("p",null,"Authors can provide short, descriptive text that is used by assistive technologies to announce a View when it is selected. This property helps\nin cases where a View contains no meaningful text."),(0,o.kt)(a.O,{mdxType:"KPlayground"},d),(0,o.kt)("h3",{id:"accessibilitylabelprovider"},(0,o.kt)(s.afO,null,(0,o.kt)("inlineCode",{parentName:"h3"},"accessibilityLabelProvider"))),(0,o.kt)("p",null,"In many cases the app presents descriptive text to the user directly using other Views, like labels for a text fields. The ",(0,o.kt)("inlineCode",{parentName:"p"},"accessibilityLabelProvider"),' points to another View that should be used as a "label" for the current one.'),(0,o.kt)(a.O,{mdxType:"KPlayground"},p),(0,o.kt)("admonition",{type:"info"},(0,o.kt)("p",{parentName:"admonition"},"Views can be linked this way at any time, even if they are not both currently displayed. Doodle will track the relationship, and surface it to assistive technologies if the Views are simultaneously displayed.")),(0,o.kt)("h3",{id:"accessibilitydescriptionprovider"},(0,o.kt)(s.jQY,null,(0,o.kt)("inlineCode",{parentName:"h3"},"accessibilityDescriptionProvider"))),(0,o.kt)("p",null,"Labels should be short descriptive names for a View. But it is possible to provide more detailed descriptions as well via the\n",(0,o.kt)("inlineCode",{parentName:"p"},"accessibilityDescriptionProvider"),". This property behaves like ",(0,o.kt)("inlineCode",{parentName:"p"},"accessibilityLabelProvider"),", but is intended for longer, more detailed text that describes the View."),(0,o.kt)("h2",{id:"widget-roles"},"Widget Roles"),(0,o.kt)("p",null,"Authors can indicate that a View plays a well-defined role as a widget by tagging it with an accessibility role. This enables assistive technologies to change the presentation of the View to the user as she navigates a scene. This is done by setting the View's ",(0,o.kt)(s.GWE,null),"."),(0,o.kt)("p",null,"Here is an example of creating a View that will serve as a ",(0,o.kt)(s.XRu,null),"."),(0,o.kt)(a.O,{mdxType:"KPlayground"},u),(0,o.kt)("p",null,"This View will now be treated as a button by accessibility technologies (i.e. screen readers). The ",(0,o.kt)("inlineCode",{parentName:"p"},"button")," role itself does not have additional properties, so simply adopting it is sufficient."),(0,o.kt)("p",null,"Other roles have state and must be synchronized with the View to ensure proper assistive support."),(0,o.kt)(a.O,{mdxType:"KPlayground"},b),(0,o.kt)("p",null,"Many of the widgets in the ",(0,o.kt)("a",{parentName:"p",href:"pathname:///api/controls/"},(0,o.kt)("inlineCode",{parentName:"a"},"Controls"))," library ship with accessibility support (though this continues to improve). The library also provides bindings for some roles and models, which makes it easier to synchronize roles with their widgets. ",(0,o.kt)(s.LVu,null),", for example, binds its role to the ",(0,o.kt)(s.YMB,null)," that underlies it. This way the role and View are always in sync."))}f.isMDXComponent=!0},7691:(e,i,t)=>{t.d(i,{ZP:()=>r});var n=t(7462),o=(t(7294),t(3905)),a=(t(8209),t(4866),t(5162),t(1912));const s={toc:[]},l="wrapper";function r(e){let{components:i,...t}=e;return(0,o.kt)(l,(0,n.Z)({},s,t,{components:i,mdxType:"MDXLayout"}),(0,o.kt)("admonition",{title:"Module Required",type:"info"},(0,o.kt)("p",null,"You must include the ",t.link," in your application in order to use these features."),(0,o.kt)(a.O,{mdxType:"KPlayground"},t.module),(0,o.kt)("p",{parentName:"admonition"},"Doodle uses opt-in modules like this to improve bundle size.")))}r.isMDXComponent=!0}}]);