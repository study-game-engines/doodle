"use strict";(self.webpackChunkdoodle_docs=self.webpackChunkdoodle_docs||[]).push([[937],{4811:(o,e,n)=>{n.r(e),n.d(e,{assets:()=>c,contentTitle:()=>s,default:()=>h,frontMatter:()=>r,metadata:()=>p,toc:()=>u});var i=n(7462),t=(n(7294),n(3905)),l=(n(8209),n(4866),n(5162),n(4903)),a=n(1912);const d='import io.nacular.doodle.application.Application\nimport io.nacular.doodle.application.application\nimport io.nacular.doodle.core.Display\nimport io.nacular.doodle.core.view\nimport io.nacular.doodle.drawing.Color.Companion.Black\nimport io.nacular.doodle.drawing.Color.Companion.White\nimport io.nacular.doodle.drawing.paint\nimport io.nacular.doodle.drawing.text\nimport org.kodein.di.instance\n\n//sampleStart\nclass HelloDoodle(display: Display): Application {\n    init {\n        display += view {\n            size   = display.size\n            render = {\n                text("Hello, Doodle!", color = Black)\n            }\n        }\n\n        display.fill(White.paint)\n    }\n\n    override fun shutdown() {}\n}\n//sampleEnd\n\nfun main() {\n    application {\n        HelloDoodle(display = instance())\n    }\n}',r={hide_title:!0,title:"Hello Doodle",description:"Your first Doodle application."},s=void 0,p={unversionedId:"introduction",id:"introduction",title:"Hello Doodle",description:"Your first Doodle application.",source:"@site/docs/introduction.mdx",sourceDirName:".",slug:"/introduction",permalink:"/doodle/docs/introduction",draft:!1,tags:[],version:"current",frontMatter:{hide_title:!0,title:"Hello Doodle",description:"Your first Doodle application."},sidebar:"tutorialSidebar",next:{title:"Installation",permalink:"/doodle/docs/installation"}},c={},u=[{value:"Feedback",id:"feedback",level:3}],m={toc:u},k="wrapper";function h(o){let{components:e,...n}=o;return(0,t.kt)(k,(0,i.Z)({},m,n,{components:e,mdxType:"MDXLayout"}),(0,t.kt)(l.l,{functionName:"helloDoodle",height:"200",mdxType:"DoodleApp"}),(0,t.kt)(a.O,{mdxType:"KPlayground"},d),(0,t.kt)("h3",{id:"feedback"},"Feedback"),(0,t.kt)("p",null,"Doodle is still under active development, so there are going to be gaps and bugs. Please report ",(0,t.kt)("a",{parentName:"p",href:"https://github.com/pusolito/doodle/issues"},"issues"),",\nand submit feature requests."),(0,t.kt)("p",null,"You can also join the discussion on the ",(0,t.kt)("a",{parentName:"p",href:"https://kotlinlang.slack.com/messages/doodle"},"#doodle")," Kotlin Slack channel."))}h.isMDXComponent=!0}}]);