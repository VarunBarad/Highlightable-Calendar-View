package com.varunbarad.highlightable_calender_view.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.varunbarad.highlightable_calendar_view.DayDecorator
import com.varunbarad.highlightable_calendar_view.HighlightableCalendarView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView: HighlightableCalendarView = this.findViewById(R.id.calendar)

        calendarView.dayDecorators = listOf(
            DayDecorator(
                Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_MONTH, -1)
                },
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimary)
            ),
            DayDecorator(
                Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_MONTH, 3)
                },
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent)
            )
        )
    }
}
