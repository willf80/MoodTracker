package com.appinlab.moodtracker.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public static final String TAG = "MoodTracker";

    public static final String[] daysString = new String[]{
            "Aujourd'hui",
            "Hier",
            "Avant-hier",
            "Il y a trois jours",
            "Il y a quatre jours",
            "Il y a cinq jours",
            "Il y a six jours",
            "Il y a une semaine",
    };

    public static String dateToStringFormat(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static int getDateDiffBetweenTwoDate(Date dateStart, Date dateEnd) {
        Date date = Calendar.getInstance().getTime();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(dateStart);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(dateEnd);

        int startDayOfYear = calendarStart.get(Calendar.DAY_OF_YEAR);
        int endDayOfYear = calendarEnd.get(Calendar.DAY_OF_YEAR);

        return endDayOfYear - startDayOfYear;
    }
}
