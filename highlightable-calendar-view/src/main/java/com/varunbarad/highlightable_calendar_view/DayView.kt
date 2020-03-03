package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    var date: Calendar? = null
        private set
    private var decorators: List<DayDecorator> = emptyList()

    private val dateFormat = SimpleDateFormat("dd", Locale.getDefault())

    internal fun bind(date: Calendar) {
        this.date = date

        this.text = this.dateFormat.format(date.time)
    }

    fun decorate() {
        this.decorators.forEach { it.decorate(this) }
    }
}
