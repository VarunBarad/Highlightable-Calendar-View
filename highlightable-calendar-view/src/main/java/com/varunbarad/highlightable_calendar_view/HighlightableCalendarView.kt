package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.varunbarad.highlightable_calendar_view.databinding.ViewHighlightableCalendarBinding
import com.varunbarad.highlightable_calendar_view.util.getFullNameOfMonth
import java.util.*

class HighlightableCalendarView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributes, defStyleAttr) {
    private val viewBinding: ViewHighlightableCalendarBinding

    private val monthCalendar: Calendar = Calendar.getInstance()

    init {
        this.viewBinding = ViewHighlightableCalendarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        this.updateCalendarDisplayedContents()
    }

    private fun updateCalendarDisplayedContents() {
        this.viewBinding.monthTitle.text =
            "${monthCalendar.getFullNameOfMonth()} ${monthCalendar.get(Calendar.YEAR)}"
    }
}
