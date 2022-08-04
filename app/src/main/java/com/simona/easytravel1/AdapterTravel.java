package com.simona.easytravel1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdapterTravel extends RecyclerView.Adapter<AdapterTravel.ClassViewHolder> {

    List<Travel> arrayTravels = new ArrayList<>();
    EditMarkTravelInterface editTravelInterface;
    Context context;

    public AdapterTravel(Context context, EditMarkTravelInterface editTravelInterface) {
        this.editTravelInterface = editTravelInterface;
        this.context = context;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder { //} implements View.OnLongClickListener, View.OnClickListener {

        ImageView pictureIdIV, favoriteTravelIV, shareIV;
        TextView travelNameTV, destinationTV, priceTV;
        TextView temporaryIdTV, startDateTV, endDateTV;
        RatingBar ratingBar;
        EditMarkTravelInterface editTravelInterf;

        public ClassViewHolder(@NonNull View itemView, EditMarkTravelInterface editTravelInterf) {
            super(itemView);
            temporaryIdTV = itemView.findViewById(R.id.tv_id_temporar);
            pictureIdIV = itemView.findViewById(R.id.destinationPictureIV);
            travelNameTV = itemView.findViewById(R.id.travelNameTV);
            destinationTV = itemView.findViewById(R.id.destinationTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            favoriteTravelIV = itemView.findViewById(R.id.iv_calatorieFavorita);
            shareIV = itemView.findViewById(R.id.shareIV);
            startDateTV = itemView.findViewById(R.id.startDateTV);
            endDateTV = itemView.findViewById(R.id.endDateTV);

            this.editTravelInterf = editTravelInterf;

            itemView.setOnClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                if (editTravelInterf != null && clickedPosition != RecyclerView.NO_POSITION) {
                    editTravelInterf.displayTravel(arrayTravels.get(clickedPosition));
                }
            });

            itemView.setOnLongClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                if (editTravelInterf != null && clickedPosition != RecyclerView.NO_POSITION) {
                    editTravelInterf.editTravel(arrayTravels.get(clickedPosition));
                    return true;
                }
                return false;
            });

            favoriteTravelIV.setOnClickListener(view -> {
                int clickedPosition = getAdapterPosition();
                if (editTravelInterf != null && clickedPosition != RecyclerView.NO_POSITION) {
                    editTravelInterf.markAsFavorite(arrayTravels.get(clickedPosition));
                }
            });

            shareIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if (editTravelInterf != null && clickedPosition != RecyclerView.NO_POSITION) {

                       editTravelInterf.shareThisTravel(arrayTravels.get(clickedPosition));
                    }
                }
            });
        }

    }


    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_travel_list,
                        parent,
                        false),
                editTravelInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        if (arrayTravels != null) {
            Travel currentTravel = arrayTravels.get(position);

            holder.temporaryIdTV.setText(String.valueOf(currentTravel.getId()));
            holder.travelNameTV.setText(currentTravel.getTravelName());
            holder.destinationTV.setText(currentTravel.getDestination());
            holder.priceTV.setText("Pret: $" + currentTravel.getPrice());
            holder.ratingBar.setRating(currentTravel.getRating());

            if (currentTravel.getUriPicture() == null){
                holder.pictureIdIV.setImageResource(R.drawable.this_green);
            } else {
                holder.pictureIdIV.setImageBitmap(convertStringToBitmap(currentTravel.getUriPicture()));
            }
//            holder.pictureIdIV.setImageBitmap(convertStringToBitmap(currentTravel.getUriPoza()));
            if (currentTravel.isFavorite()) {
                holder.favoriteTravelIV.setImageResource(R.drawable.favorite_24);
            } else {
                holder.favoriteTravelIV.setImageResource(R.drawable.favorite_border_24);
            }

            holder.startDateTV.setText(currentTravel.getStartDate());
            holder.endDateTV.setText(currentTravel.getEndDate());

        }
    }

    @Override
    public int getItemCount() {
        return arrayTravels.size();
    }

    private Bitmap convertStringToBitmap(String uriPrimit) {
        Uri uri = Uri.parse(uriPrimit);
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap pictureToDisplay = BitmapFactory.decodeStream(inputStream);
        return pictureToDisplay;
    }


    public void setTravels(List<Travel> sirTrv) {
        this.arrayTravels = sirTrv;
        notifyDataSetChanged();
    }

    public Travel getTravelObjectAtIndex(int poz) {
        return arrayTravels.get(poz);
    }


}
