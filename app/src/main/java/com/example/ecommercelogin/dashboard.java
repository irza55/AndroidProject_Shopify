package com.example.ecommercelogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommercelogin.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    //GoogleSignInClient mGoogleSignInClient;
    private ActionBarDrawerToggle toggle;
    ImageView userpic;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //actionbar
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("                  Menu");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF97BF4A")));
        //navigate button work
        DrawerLayout drawer = findViewById(R.id.drawer_layoutDash);

        NavigationView navigationView = findViewById(R.id.nav_viewEDash);
        toggle = new ActionBarDrawerToggle(
                this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //navigationView.setItemIconTintList(null);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        //google retrived data==============================================================
        userpic=(ImageView)findViewById(R.id.userPic);
        username=(TextView)findViewById(R.id.userName);

        LoginActivity obj=new LoginActivity();
        username.setText(obj.personName);
        // userpic.setImageURI((obj.personPhoto));
        Picasso.get().load(obj.personPhoto).into(userpic);

    }
    //======================================================================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_cart:

                Intent intent = new Intent(dashboard.this,cart.class);
//                        Log.d("product",Product_Name_List.get(0).toString());
                // Title.setText(Product_Price_List.get(0).toString());
                startActivity(intent);
                return true;
            case R.id.action_search:


        };
        return super.onOptionsItemSelected(item);
    }
    //-----------------------------------------------------------------------
    public void Electronics(View view) {
        Intent intent = new Intent(dashboard.this,ElectronicsActivity.class);
        startActivity(intent);
    }
//----------------------------------------------------------------------------
    public void logout(View view) {

        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.clear();
        Ed.putInt("Token",2);
        Ed.commit();


        //gmailLogout
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        //removing static data
        LoginActivity obj=new LoginActivity();
        obj.personName="user";
        obj.personPhoto= Uri.parse("android.resource://com.example.ecommercelogin/drawable/user_icon");
    }

    public void gmailLogout(View view) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_icon,menu);
        return true;
    }
//----------------------------------------------------------------------
public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int id = menuItem.getItemId();

    if (id == R.id.nav_home) {
        Intent intent = new Intent(getApplicationContext(), ElectronicsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    } else if (id == R.id.MobilePhonesNavi) {
        Intent intent = new Intent(getApplicationContext(), MobilePhones.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_tools) {

    } else if (id == R.id.logoutNavigation) {
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        Ed.clear();
        Ed.putInt("Token",2);
        Ed.commit();


        //gmailLogout
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(dashboard.this, LoginActivity.class));
                        finish();
                    }
                });
        //removing static data
        LoginActivity obj=new LoginActivity();
        obj.personName="user";
        obj.personPhoto= Uri.parse("android.resource://com.example.ecommercelogin/drawable/user_icon");

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layoutDash);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}

    public void MobilePhones(View view) {
        Intent intent = new Intent(getApplicationContext(), MobilePhones.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
}
