package ru.val.myapplication.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.val.myapplication.R;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TicketViewHolder> {
    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView dateVisit;

        TicketViewHolder(View itemView) {
            super(itemView);
            dateVisit = (TextView) itemView.findViewById(R.id.tvDateVisit);
        }
    }

    List<String> visits;

    public RVAdapter(List<String> visits) {
        this.visits = visits;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        TicketViewHolder pvh = new TicketViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TicketViewHolder personViewHolder, int i) {
        personViewHolder.dateVisit.setText(visits.get(i));
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }
}
