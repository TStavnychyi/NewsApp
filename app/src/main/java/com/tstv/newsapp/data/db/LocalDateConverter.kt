package com.tstv.newsapp.data.db

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDateConverter {
    @JvmStatic
    fun stringToDate(str: String?) = str?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME)
    }

    @JvmStatic
    fun dateToString(dateTime: LocalDate?) = dateTime?.format(DateTimeFormatter.ISO_DATE_TIME)
}