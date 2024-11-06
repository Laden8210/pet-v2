package com.example.epet.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.epet.Model.Case;
import com.example.epet.Model.MissingPet;
import com.example.epet.Model.Pet;
import com.example.epet.Model.User;
import com.example.epet.api.PostTask;
import com.example.epet.calback.GetCallback;
import com.example.epet.calback.PostCallback;
import com.example.epet.fragment.SettingFragment;
import com.example.epet.fragment.CaseFragment;
import com.example.epet.fragment.HomeFragment;
import com.example.epet.fragment.PetFragment;
import com.example.epet.R;
import com.example.epet.fragment.UserInformationFragment;
import com.example.epet.util.Messenger;
import com.example.epet.util.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HeroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GetCallback {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.menu_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (R.id.menu_home == item.getItemId()){
            changeFragment(new HomeFragment());
        }else if(R.id.menu_pet == item.getItemId()){
            changeFragment(new PetFragment());

        } else if (R.id.menu_setting == item.getItemId()) {
            changeFragment(new SettingFragment());
        }else if(R.id.menu_user == item.getItemId()){
            changeFragment(new UserInformationFragment());
        }else if(R.id.user_logout == item.getItemId()){
            Messenger.showAlertDialog(this, "Logout", "Do you want to logout?", "Yes", " No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(HeroActivity.this, LoginActivity.class));
                            SessionManager.getInstance(HeroActivity.this).clearSession();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if (R.id.menu_qr == item.getItemId()) {
            startQRScanner();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Gson gson = new Gson();
                    Pet pet = gson.fromJson(result.getContents(), Pet.class);

                    try {
                        JSONObject data = new JSONObject();

                        data.put("pet_id", pet.getPetId());

                        new PostTask(this, new PostCallback() {
                            @Override
                            public void onPostSuccess(String responseData) {

                                MissingPet missingPet = gson.fromJson(responseData, MissingPet.class);
                                Intent intent = new Intent(HeroActivity.this, FoundReportActivity.class);
                                intent.putExtra("missingPet", missingPet);
                                startActivity(intent);
                            }

                            @Override
                            public void onPostError(String errorMessage) {
                                Intent intent = new Intent(HeroActivity.this, PetOwnerInformationActivity.class);
                                intent.putExtra("pet", pet);
                                startActivity(intent);
                            }
                        }, "Error", "retrieve-missing-pet-details.php").execute(data);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

    private void startQRScanner() {
        ScanOptions integrator = new ScanOptions();
        integrator.setPrompt("Scan a QR Code");
        integrator.setOrientationLocked(false);
        barcodeLauncher.launch(integrator);
    }

    @Override
    public void onGetSuccess(String responseData) {
        Log.d("data", responseData);
        Type productListType = new TypeToken<List<Pet>>() {}.getType();
        Gson gson =new Gson();

    }

    @Override
    public void onGetError(String errorMessage) {

    }


}