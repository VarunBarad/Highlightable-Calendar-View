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

    private fun numberOfDaysOfPreviousMonthsToBeDisplayed(): Int {
        val tempCalendar = (this.monthCalendar.clone() as Calendar).apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }

        // Since if column for first day of month is 0 then no days of previous month are displayed
        // and if column is 6 then 6 days of previous month are displayed. Same goes for all
        // values in between
        return this.getWeekColumnForDate(tempCalendar)
    }

    private fun numberOfDaysOfNextMonthToBeDisplayed(): Int {
        val tempCalendar = (this.monthCalendar.clone() as Calendar)
        tempCalendar.set(
            Calendar.DAY_OF_MONTH,
            tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        )

        // Since if column for last day of month is 0 then 6 days of next month are displayed
        // and if column is 6 then 0 days of next month are displayed. Similar logic goes for all
        // values in between
        return (6 - this.getWeekColumnForDate(tempCalendar))
    }

    private fun getWeekColumnForDate(date: Calendar): Int {
        val weekDay = this.getWeekDayForDate(date)
        return when (this.firstDayOfWeek) {
            DayOfWeek.SUNDAY -> {
                when (weekDay) {
                    Calendar.SUNDAY -> 0
                    Calendar.MONDAY -> 1
                    Calendar.TUESDAY -> 2
                    Calendar.WEDNESDAY -> 3
                    Calendar.THURSDAY -> 4
                    Calendar.FRIDAY -> 5
                    Calendar.SATURDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.MONDAY -> {
                when (weekDay) {
                    Calendar.MONDAY -> 0
                    Calendar.TUESDAY -> 1
                    Calendar.WEDNESDAY -> 2
                    Calendar.THURSDAY -> 3
                    Calendar.FRIDAY -> 4
                    Calendar.SATURDAY -> 5
                    Calendar.SUNDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.TUESDAY -> {
                when (weekDay) {
                    Calendar.TUESDAY -> 0
                    Calendar.WEDNESDAY -> 1
                    Calendar.THURSDAY -> 2
                    Calendar.FRIDAY -> 3
                    Calendar.SATURDAY -> 4
                    Calendar.SUNDAY -> 5
                    Calendar.MONDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.WEDNESDAY -> {
                when (weekDay) {
                    Calendar.WEDNESDAY -> 0
                    Calendar.THURSDAY -> 1
                    Calendar.FRIDAY -> 2
                    Calendar.SATURDAY -> 3
                    Calendar.SUNDAY -> 4
                    Calendar.MONDAY -> 5
                    Calendar.TUESDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.THURSDAY -> {
                when (weekDay) {
                    Calendar.THURSDAY -> 0
                    Calendar.FRIDAY -> 1
                    Calendar.SATURDAY -> 2
                    Calendar.SUNDAY -> 3
                    Calendar.MONDAY -> 4
                    Calendar.TUESDAY -> 5
                    Calendar.WEDNESDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.FRIDAY -> {
                when (weekDay) {
                    Calendar.FRIDAY -> 0
                    Calendar.SATURDAY -> 1
                    Calendar.SUNDAY -> 2
                    Calendar.MONDAY -> 3
                    Calendar.TUESDAY -> 4
                    Calendar.WEDNESDAY -> 5
                    Calendar.THURSDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
            DayOfWeek.SATURDAY -> {
                when (weekDay) {
                    Calendar.SATURDAY -> 0
                    Calendar.SUNDAY -> 1
                    Calendar.MONDAY -> 2
                    Calendar.TUESDAY -> 3
                    Calendar.WEDNESDAY -> 4
                    Calendar.THURSDAY -> 5
                    Calendar.FRIDAY -> 6
                    else -> throw IllegalStateException("$weekDay should be from Sunday to Saturday only")
                }
            }
        }
    }

    private fun getWeekDayForDate(date: Calendar): Int {
        return date.get(Calendar.DAY_OF_WEEK)
    }
}
