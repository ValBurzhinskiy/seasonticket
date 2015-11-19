package ru.val.myapplication.model;


//import android.content.Context;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStreamReader;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.val.myapplication.util.DateConverter;

public class Seasonticket {
    private static final String FILE_NAME = "seasonticket";
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

    public void createSeasonticket(String mStartDate, String mEndDate, int mMaxCount) {
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mMaxCount = mMaxCount;
        this.visits.clear();
    }

    public String getPeriod() {
        if (!this.mStartDate.isEmpty())
            return this.mStartDate.concat(" - ").concat(this.mEndDate);
        return "";
    }

    public int getMaxCount() {
        return this.mMaxCount;
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
            Log.d("myLog", "visits.size() = " + visits.size() + "; mMaxCount = " + mMaxCount);
            if (currentCalendar.before(endCalendar) & visits.size() < mMaxCount)
                valid = true;
            else
                valid = false;
        }
        return valid;
    }


//    public void writeToFile(Context context){
//        try{
//            FileOutputStream output = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//            output.write(mStartDate.getBytes());
//            output.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void readFile(Context context) {
//
//        FileInputStream stream;
//        StringBuilder sb = new StringBuilder();
//        String line;
//
//        try {
//            stream = context.openFileInput(FILE_NAME);
//
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                }
//            } finally {
//                stream.close();
//            }
//
////            srok = sb.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
