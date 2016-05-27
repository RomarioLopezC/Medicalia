package com.example.user.medicalia.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Day {

    private static final String DATE_FORMAT = "yyy-MM-dd'T'HH:mm";
    private static final String AM = "AM";
    private static final String PM = "PM";
    public static final String AVAILABLE = "Disponible";
    public static final String LUNCH = "Comida";
    public static final String OCCUPIED = "No Disponible";

    private List<Appointment> appointments;
    private List<Integer> hoursAppointment;
    private List<Hour> hours;
    private Schedule schedule;

    private Calendar calendarStart;
    private Calendar calendarEnd;
    private Calendar calendarStartLunch;
    private Calendar calendarEndLunch;
    private SimpleDateFormat dateFormat;

    private int startHour;
    private int endHour;
    private int startMinutes;
    private int endMinutes;

    private int startLunchHour;
    private int endLunchHour;

    public Day(Schedule schedule) {
        hours = new ArrayList<>();

        appointments = schedule.getAppointments();
        hoursAppointment = new ArrayList<>();

        for (Appointment appointment : appointments) {
            hoursAppointment.add(appointment.getStartTimeCalendar().get(Calendar.HOUR_OF_DAY));
        }

        calendarStart = GregorianCalendar.getInstance();
        calendarEnd = GregorianCalendar.getInstance();
        calendarStartLunch = GregorianCalendar.getInstance();
        calendarEndLunch = GregorianCalendar.getInstance();
        dateFormat = new SimpleDateFormat(DATE_FORMAT);

        setDateCalendar(schedule.getStart(), calendarStart);
        setDateCalendar(schedule.getEnd(), calendarEnd);
        setDateCalendar(schedule.getStartLunch(), calendarStartLunch);
        setDateCalendar(schedule.getEndLunch(), calendarEndLunch);

        startHour = calendarStart.get(Calendar.HOUR_OF_DAY);
        startMinutes = calendarStart.get(Calendar.MINUTE);
        endHour = calendarEnd.get(Calendar.HOUR_OF_DAY);
        endMinutes = calendarEnd.get(Calendar.MINUTE);
        startLunchHour = calendarStartLunch.get(Calendar.HOUR_OF_DAY);
        endLunchHour = calendarEndLunch.get(Calendar.HOUR_OF_DAY);

        createListHours();
    }

    private void createListHours() {

        for (int i = startHour; i <= endHour; i++) {
            //Log.d("Day", String.valueOf(i));
            String info = AVAILABLE;
            String hoursFormat = AM;
            int hour = i;

            if (hoursAppointment.contains(hour)){
                info = OCCUPIED;
            }

            if (hour >= startLunchHour && hour <= endLunchHour) {
                info = LUNCH;
            }

            //Para ver si es AM o PM
            if (hour / 12 == 1) {
                if (hour != 12) {
                    hour = i - 12;
                }
                hoursFormat = PM;
            }

            //Para agregarle el 0 cuando sema multiplo de 10
            String minutes = String.valueOf(startMinutes);

            if (startMinutes < 10) {
                minutes = minutes + "0";
            }


            String hourString = String.valueOf(hour) + ":" + minutes;
            hours.add(new Hour(hourString, hoursFormat, info));
        }
    }

    private void setDateCalendar(String dateString, Calendar calendar) {
        try {
            Date date = dateFormat.parse(dateString);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public List<Hour> getHours() {
        return hours;
    }
}
