package com.varunbarad.highlightable_calendar_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.varunbarad.highlightable_calendar_view.databinding.ViewHighlightableCalendarBinding
import com.varunbarad.highlightable_calendar_view.util.isSameMonth
import com.varunbarad.highlightable_calendar_view.util.isToday
import java.text.DateFormatSymbols
import java.util.*

class HighlightableCalendarView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributes, defStyleAttr) {
    private lateinit var viewBinding: ViewHighlightableCalendarBinding

    @ColorInt
    private val dayTextColorSelected: Int
    @ColorInt
    private val dayBackgroundColorSelected: Int
    @ColorInt
    private val dayTextColorDisabled: Int
    @ColorInt
    private val dayBackgroundColorDisabled: Int
    @ColorInt
    private val dayOfMonthTextColor: Int
    @ColorInt
    private val dayOfWeekTextColor: Int
    @ColorInt
    private val weekLayoutBackgroundColor: Int
    @ColorInt
    private val calendarTitleTextColor: Int
    @ColorInt
    private val calendarTitleBackgroundColor: Int
    @ColorInt
    private val currentDayTextColor: Int
    @ColorInt
    private val calendarBackgroundColor: Int

    var calendarListener: CalendarListener? = null
    private var currentCalendar: Calendar? = null
    private var locale: Locale? = null
    private var lastSelectedDay: Date? = null
    var customTypeface: Typeface? = null

    var firstDayOfWeek: Int = Calendar.SUNDAY
    var decorators: List<DayDecorator>? = null

    private var currentMonthIndex: Int = 0
    private var isOverflowDateVisible: Boolean = true

    init {
        this.viewBinding = ViewHighlightableCalendarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        if (attributes != null) {
            val typedArray = context.obtainStyledAttributes(
                attributes,
                R.styleable.HighlightableCalendarView,
                0,
                0
            )

            calendarBackgroundColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_calendarBackgroundColor,
                ContextCompat.getColor(context, R.color.white)
            )
            calendarTitleBackgroundColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_titleLayoutBackgroundColor,
                ContextCompat.getColor(context, R.color.white)
            )
            calendarTitleTextColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_calendarTitleTextColor,
                ContextCompat.getColor(context, R.color.darkGrey)
            )
            weekLayoutBackgroundColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_weekLayoutBackgroundColor,
                ContextCompat.getColor(context, R.color.white)
            )
            dayOfWeekTextColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayOfWeekTextColor,
                ContextCompat.getColor(context, R.color.darkGrey)
            )
            dayOfMonthTextColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayOfMonthTextColor,
                ContextCompat.getColor(context, R.color.darkGrey)
            )
            dayBackgroundColorDisabled = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayBackgroundColorDisabled,
                ContextCompat.getColor(context, R.color.day_backgroundColor_disabled)
            )
            dayTextColorDisabled = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayTextColorDisabled,
                ContextCompat.getColor(context, R.color.day_textColor_disabled)
            )
            dayBackgroundColorSelected = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayBackgroundColorSelected,
                ContextCompat.getColor(context, R.color.day_backgroundColor_selected)
            )
            dayTextColorSelected = typedArray.getColor(
                R.styleable.HighlightableCalendarView_dayTextColorSelected,
                ContextCompat.getColor(context, R.color.white)
            )
            currentDayTextColor = typedArray.getColor(
                R.styleable.HighlightableCalendarView_currentDayOfMonthColor,
                ContextCompat.getColor(context, R.color.currentDay_textColor)
            )

            typedArray.recycle()
        } else {
            calendarBackgroundColor = ContextCompat.getColor(context, R.color.white)
            calendarTitleBackgroundColor = ContextCompat.getColor(context, R.color.white)
            calendarTitleTextColor = ContextCompat.getColor(context, R.color.darkGrey)
            weekLayoutBackgroundColor = ContextCompat.getColor(context, R.color.white)
            dayOfWeekTextColor = ContextCompat.getColor(context, R.color.darkGrey)
            dayOfMonthTextColor = ContextCompat.getColor(context, R.color.darkGrey)
            dayBackgroundColorDisabled =
                ContextCompat.getColor(context, R.color.day_backgroundColor_disabled)
            dayTextColorDisabled = ContextCompat.getColor(context, R.color.day_textColor_disabled)
            dayBackgroundColorSelected =
                ContextCompat.getColor(context, R.color.day_backgroundColor_selected)
            dayTextColorSelected = ContextCompat.getColor(context, R.color.white)
            currentDayTextColor = ContextCompat.getColor(context, R.color.currentDay_textColor)
        }

        this.viewBinding.buttonPreviousMonth.setOnClickListener {
            currentMonthIndex--
            currentCalendar = Calendar.getInstance(Locale.getDefault())
            currentCalendar?.add(Calendar.MONTH, currentMonthIndex)
            refreshCalendar(currentCalendar)

            currentCalendar?.let { calendarListener?.onMonthChanged(it.time) }
        }

        this.viewBinding.buttonNextMonth.setOnClickListener {
            currentMonthIndex++
            currentCalendar = Calendar.getInstance(Locale.getDefault())
            currentCalendar?.add(Calendar.MONTH, currentMonthIndex)
            refreshCalendar(currentCalendar)

            currentCalendar?.let { calendarListener?.onMonthChanged(it.time) }
        }

        refreshCalendar(Calendar.getInstance(context.resources.configuration.locale))
    }

    /**
     * Display calendar title with next previous month button
     */
    private fun initializeTitleLayout() {
        this.viewBinding.titleLayout.setBackgroundColor(this.calendarTitleBackgroundColor)

        currentCalendar?.let { calendar ->
            val dateText = DateFormatSymbols(locale)
                .shortMonths[calendar.get(Calendar.MONTH)]
                .toString()
                .capitalize()

            this.viewBinding.monthTitle.setTextColor(this.calendarTitleTextColor)
            this.viewBinding.monthTitle.text = "$dateText ${calendar.get(Calendar.YEAR)}"

            this.customTypeface?.let { this.viewBinding.monthTitle.setTypeface(it, Typeface.BOLD) }
        }
    }

    /**
     * Initialize the calendar week layout, considers start day
     */
    private fun initializeWeekLayout() {
        this.viewBinding.weekLayout.setBackgroundColor(this.weekLayoutBackgroundColor)

        val weekDaysArray = DateFormatSymbols(locale).shortWeekdays
        for (i in 1 until weekDaysArray.size) {
            val dayOfTheWeekName = if (weekDaysArray[i].length > 3) {
                weekDaysArray[i].substring(0, 3).toUpperCase()
            } else {
                weekDaysArray[i]
            }

            currentCalendar?.let { cal ->
                when (getWeekIndex(i, cal)) {
                    1 -> {
                        this.viewBinding.weekDay1.text = dayOfTheWeekName
                        this.viewBinding.weekDay1.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay1.typeface = typeface
                        }
                    }
                    2 -> {
                        this.viewBinding.weekDay2.text = dayOfTheWeekName
                        this.viewBinding.weekDay2.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay2.typeface = typeface
                        }
                    }
                    3 -> {
                        this.viewBinding.weekDay3.text = dayOfTheWeekName
                        this.viewBinding.weekDay3.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay3.typeface = typeface
                        }
                    }
                    4 -> {
                        this.viewBinding.weekDay4.text = dayOfTheWeekName
                        this.viewBinding.weekDay4.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay4.typeface = typeface
                        }
                    }
                    5 -> {
                        this.viewBinding.weekDay5.text = dayOfTheWeekName
                        this.viewBinding.weekDay5.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay5.typeface = typeface
                        }
                    }
                    6 -> {
                        this.viewBinding.weekDay6.text = dayOfTheWeekName
                        this.viewBinding.weekDay6.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay6.typeface = typeface
                        }
                    }
                    7 -> {
                        this.viewBinding.weekDay7.text = dayOfTheWeekName
                        this.viewBinding.weekDay7.setTextColor(this.dayOfWeekTextColor)

                        val typeface = customTypeface
                        if (typeface != null) {
                            this.viewBinding.weekDay7.typeface = typeface
                        }
                    }
                }
            }
        }
    }

    private fun setDaysInCalendar() {
        val cal = Calendar.getInstance(locale)
        currentCalendar?.let { cal.time = it.time }
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.firstDayOfWeek = firstDayOfWeek
        val firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK)

        // Calculate dayOfMonthIndex
        var dayOfMonthIndex = getWeekIndex(firstDayOfMonth, cal)
        val actualMaximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val startCal = cal.clone() as Calendar
        // Add required number of days
        startCal.add(Calendar.DATE, -(dayOfMonthIndex - 1))
        val monthEndIndex = 42 - (actualMaximum + dayOfMonthIndex - 1)

        for (i in 1..42) {
            val dayView = getDayViewForDay(i)

            // Apply the default styles
            dayView.setOnClickListener(null)
            dayView.bind(startCal.time, decorators ?: emptyList())
            dayView.visibility = VISIBLE

            customTypeface?.let { dayView.typeface = it }

            if (isSameMonth(cal, startCal)) {
                dayView.setOnClickListener(onDayOfMonthClickListener)
                dayView.setBackgroundColor(calendarBackgroundColor)
                dayView.setTextColor(dayOfWeekTextColor)

                // Set current day color
                markDayAsCurrentDay(startCal)
            } else {
                dayView.setBackgroundColor(dayBackgroundColorDisabled)
                dayView.setTextColor(dayTextColorDisabled)

                if (!isOverflowDateVisible) {
                    dayView.visibility = GONE
                } else if ((i > 35) && ((monthEndIndex.toFloat() / 7f) >= 1)) {
                    dayView.visibility = GONE
                }
            }
            dayView.decorate()

            startCal.add(Calendar.DATE, 1)
            dayOfMonthIndex++
        }

        // If the last week row has no visible days, hide it or show it in case it does
        this.viewBinding.weekRow6.visibility = this.viewBinding.dayOfMonthText36.visibility
    }

    private fun clearDayOfTheMonthStyle(currentDate: Date?) {
        if (currentDate != null) {
            val cal: Calendar = getTodaysCalendar()
            cal.firstDayOfWeek = firstDayOfWeek
            cal.time = currentDate

            val dayView: DayView = getDayOfMonthText(cal)
            dayView.setBackgroundColor(calendarBackgroundColor)
            dayView.setTextColor(dayOfWeekTextColor)
            dayView.decorate()
        }
    }

    private fun getDayOfMonthText(currentCalendar: Calendar): DayView {
        return getDayViewForDay(getDayIndexByDate(currentCalendar))
    }

    private fun getDayIndexByDate(currentCalendar: Calendar): Int {
        val monthOffset = getMonthOffset(currentCalendar)
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val index = currentDay + monthOffset
        return index
    }

    private fun getMonthOffset(currentCalendar: Calendar): Int {
        val cal = Calendar.getInstance()
        cal.firstDayOfWeek = firstDayOfWeek
        cal.time = currentCalendar.time
        cal.set(Calendar.DAY_OF_MONTH, 1)

        val firstDayWeekPosition = cal.firstDayOfWeek
        val dayPosition = cal.get(Calendar.DAY_OF_WEEK)

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1
        } else {
            if (dayPosition == 1) {
                return 6
            } else {
                return dayPosition - 2
            }
        }
    }

    private fun getWeekIndex(weekIndex: Int, currentCalendar: Calendar): Int {
        return if (currentCalendar.firstDayOfWeek == Calendar.SUNDAY) {
            weekIndex
        } else {
            if (weekIndex == 1) {
                7
            } else {
                (weekIndex - 1)
            }
        }
    }

    private fun getTodaysCalendar(): Calendar {
        val tempLocale = locale
        val currentCal = if (tempLocale != null) {
            Calendar.getInstance(tempLocale)
        } else {
            Calendar.getInstance()
        }
        currentCal.firstDayOfWeek = firstDayOfWeek
        return currentCal
    }

    fun refreshCalendar(currentCal: Calendar?) {
        this.currentCalendar = currentCal
        this.currentCalendar?.firstDayOfWeek = firstDayOfWeek
        this.locale = this.context.resources.configuration.locale

        // Set date title
        initializeTitleLayout()

        // Set week days title
        initializeWeekLayout()

        // Initialize and set days in calendar
        setDaysInCalendar()
    }

    fun markDayAsCurrentDay(cal: Calendar?) {
        if ((cal != null) && isToday(cal)) {
            val dayView = getDayOfMonthText(cal)
            dayView.setTextColor(currentDayTextColor)
        }
    }

    fun markDayAsSelectedDay(date: Date) {
        val currentCal = getTodaysCalendar()
        currentCal.firstDayOfWeek = firstDayOfWeek
        currentCal.time = date

        // Clear previous marks
        clearDayOfTheMonthStyle(lastSelectedDay)

        // Store current value as last value
        lastSelectedDay = date

        // Mark current day as selected
        val dayView = getDayOfMonthText(currentCal)
        dayView.setBackgroundColor(dayBackgroundColorSelected)
        dayView.setTextColor(dayTextColorSelected)
    }

    private val onDayOfMonthClickListener: OnClickListener = OnClickListener { view ->
        val dayView = view as DayView

        currentCalendar?.let {
            // Fire event
            val cal = Calendar.getInstance()
            cal.firstDayOfWeek = firstDayOfWeek
            cal.time = it.time
            cal.set(Calendar.DAY_OF_MONTH, dayView.text.toString().toInt())
            markDayAsSelectedDay(cal.time)

            // Set the current day color
            markDayAsCurrentDay(it)

            calendarListener?.onDateSelected(cal.time)
        }
    }

    private fun getDayViewForDay(day: Int): DayView {
        return when (day) {
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
            else -> throw IllegalArgumentException("Day $day not possible")
        }
    }
}
