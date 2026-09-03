package com.vktrsansara.app.pixifx

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform