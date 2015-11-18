package ru.val.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.FileOutputStream;
import java.util.Calendar;

import ru.val.myapplication.util.TypeTicket;


public class FragmentSecond extends Fragment implements AdapterView.OnItemSelectedListener{

    private Calendar now = Calendar.getInstance();
    private Calendar now2 = Calendar.getInstance();
    private String startDate, endDate;
    private int maxCount;

    private TextView tvStartDate, tvEndDate;
    private ImageButton btnStartDate;
    private Button btnCreate;
    private Spinner spinnerType;

    public static FragmentSecond newInstance() {
        return new FragmentSecond();
    }

    public FragmentSecond() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        tvStartDate = (TextView) rootView.findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) rootView.findViewById(R.id.tvEndDate);
        spinnerType = (Spinner) rootView.findViewById(R.id.spinnerType);
        btnCreate = (Button) rootView.findViewById(R.id.btnCreate);
        btnStartDate = (ImageButton) rootView.findViewById(R.id.btnStartDate);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        startDate = "" + now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR);
        tvStartDate.setText(String.format(getResources().getString(R.string.start_date_format), startDate));

        now2.add(Calendar.DATE, 28);
        endDate = "" + now2.get(Calendar.DAY_OF_MONTH) + "/" + (now2.get(Calendar.MONTH) + 1) + "/" + now2.get(Calendar.YEAR);
        tvEndDate.setText(String.format(getResources().getString(R.string.end_date_format), endDate));

        spinnerType.setSelection(2);
        spinnerType.setOnItemSelectedListener(this);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        mDateSetListener,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder str = new StringBuilder();
                str.append("Колличество тренировок = " + maxCount + "\n" +
                        String.format(getResources().getString(R.string.start_date_format), startDate) + "\n" +
                        String.format(getResources().getString(R.string.end_date_format), endDate));

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Новый абонемент");
                builder.setMessage(str);
                builder.setPositiveButton("OK", null);
                builder.show();

                writeToFile();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            startDate = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            tvStartDate.setText(String.format(getResources().getString(R.string.start_date_format), startDate));

            now2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            now2.set(Calendar.MONTH, monthOfYear);
            now2.set(Calendar.YEAR, year);
            now2.add(Calendar.DATE, 28);
            endDate = "" + now2.get(Calendar.DAY_OF_MONTH) + "/" + (now2.get(Calendar.MONTH) + 1) + "/" + now2.get(Calendar.YEAR);
            tvEndDate.setText(String.format(getResources().getString(R.string.end_date_format), endDate));
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                maxCount = TypeTicket.FOUR.getMaxCount();
                break;
            case 1:
                maxCount = TypeTicket.EIGHT.getMaxCount();
                break;
            case 2:
                maxCount = TypeTicket.TWELVE.getMaxCount();
                break;
            case 3:
                maxCount = TypeTicket.SIXTEEN.getMaxCount();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void writeToFile(){
        String mFileName = "seasonticket";
        try{
            FileOutputStream output = getActivity().openFileOutput(mFileName, Context.MODE_PRIVATE);
            output.write(startDate.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
