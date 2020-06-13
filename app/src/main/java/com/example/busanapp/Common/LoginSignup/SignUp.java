package com.example.busanapp.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busanapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText;

    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_retailer_sign_up);

        backBtn = findViewById(R.id.login_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }

    public void callNextSignupScreen(View view) {

         if (!validateFullName() |!validateUsername() |!validateEmail() | !validatePassword()){
             return;
         }

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

       Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(findViewById(R.id.signup_back_button), "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(findViewById(R.id.signup_next_button), "transition_next_btn");
        pairs[2] = new Pair<View, String>(findViewById(R.id.signup_login_button), "transition_login_btn");
        pairs[3] = new Pair<View, String>(findViewById(R.id.signup_password), "transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }
    public void callLoginScreen(View view){

        Intent intent = new Intent(getApplicationContext(),Login.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(findViewById(R.id.longin_btn),"transition_login");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }

    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("칸을 비울 수 없습니다");
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("이름이 너무 깁니다!");
        } else if(val.length()>20){
            username.setError("");
            return false;
        }
        else if(!val.matches(checkspaces)){
            username.setError("칸을 비울 수 없습니다.");
            return false;
        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
        return false;
    }
    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("칸을 비울 수 없습니다.");
        }
        else if(!val.matches(checkEmail)){
            email.setError("정확하지 않습니다.");
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^"+
                "(?=.*[a-zA-Z])"+
                "(?=.*[@!#$%^&+=])"+
                        "(?=.\\S+$)"+
                                ".{4,}"+
                "$";

        if (val.isEmpty()) {
            password.setError("칸을 비울 수 없습니다.");
        }
        else if(!val.matches(checkPassword)){
            password.setError("정확하지 않습니다.");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
        return false;
    }
}
