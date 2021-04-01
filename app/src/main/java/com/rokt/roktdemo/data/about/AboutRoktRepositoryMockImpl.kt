package com.rokt.roktdemo.data.about

import com.rokt.roktdemo.data.AboutRoktRepository
import com.rokt.roktdemo.model.AboutContent
import com.rokt.roktdemo.model.AboutLink
import com.rokt.roktdemo.model.AboutRokt

class AboutRoktRepositoryMockImpl : AboutRoktRepository {

    // TODO : Api layer
    private val aboutContent = listOf(
        AboutContent(
            "What is the Rokt Demo app?",
            """
                Discover why Rokt is the easiest and most powerful way to optimize value across both your customer engagement and revenue objectives. 
                
                The purpose of this app is to showcase the functionality that Rokt provides in-app. This is demonstrated with examples of how Rokt’s partners are generating stronger revenue outcomes by providing a more personalized experience for each customer at scale. 
                
                These examples are used to show Rokt’s capabilities, are for demonstration purposes only, and may not be true reflections of the partners applications. This app does not collect personal or device identifiers.
                
            """.trimIndent()
        ),
        AboutContent(
            "Who we are",
            """
                Rokt is the global leader in e-commerce technology, powering the Transaction Moment™ of best-in-class companies including Live Nation, Groupon, Staples, Lands' End, Fanatics, GoDaddy, Vistaprint, and HelloFresh. Rokt's mission: To make e-commerce smarter, faster, and better.
                
                Through its proprietary technology, Rokt enables its e-commerce clients to increase brand engagement and unlock new revenues in the Transaction Moment™, allowing them to stay ahead of their competition while delivering a superior and individualized experience for each customer.
                
                Founded in Sydney, the company now operates in the US, Canada, the UK, Ireland, France, Germany, the Netherlands, Denmark, Sweden, Norway, Finland, Spain, Australia, New Zealand, Singapore, and Japan.
                
            """.trimIndent(),
            imageUrl = "testing"
        )
    )

    override fun getAboutRokt(): AboutRokt {
        return AboutRokt(aboutContent, listOf(AboutLink("Learn More", "https://rokt.com/")))
    }
}
