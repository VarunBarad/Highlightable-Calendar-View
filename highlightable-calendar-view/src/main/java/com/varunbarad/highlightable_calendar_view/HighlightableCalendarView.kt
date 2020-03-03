package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.varunbarad.highlightable_calendar_view.databinding.ViewHighlightableCalendarBinding
import com.varunbarad.highlightable_calendar_view.util.getFullNameOfMonth
import com.varunbarad.highlightable_calendar_view.util.isSameMonth
import com.varunbarad.highlightable_calendar_view.util.isThisCalendarOfNextMonthFrom
import com.varunbarad.highlightable_calendar_view.util.isThisCalendarOfPreviousMonthFrom
import java.util.*
import kotlin.math.ceil

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
        this.setMonthDayTexts()
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

    private fun setMonthDayTexts() {
        this.setRowVisibility(this.numberOfRowsToDisplayInCurrentMonthCalendar())

        for (cellIndex in 1..this.numberOfDaysTotalToBeDisplayed()) {
            val dayView = this.getDayViewForCellIndex(cellIndex)
            val date = this.getDateForCellIndex(cellIndex)

            dayView.text = "${date.get(Calendar.DAY_OF_MONTH)}"

            if (!monthCalendar.isSameMonth(date)) {
                // ToDo: Apply non-current-month style
                dayView.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.day_backgroundColor_disabled
                    )
                )
                dayView.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.day_textColor_disabled
                    )
                )
            } else {
                // ToDo: Apply current-month style
                dayView.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.day_backgroundColor_enabled
                    )
                )
                dayView.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.day_textColor_enabled
                    )
                )
            }
        }
    }

    private fun setRowVisibility(rowsToDisplay: Int) {
        this.viewBinding.weekRow1.visibility = if (rowsToDisplay > 0) {
            VISIBLE
        } else {
            GONE
        }
        this.viewBinding.weekRow2.visibility = if (rowsToDisplay > 1) {
            VISIBLE
        } else {
            GONE
        }
        this.viewBinding.weekRow3.visibility = if (rowsToDisplay > 2) {
            VISIBLE
        } else {
            GONE
        }
        this.viewBinding.weekRow4.visibility = if (rowsToDisplay > 3) {
            VISIBLE
        } else {
            GONE
        }
        this.viewBinding.weekRow5.visibility = if (rowsToDisplay > 4) {
            VISIBLE
        } else {
            GONE
        }
        this.viewBinding.weekRow6.visibility = if (rowsToDisplay > 5) {
            VISIBLE
        } else {
            GONE
        }
    }

    /**
     * Returns day-view for cell index
     *
     * @param cellIndex Index of cell from 1 to 42
     */
    private fun getDayViewForCellIndex(cellIndex: Int): DayView {
        return when (cellIndex) {
            1 -> this.viewBinding.dayOfMonthText1
            2 -> this.viewBinding.dayOfMonthText2
            3 -> this.viewBinding.dayOfMonthText3
            4 -> this.viewBinding.dayOfMonthText4
            5 -> this.viewBinding.dayOfMonthText5
            6 -> this.viewBinding.dayOfMonthText6
            7 -> this.viewBinding.dayOfMonthText7
            8 -> this.viewBinding.dayOfMonthText8
            9 -> this.viewBinding.dayOfMonthText9
            10 -> this.viewBinding.dayOfMonthText10
            11 -> this.viewBinding.dayOfMonthText11
            12 -> this.viewBinding.dayOfMonthText12
            13 -> this.viewBinding.dayOfMonthText13
            14 -> this.viewBinding.dayOfMonthText14
            15 -> this.viewBinding.dayOfMonthText15
            16 -> this.viewBinding.dayOfMonthText16
            17 -> this.viewBinding.dayOfMonthText17
            18 -> this.viewBinding.dayOfMonthText18
            19 -> this.viewBinding.dayOfMonthText19
            20 -> this.viewBinding.dayOfMonthText20
            21 -> this.viewBinding.dayOfMonthText21
            22 -> this.viewBinding.dayOfMonthText22
            23 -> this.viewBinding.dayOfMonthText23
            24 -> this.viewBinding.dayOfMonthText24
            25 -> this.viewBinding.dayOfMonthText25
            26 -> this.viewBinding.dayOfMonthText26
            27 -> this.viewBinding.dayOfMonthText27
            28 -> this.viewBinding.dayOfMonthText28
            29 -> this.viewBinding.dayOfMonthText29
            30 -> this.viewBinding.dayOfMonthText30
            31 -> this.viewBinding.dayOfMonthText31
            32 -> this.viewBinding.dayOfMonthText32
            33 -> this.viewBinding.dayOfMonthText33
            34 -> this.viewBinding.dayOfMonthText34
            35 -> this.viewBinding.dayOfMonthText35
            36 -> this.viewBinding.dayOfMonthText36
            37 -> this.viewBinding.dayOfMonthText37
            38 -> this.viewBinding.dayOfMonthText38
            39 -> this.viewBinding.dayOfMonthText39
            40 -> this.viewBinding.dayOfMonthText40
            41 -> this.viewBinding.dayOfMonthText41
            42 -> this.viewBinding.dayOfMonthText42
            else -> throw IllegalArgumentException("Cell-Index: $cellIndex out of range (1-42)")
        }
    }

    private fun getDayViewForRowAndColumn(calendarGridRow: Int, calendarGridColumn: Int): DayView {
        return when (calendarGridRow) {
            0 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText1
                    1 -> this.viewBinding.dayOfMonthText2
                    2 -> this.viewBinding.dayOfMonthText3
                    3 -> this.viewBinding.dayOfMonthText4
                    4 -> this.viewBinding.dayOfMonthText5
                    5 -> this.viewBinding.dayOfMonthText6
                    6 -> this.viewBinding.dayOfMonthText7
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            1 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText8
                    1 -> this.viewBinding.dayOfMonthText9
                    2 -> this.viewBinding.dayOfMonthText10
                    3 -> this.viewBinding.dayOfMonthText11
                    4 -> this.viewBinding.dayOfMonthText12
                    5 -> this.viewBinding.dayOfMonthText13
                    6 -> this.viewBinding.dayOfMonthText14
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            2 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText15
                    1 -> this.viewBinding.dayOfMonthText16
                    2 -> this.viewBinding.dayOfMonthText17
                    3 -> this.viewBinding.dayOfMonthText18
                    4 -> this.viewBinding.dayOfMonthText19
                    5 -> this.viewBinding.dayOfMonthText20
                    6 -> this.viewBinding.dayOfMonthText21
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            3 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText22
                    1 -> this.viewBinding.dayOfMonthText23
                    2 -> this.viewBinding.dayOfMonthText24
                    3 -> this.viewBinding.dayOfMonthText25
                    4 -> this.viewBinding.dayOfMonthText26
                    5 -> this.viewBinding.dayOfMonthText27
                    6 -> this.viewBinding.dayOfMonthText28
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            4 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText29
                    1 -> this.viewBinding.dayOfMonthText30
                    2 -> this.viewBinding.dayOfMonthText31
                    3 -> this.viewBinding.dayOfMonthText32
                    4 -> this.viewBinding.dayOfMonthText33
                    5 -> this.viewBinding.dayOfMonthText34
                    6 -> this.viewBinding.dayOfMonthText35
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            5 -> {
                when (calendarGridColumn) {
                    0 -> this.viewBinding.dayOfMonthText36
                    1 -> this.viewBinding.dayOfMonthText37
                    2 -> this.viewBinding.dayOfMonthText38
                    3 -> this.viewBinding.dayOfMonthText39
                    4 -> this.viewBinding.dayOfMonthText40
                    5 -> this.viewBinding.dayOfMonthText41
                    6 -> this.viewBinding.dayOfMonthText42
                    else -> throw IllegalArgumentException("calendarColumn can only be from 0-6 and not $calendarGridColumn")
                }
            }
            else -> throw IllegalArgumentException("calendarRow can only be from 0-5 and not $calendarGridRow")
        }
    }

    private fun numberOfRowsToDisplayInCurrentMonthCalendar(): Int {
        val numberOfPreviousMonthDays = this.numberOfDaysOfPreviousMonthToBeDisplayed()
        val numberOfCurrentMonthDays = this.monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val numberOfNextMonthDays = this.numberOfDaysOfNextMonthToBeDisplayed()

        return ceil((numberOfPreviousMonthDays + numberOfCurrentMonthDays + numberOfNextMonthDays).toDouble() / 7.toDouble()).toInt()
    }

    private fun numberOfDaysTotalToBeDisplayed(): Int {
        return this.numberOfDaysOfPreviousMonthToBeDisplayed() +
                this.monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) +
                this.numberOfDaysOfNextMonthToBeDisplayed()
    }

    private fun numberOfDaysOfPreviousMonthToBeDisplayed(): Int {
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

    private fun getDateForCellIndex(cellIndex: Int): Calendar {
        if (cellIndex <= this.numberOfDaysOfPreviousMonthToBeDisplayed()) {
            val previousMonthCalendar = (this.monthCalendar.clone() as Calendar).apply {
                add(Calendar.MONTH, -1)
            }
            val dayOfPreviousMonth =
                previousMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - this.numberOfDaysOfPreviousMonthToBeDisplayed() + cellIndex

            previousMonthCalendar.set(Calendar.DAY_OF_MONTH, dayOfPreviousMonth)

            return previousMonthCalendar
        } else if (cellIndex <= (this.numberOfDaysOfPreviousMonthToBeDisplayed() + this.monthCalendar.getActualMaximum(
                Calendar.DAY_OF_MONTH
            ))
        ) {
            val tempCalendar = this.monthCalendar.clone() as Calendar
            tempCalendar.set(
                Calendar.DAY_OF_MONTH,
                (cellIndex - this.numberOfDaysOfPreviousMonthToBeDisplayed())
            )
            return tempCalendar
        } else if (cellIndex <= this.numberOfDaysTotalToBeDisplayed()) {
            val nextMonthDate =
                cellIndex - (this.numberOfDaysOfPreviousMonthToBeDisplayed() + this.monthCalendar.getActualMaximum(
                    Calendar.DAY_OF_MONTH
                ))

            return (monthCalendar.clone() as Calendar).apply {
                add(Calendar.MONTH, 1)
                set(Calendar.DAY_OF_MONTH, nextMonthDate)
            }
        } else {
            throw IllegalArgumentException() // ToDo: Add proper explanation
        }
    }

    /**
     * @return Index of cell, starting from 1 and ending at 42
     */
    private fun getIndexOfCellForDate(date: Calendar): Int {
        val daysOfPreviousMonthToDisplay = this.numberOfDaysOfPreviousMonthToBeDisplayed()
        val daysOfNextMonthToDisplay = this.numberOfDaysOfNextMonthToBeDisplayed()
        return when {
            date.isThisCalendarOfPreviousMonthFrom(this.monthCalendar) -> {
                val lastDateOfPreviousMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH)
                val currentDate = date.get(Calendar.DAY_OF_MONTH)

                val cellIndex =
                    daysOfPreviousMonthToDisplay - (lastDateOfPreviousMonth - currentDate)
                if (cellIndex > 0) {
                    cellIndex
                } else {
                    throw IllegalStateException("This date shouldn't be displayed: $date")
                }
            }
            date.isThisCalendarOfNextMonthFrom(this.monthCalendar) -> {
                val daysInCurrentMonth = this.monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                val currentDate = date.get(Calendar.DAY_OF_MONTH)

                if (currentDate > daysOfNextMonthToDisplay) {
                    throw IllegalStateException("This date shouldn't be display: $date")
                } else {
                    val cellIndex = daysOfPreviousMonthToDisplay + daysInCurrentMonth + currentDate

                    cellIndex
                }
            }
            else -> {
                val currentDate = date.get(Calendar.DAY_OF_MONTH)
                val cellIndex = daysOfPreviousMonthToDisplay + currentDate

                cellIndex
            }
        }
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
