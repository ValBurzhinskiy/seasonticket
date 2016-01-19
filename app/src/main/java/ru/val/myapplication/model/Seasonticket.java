package ru.val.myapplication.model;


import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.val.myapplication.interfaces.Observable;
import ru.val.myapplication.interfaces.Observer;
import ru.val.myapplication.util.DateConverter;

public class Seasonticket implements Observable {
    private static final String FILE_NAME = "seasonticket.txt";
    private String mStartDate, mEndDate;
    private int mMaxCount;
    private List<String> visits;

    //список слушателей
    private List<Observer> observers;


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
        notifyObservers();
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
            valid = currentCalendar.before(endCalendar) & visits.size() < mMaxCount;
        }
        return valid;
    }


    public void writeToFile(Context context) {
        FileOutputStream output = null;
        if (!this.mStartDate.isEmpty()) {
            try {
                output = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                String str = mStartDate + "\n" + mEndDate + "\n" + mMaxCount;
                for (int i = 0; i < visits.size(); i++)
                    str += "\n" + visits.get(i);
                output.write(str.getBytes());
            } catch (Exception e) {
                Log.d("myLog", "Ошибка записи: " + e.getMessage());
            } finally {
                try {
                    if (output != null)
                        output.close();
                } catch (Exception e) {
                    Log.d("myLog", "Ошибка закрытия файла: " + e.getMessage());
                }
            }
        }
    }

    public void readFile(Context context) {

        FileInputStream input = null;
        List<String> list = new ArrayList<>();
        String line;
        try {
            input = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            Log.d("myLog", "Ошибка чтения: " + e.getMessage());
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (Exception e) {
                Log.d("myLog", "Ошибка закрытия файла: " + e.getMessage());
            }
        }
        if (!list.isEmpty()) {
            this.mStartDate = list.get(0);
            this.mEndDate = list.get(1);
            this.mMaxCount = Integer.parseInt(list.get(2));
            this.visits.clear();
            for (int i = 3; i < list.size(); i++)
                this.visits.add(list.get(i));
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        Log.d("myLogs", "o1: " + o);

    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
//            observer.update(getPeriod(), mMaxCount);
        }
    }
}
