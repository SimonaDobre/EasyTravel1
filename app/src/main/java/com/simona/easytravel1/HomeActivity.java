package com.simona.easytravel1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements EditMarkTravelInterface {

    FloatingActionButton floatingActionButton;
    RecyclerView rv;
    AdapterTravel mAdapter;

    private TravelViewModel mTravelViewModel;

    public static final int REZULTAT_ADAUGARE = 100;
    public static final int REZULTAT_EDITARE = 200;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        addNewTravel();
        swipeToDelete();

    }

    private void addNewTravel() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
                startActivityForResult(intent, REZULTAT_ADAUGARE);
            }
        });
    }

    private void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Travel travelToBeDeleted = mAdapter.getTravelObjectAtIndex(viewHolder.getAdapterPosition());
                mTravelViewModel.deleteViewModel(travelToBeDeleted);
                Snackbar.make(rv, "am sters ", Snackbar.LENGTH_LONG)
                        .setAction("UNDO ?", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mTravelViewModel.insertViewModel(travelToBeDeleted);
                            }
                        }).show();
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public void editTravel(Travel tr) {
        Intent i = new Intent(HomeActivity.this, AddEditActivity.class);
        i.putExtra(AddEditActivity.ID_CODE_FOR_EDIT, tr.getId());
        i.putExtra(AddEditActivity.NAME_ADD_EDIT, tr.getTravelName());
        i.putExtra(AddEditActivity.DESTINATION_ADD_EDIT, tr.getDestination());
        i.putExtra(AddEditActivity.TYPE_ADD_EDIT, tr.getTypeOfTravel());
        i.putExtra(AddEditActivity.START_DATE_ADD_EDIT, tr.getStartDate());
        i.putExtra(AddEditActivity.END_DATE_ADD_EDIT, tr.getEndDate());
        i.putExtra(AddEditActivity.PRICE_ADD_EDIT, tr.getPrice());
        i.putExtra(AddEditActivity.RATING_ADD_EDIT, tr.getRating());
        i.putExtra(AddEditActivity.URI_PICTURE_ADD_EDIT, tr.getUriPicture());
        i.putExtra(AddEditActivity.IS_FAVORITE_ADD_EDIT, tr.isFavorite());
        Toast.makeText(HomeActivity.this, tr.getTravelName() + " trimis la EDITARE ", Toast.LENGTH_SHORT).show();
        startActivityForResult(i, REZULTAT_EDITARE);
    }

    @Override
    public void displayTravel(Travel tr) {
        Intent i = new Intent(HomeActivity.this, DetailsTravelActivity.class);
        i.putExtra(AddEditActivity.NAME_ADD_EDIT, tr.getTravelName());
        i.putExtra(AddEditActivity.DESTINATION_ADD_EDIT, tr.getDestination());
        i.putExtra(AddEditActivity.START_DATE_ADD_EDIT, tr.getStartDate());
        i.putExtra(AddEditActivity.END_DATE_ADD_EDIT, tr.getEndDate());
        i.putExtra(AddEditActivity.PRICE_ADD_EDIT, tr.getPrice());
        i.putExtra(AddEditActivity.RATING_ADD_EDIT, tr.getRating());
        i.putExtra(AddEditActivity.URI_PICTURE_ADD_EDIT, tr.getUriPicture());
        i.putExtra(AddEditActivity.ID_CODE_FOR_EDIT, tr.getId());
        startActivity(i);
//        Toast.makeText(HomeActivity.this, "AFISARE detalii pt " + tr.getTravelName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void markAsFavorite(Travel tr) {
        tr.changeFavorit();
        mTravelViewModel.updateViewModel(tr);
    }

    @Override
    public void shareThisTravel(Travel tr) {
//        Toast.makeText(HomeActivity.this, "Share travel " + tr.getDestination(), Toast.LENGTH_SHORT).show();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, tr.getDestination());
        startActivity(Intent.createChooser(shareIntent,
                "Share information for this travel: \n\n"
                        + "Destination: " + tr.getDestination() + "\n"
                        + "Start date: " + tr.getStartDate() + "\n"
                        + "End date: " + tr.getEndDate() + "\n"
                        + "Price: $" + tr.getPrice() + "\n"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditActivity.NAME_ADD_EDIT);
            String destination = data.getStringExtra(AddEditActivity.DESTINATION_ADD_EDIT);
            String type = data.getStringExtra(AddEditActivity.TYPE_ADD_EDIT);
            int price = data.getIntExtra(AddEditActivity.PRICE_ADD_EDIT, 10);
            String startDate = data.getStringExtra(AddEditActivity.START_DATE_ADD_EDIT);
            String endDate = data.getStringExtra(AddEditActivity.END_DATE_ADD_EDIT);
            int rating = data.getIntExtra(AddEditActivity.RATING_ADD_EDIT, 1);
            String uriPicture = data.getStringExtra(AddEditActivity.URI_PICTURE_ADD_EDIT);

            if (requestCode == REZULTAT_ADAUGARE) {
                if (uriPicture != null) {
                    Travel travelNou = new Travel(name, destination, type, price, startDate, endDate, rating, uriPicture, false);
                    mTravelViewModel.insertViewModel(travelNou);
                    Toast.makeText(HomeActivity.this, "SALVAT", Toast.LENGTH_SHORT).show();
                } else {
                    Travel travelNou = new Travel(name, destination, type, price, startDate, endDate, rating, null, false);
                    mTravelViewModel.insertViewModel(travelNou);
                    Toast.makeText(HomeActivity.this, "SALVAT cu poza DEFAULT", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REZULTAT_EDITARE) {
                int idEditedTravel = data.getIntExtra(AddEditActivity.ID_CODE_FOR_EDIT, -1);
                boolean isFav = data.getBooleanExtra(AddEditActivity.IS_FAVORITE_ADD_EDIT, false);
                Travel editedTravel = new Travel(name, destination, type, price,
                        startDate, endDate, rating,
                        uriPicture, isFav);
                editedTravel.setId(idEditedTravel);
                mTravelViewModel.updateViewModel(editedTravel);
            } else {
                Toast.makeText(HomeActivity.this, "Nu s-a salvat", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void menuMore(View v) {
        Toast.makeText(this, "MENU 3 PUNCTE", Toast.LENGTH_SHORT).show();
    }

    public void generalMenu(View v) {
        openGeneralMenu(drawerLayout);
    }

    public static void openGeneralMenu(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void toAboutUs(View v) {
        redirectToNewActivity(this, AboutUsActivity.class);
    }

    public void toContactUs(View v) {
        redirectToNewActivity(this, ContactUsActivity.class);
    }

    public void toHomeActivity(View v) {
        recreate();
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectToNewActivity(Activity activity, Class clasa) {
        Intent intent = new Intent(activity, clasa);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void initViews() {
        floatingActionButton = findViewById(R.id.addFloatingActionBtn);
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_circle));

        rv = findViewById(R.id.travelListRV);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.addItemDecoration(new DividerItemDecoration(this, llm.getOrientation()));
        mAdapter = new AdapterTravel(this, this);
        rv.setAdapter(mAdapter);

        drawerLayout = findViewById(R.id.dl_home);

        mTravelViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(TravelViewModel.class);
        mTravelViewModel.getAllRegisteredTravelsViewModel().observe(this, new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                mAdapter.setTravels(travels);
            }
        });

    }


}