package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.varunbarad.highlightable_calendar_view.databinding.ViewHighlightableCalendarBinding

class HighlightableCalendarView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributes, defStyleAttr) {
    private val viewBinding: ViewHighlightableCalendarBinding

    init {
        this.viewBinding = ViewHighlightableCalendarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }
}
