package com.star.domain.log

fun throwException(tag: String, msg: String): Nothing = throw Exception("[$tag]$msg")