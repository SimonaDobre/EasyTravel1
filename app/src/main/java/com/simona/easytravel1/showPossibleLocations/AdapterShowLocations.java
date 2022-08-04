package com.simona.easytravel1.showPossibleLocations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.easytravel1.R;

import java.util.ArrayList;

public class AdapterShowLocations extends RecyclerView.Adapter<AdapterShowLocations.ClassViewHolderShowPossibleLocations> {

    Context context;
    ArrayList<String> arrayPossibleLocations;
    SelectLocationInterface selectLocationInterface;

    public AdapterShowLocations(Context context, ArrayList<String> arrayPossibleLocations, SelectLocationInterface selectareLocationInterface) {
        this.context = context;
        this.arrayPossibleLocations = arrayPossibleLocations;
        this.selectLocationInterface = selectareLocationInterface;
    }

    class ClassViewHolderShowPossibleLocations extends RecyclerView.ViewHolder {

        TextView locationTV;
        SelectLocationInterface sli;

        public ClassViewHolderShowPossibleLocations(@NonNull View itemView, SelectLocationInterface sli) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.locationTV);
            this.sli = sli;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sli.selectDestinationCity(getAdapterPosition());
                }
            });
        }
    }


    @NonNull
    @Override
    public ClassViewHolderShowPossibleLocations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolderShowPossibleLocations(LayoutInflater
                .from(context).inflate(R.layout.row_possible_locations,
                        parent, false), selectLocationInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolderShowPossibleLocations holder, int position) {
        holder.locationTV.setText(arrayPossibleLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayPossibleLocations.size();
    }


}
