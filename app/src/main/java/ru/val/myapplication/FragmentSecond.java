package ru.val.myapplication;


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

import java.util.Calendar;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import ru.val.myapplication.model.Seasonticket;
import ru.val.myapplication.model.TypeTicket;


public class FragmentSecond extends Fragment implements AdapterView.OnItemSelectedListener {

    private Seasonticket seasonticket = Seasonticket.getInstance();

    private Calendar nowCalendar;
    private Calendar endCalendar;
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
        nowCalendar = Calendar.getInstance();
        startDate = "" + nowCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (nowCalendar.get(Calendar.MONTH) + 1) + "/" + nowCalendar.get(Calendar.YEAR);
        tvStartDate.setText(String.format(getResources().getString(R.string.start_date_format), startDate));

        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DATE, 28);
        endDate = "" + endCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (endCalendar.get(Calendar.MONTH) + 1) + "/" + endCalendar.get(Calendar.YEAR);
        tvEndDate.setText(String.format(getResources().getString(R.string.end_date_format), endDate));

        spinnerType.setSelection(2);
        spinnerType.setOnItemSelectedListener(this);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        mDateSetListener,
                        nowCalendar.get(Calendar.YEAR),
                        nowCalendar.get(Calendar.MONTH),
                        nowCalendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder str = new StringBuilder();
                if (!seasonticket.isValid()) {
                    str.append("Колличество тренировок = ").append(maxCount).append("\n");
                    str.append(String.format(getResources().getString(R.string.start_date_format), startDate)).append("\n");
                    str.append(String.format(getResources().getString(R.string.end_date_format), endDate));

                    //Вынести ли на кнопку ОК диалога? Чтобы дать возможность отменить.
                    seasonticket.createSeasonticket(startDate, endDate, maxCount);
                    //Обновление первого фрагмента
                } else
                    str.append("Есть действительный билет");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Новый абонемент");
                builder.setMessage(str);
                builder.setPositiveButton("OK", null);
                builder.show();


            }
        });

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            startDate = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            tvStartDate.setText(String.format(getResources().getString(R.string.start_date_format), startDate));

            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.add(Calendar.DATE, 28);
            endDate = "" + endCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (endCalendar.get(Calendar.MONTH) + 1) + "/" + endCalendar.get(Calendar.YEAR);
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


}
