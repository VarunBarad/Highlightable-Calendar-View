package com.varunbarad.highlightable_calendar_view;

import java.util.Date;

public interface OnMonthChangeListener {
    // ToDo: Find a way to pass old and new months to this
    void onMonthChanged(Date time);
}
