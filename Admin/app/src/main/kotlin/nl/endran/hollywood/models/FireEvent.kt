package nl.endran.hollywood.models

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class FireEvent(
        val appId: String? = null,
        val checked: Boolean? = null,
        val clientType: String? = null,
        val name: String? = null,
        val radioId: Int? = null,
        val timeStamp: Long? = null,
        val slider: Int? = null) {

    fun readableTimeStamp(): String? {
        if (timeStamp != null) {
            val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp!!), ZoneId.of("UTC"))
            return zonedDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))
        } else {
            return null
        }
    }

}
