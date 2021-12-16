package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

data class Lyrics(
    val lyrics: String
)
// If I add "timestamp", and then AWS sends back Lyrics.timestamp to an earlier version, it will
// not crash, it will just ignore it (good thing, version-safe)
// That's adding attributes
// Deleting attributes also relatively safe, your old variable has to be nullable
// Mutating attributes is when you run into trouble