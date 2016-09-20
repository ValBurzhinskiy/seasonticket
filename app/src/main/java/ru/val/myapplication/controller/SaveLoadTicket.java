package ru.val.myapplication.controller;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.val.myapplication.model.Seasonticket;

public class SaveLoadTicket {
    private static final String FILE_NAME = "seasonticket.txt";

    public static void SaveTicket (Context context, Seasonticket ticket) {
        FileOutputStream output = null;
            try {
                output = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                String str = ticket.getStartDate() + "\n" + ticket.getEndDate() + "\n" + ticket.getMaxCount();
                for (String item : ticket.getVisits())
                    str += "\n" + item;
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

    public static void LoadTicket (Context context) {

        Seasonticket ticket = Seasonticket.getInstance();

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
            ticket.setStartDate(list.get(0));
            ticket.setEndDate(list.get(1));
            ticket.setMaxCount(Integer.parseInt(list.get(2)));
            ticket.clearVisits();
            for (int i = 3; i < list.size(); i++)
                ticket.add(list.get(i));
        }
    }
}
