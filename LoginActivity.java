package com.example.ecommercelogin.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ecommercelogin.R;
import com.example.ecommercelogin.Signup;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.example.ecommercelogin.dashboard;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    private ProgressBar progressBar;
    private Button  btnLogin;
    private Button GoogleLogin;

   public static String personName="User";
   public static String personEmail="";
    public static String personId="";
   public static Uri personPhoto=Uri.parse("android.resource://com.example.ecommercelogin/drawable/user_icon");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        //foe google sign in
        // Configure Google Sign In
        GoogleLogin =(Button)findViewById(R.id.GoogleSignin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
//click listener
        GoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               Intent SigninIntent=mGoogleSignInClient.getSignInIntent();
               startActivityForResult(SigninIntent,102);
            }
        });
        //foe google sign in End here
        btnLogin = (Button) findViewById(R.id.login);

        ///get value for auto login
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        int Token=sp1.getInt("Token",0);
        if(Token== 1){
            Intent intent = new Intent(LoginActivity.this, dashboard.class);
            startActivity(intent);
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter Email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                   inputPassword.setError("Enter Password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //for saving email and pass to not login each time or we can do this by saving bool value
                                    SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putInt("Token",1);

                                    Ed.commit();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                     personEmail = user.getEmail();
                                    personId=user.getUid();

                                     Toast.makeText(getApplicationContext(), personEmail, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });



    }
    //SIGNUP=======================================================================================================================
    public void signup (View v){
        Intent intent = new Intent(LoginActivity.this, Signup.class);
        startActivity(intent);
        //finish();
    }
    //google sign in===============================================================================================================
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 102);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 102) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (acct != null) {
                            personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                            personEmail = acct.getEmail();
                            personId = acct.getId();
                            personPhoto = acct.getPhotoUrl();



                    Toast.makeText(LoginActivity.this, "Welcome "+personName, Toast.LENGTH_LONG).show();}
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
              
                // ...
            }
        }
    }
//google sign in check==============================================================================================
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//for saving email and pass to not login each time or we can do this by saving bool value
                            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed=sp.edit();
                            Ed.putInt("Token",1);

                            Ed.commit();
                            // Sign in success, update UI with the signed-in user's information

                           // Toast.makeText(getApplicationContext(), "Welcome "+personName, Toast.LENGTH_SHORT).show();
//

                            Intent intent = new Intent(LoginActivity.this, dashboard.class);
                            startActivity(intent);
                            finish();
                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }

                        //------------------------------------------------
                    }
                });
    }

}
