package com.simona.easytravel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView aboutUsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initViews();

    }

    private void initViews() {
        drawerLayout = findViewById(R.id.aboutUsDrawerLayout);
        aboutUsTV = findViewById(R.id.aboutUsTV);
    }

    public void generalMenu(View v){
        HomeActivity.openGeneralMenu(drawerLayout);
    }


    public void toAboutUs(View v){
        recreate();
    }

    public void toContactUs(View v){
        HomeActivity.redirectToNewActivity(this, ContactUsActivity.class);
    }

    public void toHomeActivity(View v){
        HomeActivity.redirectToNewActivity(this, HomeActivity.class);
    }



    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }
}