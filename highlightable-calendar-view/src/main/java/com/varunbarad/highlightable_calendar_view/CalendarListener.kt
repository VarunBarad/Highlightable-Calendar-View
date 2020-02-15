package com.varunbarad.highlightable_calendar_view

import java.util.*

interface CalendarListener {
    fun onDateSelected(date: Date)

    fun onMonthChanged(date: Date)
}
