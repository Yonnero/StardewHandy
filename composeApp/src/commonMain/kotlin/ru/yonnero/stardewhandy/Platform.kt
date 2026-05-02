package ru.yonnero.stardewhandy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform