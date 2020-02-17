package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.content.res.TypedArray
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

    var firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
        set(value) {
            field = value
            this.monthCalendar.firstDayOfWeek = (value.ordinal + 1)
            this.updateCalendarDisplayedContents()
        }

    private val monthCalendar: Calendar = Calendar.getInstance()

    init {
        this.viewBinding = ViewHighlightableCalendarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        this.readAndInitializeAttributes(attributes, defStyleAttr)

        this.viewBinding.buttonPreviousMonth.setOnClickListener {
            this.monthCalendar.add(Calendar.MONTH, -1)

            this.updateCalendarDisplayedContents()

            // ToDo: Call month-change-listener
        }

        this.viewBinding.buttonNextMonth.setOnClickListener {
            this.monthCalendar.add(Calendar.MONTH, +1)

            this.updateCalendarDisplayedContents()

            // ToDo: Call month-change-listener
        }

        this.updateCalendarDisplayedContents()
    }

    private fun readAndInitializeAttributes(attr: AttributeSet?, defStyleAttr: Int) {
        attr?.let {
            val attributeValues: TypedArray = context.obtainStyledAttributes(
                it,
                R.styleable.HighlightableCalendarView,
                defStyleAttr,
                0
            )

            this.firstDayOfWeek = DayOfWeek.values()[attributeValues.getInt(
                R.styleable.HighlightableCalendarView_firstDayOfWeek,
                DayOfWeek.SUNDAY.ordinal
            )]

            attributeValues.recycle()
        }
    }

    private fun updateCalendarDisplayedContents() {
        this.viewBinding.monthTitle.text =
            "${monthCalendar.getFullNameOfMonth()} ${monthCalendar.get(Calendar.YEAR)}"

        this.setWeekDayNames()
    }

    private fun setWeekDayNames() {
        when (this.monthCalendar.firstDayOfWeek) {
            Calendar.SUNDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
            }
            Calendar.MONDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
            }
            Calendar.TUESDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
            }
            Calendar.WEDNESDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
            }
            Calendar.THURSDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
            }
            Calendar.FRIDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
            }
            Calendar.SATURDAY -> {
                this.viewBinding.weekDay1.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_saturday)
                this.viewBinding.weekDay2.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_sunday)
                this.viewBinding.weekDay3.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_monday)
                this.viewBinding.weekDay4.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_tuesday)
                this.viewBinding.weekDay5.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_wednesday)
                this.viewBinding.weekDay6.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_thursday)
                this.viewBinding.weekDay7.text =
                    context.resources.getString(R.string.label_dayOfWeek_short_friday)
            }
        }
    }
}
