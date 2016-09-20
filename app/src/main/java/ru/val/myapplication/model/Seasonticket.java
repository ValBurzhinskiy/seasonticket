package ru.val.myapplication.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.val.myapplication.controller.DateConverter;

public class Seasonticket {

    private String mStartDate, mEndDate;
    private int mMaxCount;
    private List<String> visits;


    private static Seasonticket ourInstance = new Seasonticket();

    public static Seasonticket getInstance() {
        return ourInstance;
    }

    private Seasonticket() {
        mStartDate = "";
        mEndDate = "";
        mMaxCount = 0;
        visits = new ArrayList<>();
    }


    public void setStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public void setEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }


    public void clearVisits() {
        this.visits.clear();
    }

    public void setMaxCount(int mMaxCount) {
        this.mMaxCount = mMaxCount;
    }

    public String getPeriod() {
        if (!this.mStartDate.isEmpty())
            return this.mStartDate.concat(" - ").concat(this.mEndDate);
        return "";
    }

    public int getMaxCount() {
        return this.mMaxCount;
    }
    public String getStartDate() {
        return this.mStartDate;
    }

    public String getEndDate() {
        return this.mEndDate;
    }


    public List<String> getVisits() {
        return this.visits;
    }

    public void add(String str) {
        this.visits.add(str);
    }

    public boolean isValid() {
        // Проверка сроков и кол-ва посещений
        Calendar currentCalendar = Calendar.getInstance();
        boolean valid = false;
        if (!mEndDate.isEmpty()) {
            Calendar endCalendar = DateConverter.stringToDate(mEndDate);
            valid = currentCalendar.before(endCalendar) & visits.size() < mMaxCount;
        }
        return valid;
    }
}
