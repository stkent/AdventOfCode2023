package utils

fun <T : Any> T.println(): T = this.also { println(it) }
