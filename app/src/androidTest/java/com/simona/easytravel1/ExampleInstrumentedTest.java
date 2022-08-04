package com.simona.nasa1;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.asteroidDetails.UtilsAsteroidDetails;
import com.simona.nasa1.closestPassing.AsteroidClosenessDates;

public class AsteroidDetailsActivity extends AppCompatActivity {

    TextView nameTV, diameterTV, infoTV;
    RecyclerView rv;
    AdapterAsteroidDetails myAdapter;
    String receivedID, receivedName, receivedDia