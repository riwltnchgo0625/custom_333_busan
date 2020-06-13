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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busanapp.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    RadioGroup radioGroup;
    DatePicker datePicker;
    ImageView backBtn;
    Button next, login;
    TextView titleText;
    RadioButton selectedGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);


        backBtn = findViewById(R.id.login_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);


        radioGroup = findViewById(R.id.radio_group);
        datePicker  =findViewById(R.id.age_picker);

    }

    public void callNextSignupScreen(View view) {

        if(!validateGender() | !validateAge()){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String _date = day+"/"+month+"/"+year;


        Intent intent = new Intent(getApplicationContext(), SignUp3rd.class);

        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");


            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
            startActivity(intent, options.toBundle());



    }

    private boolean validateGender(){
        if (radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "성별을 선택하세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14){
            Toast.makeText(this,"적합하지 않습니다.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;

    }
}
