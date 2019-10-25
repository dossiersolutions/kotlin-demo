package no.dossier.app.kotlindemo.util

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse

fun parseBitBucketDateTime(dateStr: String): DateTime {
    val dateStrNoNano = dateStr.subSequence(0, dateStr.indexOf("+")).toString()
    val dateFormat: DateFormat = DateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return dateFormat.parse(dateStrNoNano).local;
}