package com.simona.easytravel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.simona.easytravel1.weather.Example;
import com.simona.easytravel1.weather.HelperShowWeather;
import com.simona.easytravel1.weather.WeatherInterface;
import com.simona.easytravel1.weather.Main;
//import com.simona.easytravel1.VremeaPackage.Wind;
//import com.simona.easytravel1.AfisareLocatii.Prediction;

import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsTravelActivity extends AppCompatActivity {

    ImageView pictureIV, weatherIV, shareIV;
    TextView nameTV, destTV, priceTV, startDateTV, endDateTV, tempTV, feelsLikeTV, windTV;
    RatingBar rb;
    String name;
    String destination;
    int price;
    String startDate, endDate;
    int stars;
    String uriStringPicture;
    int receivedTravelID;

    private WeatherInterface mWeatherInterface;
    public static final String API_KEY = BuildConfig.W_API_KEY;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_travel);

        initViews();
        getLocalWeather();

    }


    private void getLocalWeather() {
// https://openweathermap.org/current
        Call<Example> callEx = mWeatherInterface.getWeatherForSelectedCity(destination, API_KEY);
        callEx.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example ex = response.body();
                Main mm = ex.getMain();
//                Wind ww = ex.getWind();
                int tempe = (int) (mm.getTempMin() - 273.15);
                int tFeelsLike = (int) (mm.getTempMax() - 273.15);

                tempTV.setText("temp: " + tempe + " C");
                feelsLikeTV.setText("feels like: " + tFeelsLike + " C");

                if (tempe < -10) {
                    weatherIV.setImageResource(R.drawable.freezing);
                } else if (tempe < -2) {
                    weatherIV.setImageResource(R.drawable.cold);
                } else if (tempe < 10) {
                    weatherIV.setImageResource(R.drawable.coldd);
                } else if (tempe < 18) {
                    weatherIV.setImageResource(R.drawable.warm);
                } else if (tempe < 23) {
                    weatherIV.setImageResource(R.drawable.symbols_weather_clear_sunny);
                } else {
                    weatherIV.setImageResource(R.drawable.sun_beach);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }

    private Bitmap conversionStringToBitmap(String uriPrimit) {
        Uri uri = null;
        InputStream inputStream = null;
        Bitmap picture;
        try {
            uri = Uri.parse(uriPrimit);
            try {
                inputStream = this.getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        picture = BitmapFactory.decodeStream(inputStream);
        return picture;
    }

    public void generalMenu(View v) {
        HomeActivity.openGeneralMenu(drawerLayout);
    }

    public void toAboutUs(View v) {
        HomeActivity.redirectToNewActivity(this, AboutUsActivity.class);
    }

    public void toContactUs(View v) {
        HomeActivity.redirectToNewActivity(this, ContactUsActivity.class);
    }

    public void toHomeActivity(View v) {
        HomeActivity.redirectToNewActivity(this, HomeActivity.class);
    }


    public void shareAtravel() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, receivedTravelID);
        shareIntent.putExtra(Intent.EXTRA_TEXT, name);
        shareIntent.putExtra(Intent.EXTRA_TEXT, destination);
        shareIntent.putExtra(Intent.EXTRA_TEXT, price);
        shareIntent.putExtra(Intent.EXTRA_TEXT, startDate);
        shareIntent.putExtra(Intent.EXTRA_TEXT, endDate);
        shareIntent.putExtra(Intent.EXTRA_TEXT, stars);
        shareIntent.putExtra(Intent.EXTRA_TEXT, uriStringPicture);
        startActivity(Intent.createChooser(shareIntent, ""));
    }


    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }


    private void initViews() {
        pictureIV = findViewById(R.id.pictureDetfailsIV);
        weatherIV = findViewById(R.id.pictureWeatherIV);
        shareIV = findViewById(R.id.shareDetailsIV);

        shareIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAtravel();
            }
        });

        nameTV = findViewById(R.id.nameDetailsTV);
        destTV = findViewById(R.id.destinationDetailsTV);
        priceTV = findViewById(R.id.priceDetailsTV);
        startDateTV = findViewById(R.id.startDateDetailsTV);
        endDateTV = findViewById(R.id.endDateDetailsTV);
        rb = findViewById(R.id.starsRatingBar);
        tempTV = findViewById(R.id.temperatureTV);
        feelsLikeTV = findViewById(R.id.feelsLikeTV);
        windTV = findViewById(R.id.windDetailsTV);

        drawerLayout = findViewById(R.id.dl_detalii);

        Intent receivedIntent = getIntent();
        name = receivedIntent.getStringExtra(AddEditActivity.NAME_ADD_EDIT);
        nameTV.setText(name);

        receivedTravelID = receivedIntent.getIntExtra(AddEditActivity.ID_CODE_FOR_EDIT, -1);

        destination = receivedIntent.getStringExtra(AddEditActivity.DESTINATION_ADD_EDIT);
        destTV.setText(destination);

        price = receivedIntent.getIntExtra(AddEditActivity.PRICE_ADD_EDIT, 1);
        priceTV.setText("$" + String.valueOf(price));

        startDate = receivedIntent.getStringExtra(AddEditActivity.START_DATE_ADD_EDIT);
        startDateTV.setText("De la: " + startDate);

        endDate = receivedIntent.getStringExtra(AddEditActivity.END_DATE_ADD_EDIT);
        endDateTV.setText("Pana la: " + endDate);

        stars = receivedIntent.getIntExtra(AddEditActivity.RATING_ADD_EDIT, 1);
        rb.setRating(stars);

        uriStringPicture = receivedIntent.getStringExtra(AddEditActivity.URI_PICTURE_ADD_EDIT);
        pictureIV.setImageBitmap(conversionStringToBitmap(uriStringPicture));

        mWeatherInterface = HelperShowWeather.generateRetrofit().create(WeatherInterface.class);
    }

}