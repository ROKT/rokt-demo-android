package com.rokt.roktdemo.model


class AboutRokt(val content: List<AboutContent>, val links: List<AboutLink>)
class AboutContent(val title: String, val content: String, val imageUrl: String? = null)
class AboutLink(val text: String, val url: String)


