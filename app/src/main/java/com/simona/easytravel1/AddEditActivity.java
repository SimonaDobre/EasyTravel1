package com.simona.easytravel1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.simona.easytravel1.showPossibleLocations.AdapterShowLocations;
import com.simona.easytravel1.showPossibleLocations.Example;
import com.simona.easytravel1.showPossibleLocations.HelperAutocompleteLocations;
import com.simona.easytravel1.showPossibleLocations.ShowAllPossibleLocationsInterface;
import com.simona.easytravel1.showPossibleLocations.SelectLocationInterface;
import com.simona.easytravel1.showPossibleLocations.Prediction;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Images.Media.*;

public class AddEditActivity extends AppCompatActivity implements SelectLocationInterface {

    TextInputLayout nameTIL, destinationTIL;
    TextInputEditText nameInputED, destinationInputED;

    RadioGroup radioGroup;
    RadioButton radioButton;
    int idSelected;
    String typeTravel;

    SeekBar seekBarPrice;
    int price;
    TextView priceTV;

    RatingBar ratingBar;
    int ratingStars;

    String startDate, endDate, existingStartDate;
    EditText ed_dataStart, ed_dataEnd;

    ImageView pictureForDestination;

    Uri uriChosenPicture;
    String stringUriPicture;

    Button saveBtn;

    int yearMin = 2022;
    int monthMin;
    int dayMin;

    private static final int PERMIT_ACCES_CAMERA = 5;
    private static final int PERMIT_ACCES_ARCHIVE = 6;
    public static final int SELECT_PICTURE_FROM_ARHIVE = 15;
    public static final int GET_NEW_PICTURE = 20;

    public static final String ID_CODE_FOR_EDIT = "ci";
    public static final String NAME_ADD_EDIT = "nc";
    public static final String DESTINATION_ADD_EDIT = "dc";
    public static final String TYPE_ADD_EDIT = "tc";
    public static final String START_DATE_ADD_EDIT = "di";
    public static final String END_DATE_ADD_EDIT = "ds";
    public static final String PRICE_ADD_EDIT = "pc";
    public static final String RATING_ADD_EDIT = "rc";
    public static final String URI_PICTURE_ADD_EDIT = "up";
    public static final String IS_FAVORITE_ADD_EDIT = "ef";

    int idReceivedForEditing;
    boolean isFav;

    RecyclerView rvPossibleDestinations;
    ArrayList<String> arrayDestinations;
    AdapterShowLocations mAdapterDestinations;
    private ShowAllPossibleLocationsInterface mInterfaceShowLocations;
    String API_KEY_AUTOCOMPLETE = BuildConfig.G_API_KEY;
    String choseDestination;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        initViews();
        setSaveBtn();
        choseStartDate();
        choseEndDate();
        pickPictureFromArchive();
        getNewPicture();
        selectPrice();
        selectRatingStars();
        showListOfPossibleDestinations();

    }



    // the Google API displays any existing city in the world, but
    // the weather API only recognise the most important ones,
    // so, if a small city will be picked from the Google available list
    // there will be an NullPointerException thrown by DetailsTravelActivity.
    // only chose big known cities, as destination!
    private void showListOfPossibleDestinations() {
        destinationInputED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fillRecyclerViewWithPossibleDestinations();
            }
        });
    }

    private void fillRecyclerViewWithPossibleDestinations() {
        rvPossibleDestinations.setVisibility(View.VISIBLE);
        Call<Example> getLocationsFromAPI = mInterfaceShowLocations
                .showAllPossibleLocations(destinationInputED.getText().toString().trim(),
                        API_KEY_AUTOCOMPLETE);
        getLocationsFromAPI.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                arrayDestinations.clear();
                Example eee = response.body();
                List<Prediction> ppp = eee.getPredictions();
                for (Prediction p : ppp) {
                    arrayDestinations.add(p.getDescription());
                }
                mAdapterDestinations.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }

    @Override
    public void selectDestinationCity(int pozi) {
        choseDestination = arrayDestinations.get(pozi);
        destinationInputED.setText(choseDestination);
        rvPossibleDestinations.setVisibility(View.GONE);
        //  Toast.makeText(AddEditActivity.this, "destinatia selectata = " + arrayDestinations.get(pozi), Toast.LENGTH_SHORT).show();
    }

    private void selectRatingStars() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingStars = (int) ratingBar.getRating();
//                Log.i("stele=", ratingStars + "");
                //  Toast.makeText(AddEditActivity.this, ratingStars + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectPrice() {
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                price = i;
                priceTV.setText(i + "$");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void selectTravelTypeRadioBtn(View v) {
        idSelected = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(idSelected);
        typeTravel = String.valueOf(radioButton.getText());
//        Toast.makeText(AddEditActivity.this, typeTravel, Toast.LENGTH_SHORT).show();
    }

    private void setSaveBtn() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent();
                ii.putExtra(NAME_ADD_EDIT, nameInputED.getText().toString());
                ii.putExtra(DESTINATION_ADD_EDIT, destinationInputED.getText().toString());
                ii.putExtra(TYPE_ADD_EDIT, typeTravel);
                ii.putExtra(START_DATE_ADD_EDIT, startDate);
                ii.putExtra(END_DATE_ADD_EDIT, endDate);
                ii.putExtra(PRICE_ADD_EDIT, price);
                ii.putExtra(RATING_ADD_EDIT, ratingStars);
                try {
                    stringUriPicture = uriChosenPicture.toString();
                    if (stringUriPicture == null) {
//                        Toast.makeText(AddEditActivity.this, "Aleg poza DEFAULT", Toast.LENGTH_SHORT).show();
                        stringUriPicture = "pozaDef";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ii.putExtra(URI_PICTURE_ADD_EDIT, stringUriPicture);

                if (idReceivedForEditing != -1) {
                    ii.putExtra(ID_CODE_FOR_EDIT, idReceivedForEditing);
                }

                ii.putExtra(IS_FAVORITE_ADD_EDIT, isFav);

                setResult(RESULT_OK, ii);
                finish();
            }
        });
    }

    private void pickPictureFromArchive() {
        (findViewById(R.id.iv_pickPhoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(AddEditActivity.this, "Aleg poza din ARHIVA", Toast.LENGTH_SHORT).show();
                requestAccessToArchive();
            }
        });
    }

    void requestAccessToArchive() {
        if (ContextCompat.checkSelfPermission(AddEditActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddEditActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMIT_ACCES_ARCHIVE);
        } else {
            accessGrantedToArchive();
        }
    }

    void accessGrantedToArchive() {
        Intent ii = new Intent(Intent.ACTION_OPEN_DOCUMENT, EXTERNAL_CONTENT_URI);
        startActivityForResult(ii, SELECT_PICTURE_FROM_ARHIVE);
    }

    private void getNewPicture() {
        (findViewById(R.id.iv_takePhoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddEditActivity.this, "Poza noua cu camera", Toast.LENGTH_SHORT).show();
                requestAccessToCamera();
            }
        });
    }

    void requestAccessToCamera() {
        if (ContextCompat.checkSelfPermission(AddEditActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(AddEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {

            String[] permisions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(AddEditActivity.this, permisions, PERMIT_ACCES_CAMERA);
        } else {
            accessGrantedToCamera();
        }
    }

    void accessGrantedToCamera() {
        ContentValues cv = new ContentValues();
        cv.put(Media.TITLE, "newPicturebbb");
        cv.put(Media.DESCRIPTION, "fromCamerammm");
        uriChosenPicture = getContentResolver().insert(EXTERNAL_CONTENT_URI, cv);

        Intent ii = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ii.putExtra(MediaStore.EXTRA_OUTPUT, uriChosenPicture);
        startActivityForResult(ii, GET_NEW_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMIT_ACCES_CAMERA || requestCode == PERMIT_ACCES_ARCHIVE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessGrantedToCamera();
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessGrantedToArchive();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE_FROM_ARHIVE) {
                uriChosenPicture = data.getData();
                if (uriChosenPicture != null) {
                    try {
                        InputStream imageStr = getContentResolver().openInputStream(uriChosenPicture);
                        Bitmap selectedPicture = BitmapFactory.decodeStream(imageStr);
                        pictureForDestination.setImageBitmap(selectedPicture);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == GET_NEW_PICTURE) {
                pictureForDestination.setImageURI(uriChosenPicture);
            }
        }
    }

    private void choseStartDate() {
        ed_dataStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearMin = i;
                        monthMin = i1;
                        dayMin = i2;
                        startDate = i + "-" + (i1 + 1) + "-" + i2;
                        ed_dataStart.setText(startDate);
                        if (!startDate.equals(existingStartDate)){
                            endDate = null;
                            ed_dataEnd.setText(endDate);
                        }
                    }
                }, startYear, startMonth, startDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    private void choseEndDate() {
        ed_dataEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yearMin == 2021) {
                    Toast.makeText(AddEditActivity.this, "Alege intai data de inceput!", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearMin = i;
                        monthMin = i1;
                        dayMin = i2;
                        endDate = i + "-" + (i1 + 1) + "-" + i2;
                        ed_dataEnd.setText(endDate);
                    }
                }, yearMin, monthMin, dayMin + 2);
                Calendar c2 = new GregorianCalendar(yearMin, monthMin, dayMin);
                c2.add(c2.DATE, 2);
                datePickerDialog.getDatePicker().setMinDate(c2.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    private Bitmap conversionStringToBitmap(String uriPrimit) {
        Uri uri = Uri.parse(uriPrimit);
        InputStream inputStream = null;
        try {
            inputStream = this.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap picBitmap = BitmapFactory.decodeStream(inputStream);
        return picBitmap;
    }

    private void initViews() {
        nameTIL = findViewById(R.id.travelNameTIL);
        nameInputED = findViewById(R.id.travelNameED);
        destinationTIL = findViewById(R.id.destinationTIL);
        destinationInputED = findViewById(R.id.destinationED);
        seekBarPrice = findViewById(R.id.sb_price);
        seekBarPrice.setMax(1000);
        ratingBar = findViewById(R.id.ratingBar2);

        pictureForDestination = findViewById(R.id.choseImageIV);
        ed_dataStart = findViewById(R.id.ed_dataStart);
        ed_dataEnd = findViewById(R.id.ed_dataEnd);
        saveBtn = findViewById(R.id.saveBtn);
        radioGroup = findViewById(R.id.radioGrup);
        priceTV = findViewById(R.id.priceAddEditTV);

        rvPossibleDestinations = findViewById(R.id.listaPosibleDestinationsRV);
        arrayDestinations = new ArrayList<>();
        rvPossibleDestinations.setLayoutManager(new LinearLayoutManager(this));
        mAdapterDestinations = new AdapterShowLocations(this, arrayDestinations, this);
        rvPossibleDestinations.setAdapter(mAdapterDestinations);

        mInterfaceShowLocations = HelperAutocompleteLocations.generateRetrofitt().create(ShowAllPossibleLocationsInterface.class);

        Intent intentPrimit = getIntent();
        if (intentPrimit.hasExtra(ID_CODE_FOR_EDIT)) {
            idReceivedForEditing = intentPrimit.getIntExtra(ID_CODE_FOR_EDIT, -1);
            nameInputED.setText(intentPrimit.getStringExtra(NAME_ADD_EDIT));
            destinationInputED.setText(intentPrimit.getStringExtra(DESTINATION_ADD_EDIT));
            price = intentPrimit.getIntExtra(AddEditActivity.PRICE_ADD_EDIT, 1);

            seekBarPrice.setProgress(price);
            priceTV.setText(String.valueOf(intentPrimit.getIntExtra(PRICE_ADD_EDIT, 1)));
            ed_dataStart.setText(intentPrimit.getStringExtra(START_DATE_ADD_EDIT));

            startDate = intentPrimit.getStringExtra(AddEditActivity.START_DATE_ADD_EDIT);
            existingStartDate = intentPrimit.getStringExtra(AddEditActivity.START_DATE_ADD_EDIT);
            ed_dataStart.setText(intentPrimit.getStringExtra(START_DATE_ADD_EDIT));

            endDate = intentPrimit.getStringExtra(AddEditActivity.END_DATE_ADD_EDIT);
            ed_dataEnd.setText(intentPrimit.getStringExtra(END_DATE_ADD_EDIT));

            stringUriPicture = intentPrimit.getStringExtra(URI_PICTURE_ADD_EDIT);

            try {
                uriChosenPicture = Uri.parse(stringUriPicture);
                if (stringUriPicture != null) {
                    pictureForDestination.setImageBitmap(conversionStringToBitmap(stringUriPicture));
                } else {
                    pictureForDestination.setImageResource(R.drawable.this_will_be_uptated);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            ratingStars = intentPrimit.getIntExtra(RATING_ADD_EDIT, 1);
            ratingBar.setRating(ratingStars);
            typeTravel = intentPrimit.getStringExtra(TYPE_ADD_EDIT);
            isFav = intentPrimit.getBooleanExtra(IS_FAVORITE_ADD_EDIT, false);
        }
    }


}