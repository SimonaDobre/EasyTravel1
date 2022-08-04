package com.simona.easytravel1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


public class ContactUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView phoneTV, emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initViews();

    }

    private void initViews() {
        drawerLayout = findViewById(R.id.dl_contactus);
        phoneTV = findViewById(R.id.phoneTV);
        emailTV = findViewById(R.id.emailTV);
    }

    public void generalMenu(View v) {
        HomeActivity.openGeneralMenu(drawerLayout);
    }


    public void toAboutUs(View v) {
        HomeActivity.redirectToNewActivity(this, AboutUsActivity.class);

    }

    public void toContactUs(View v) {
        recreate();
    }

    public void toHomeActivity(View v) {
        HomeActivity.redirectToNewActivity(this, HomeActivity.class);
    }


    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }


}

