@file:JvmName("CalendarUtils")

package com.varunbarad.highlightable_calendar_view.util

import java.util.*

/**
 * Checks if both dates fall under same month
 *
 * @param c1 first date
 * @param c2 second date
 *
 * @return true if both are in same month, false otherwise
 */
fun isSameMonth(c1: Calendar, c2: Calendar): Boolean {
    return ((c1.get(Calendar.ERA) == c2.get(Calendar.ERA))
            && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
            && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)))
}

/**
 * Checks if both dates are for same day
 *
 * @param c1 first date
 * @param c2 second date
 *
 * @return true if both are for same date, false otherwise
 */
fun isSameDay(c1: Calendar, c2: Calendar): Boolean {
    return isSameMonth(c1, c2) && (c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
}


/**
 * Checks if the date is for today
 *
 * @param calendar date to check
 *
 * @return true if the date is of today
 */
fun isToday(calendar: Calendar): Boolean {
    return isSameDay(calendar, Calendar.getInstance())
}

/**
 * Check if the passed date is in the past
 *
 * @param date The date to check
 *
 * @return true if the passed date is older than today, false otherwise
 */
fun isPastDay(date: Date): Boolean {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return date.before(calendar.time)
}

internal fun Calendar.getFullNameOfMonth(): String {
    return this.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        ?: throw IllegalStateException()
}
