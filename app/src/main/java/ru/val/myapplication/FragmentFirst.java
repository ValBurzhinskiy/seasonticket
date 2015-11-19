package ru.val.myapplication;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.val.myapplication.model.Seasonticket;
import ru.val.myapplication.util.DateConverter;
import ru.val.myapplication.util.DividerItemDecoration;
import ru.val.myapplication.util.RVAdapter;


public class FragmentFirst extends Fragment implements View.OnClickListener {
    private Seasonticket seasonticket;

    private int currentCount, maxCount;
    private List<String> visits;
    private String period;

    private FloatingActionButton fab;
    private TextView tvProgressValue;
    private TextView tvPeriod;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RVAdapter adapter;


    public static FragmentFirst newInstance() {
        return new FragmentFirst();
    }

    public FragmentFirst() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        tvProgressValue = (TextView) rootView.findViewById(R.id.tvProgressValue);
        tvPeriod = (TextView) rootView.findViewById(R.id.tvPeriod);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Блок инициализации билета. Загрузка действующего билета
        seasonticket = Seasonticket.getInstance();
        period = seasonticket.getPeriod();
        // оставить только seasonticket.getVisits()
        visits = seasonticket.getVisits();
        currentCount = visits.size();
        maxCount = seasonticket.getMaxCount();

        //

        progressBar.setMax(maxCount);
        progressBar.setProgress(currentCount);
        tvPeriod.setText(String.format(getResources().getString(R.string.validity_period), period));
        tvProgressValue.setText(String.format(getResources().getString(R.string.progress_value), currentCount, maxCount));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        adapter = new RVAdapter(visits);
        adapter = new RVAdapter(seasonticket.getVisits());
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (visits.size() < maxCount) {
            Calendar now = Calendar.getInstance();
            String currentDate = DateConverter.dateToString(getActivity(), now);

            seasonticket.add(String.format(getResources().getString(R.string.item_date), currentDate));
            progressBar.setProgress(visits.size());
            tvProgressValue.setText(String.format(getResources().getString(R.string.progress_value), visits.size(), maxCount));
            //

            adapter.notifyDataSetChanged();
        } else
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }


}
