package com.tstv.newsapp.internal

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T>{
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun convertStringToLocalTime(dateInString: String): LocalDateTime{
    return LocalDateTime.parse(dateInString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun convertLocalTimeToString(localTime: LocalDateTime): String{
    with(localTime) {
        val month = if(monthValue.toString().length == 1) "0$monthValue" else monthValue.toString()
        val day = if(dayOfMonth.toString().length == 1) "0$dayOfMonth" else dayOfMonth.toString()
        val hour = if(hour.toString().length == 1) "0$hour" else hour.toString()
        val minutes = if(minute.toString().length == 1) "0$minute" else minute.toString()
        val seconds = if(second.toString().length == 1) "0$second" else second.toString()
        return StringBuilder()
            .append(year).append("-").append(month).append("-").append(day)
            .append("T").append(hour).append(":").append(minutes).append(":")
            .append(seconds)
            .toString()
    }
}