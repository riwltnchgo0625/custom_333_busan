package com.example.busanapp.mytrip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.busanapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.example.busanapp.mytrip.Constants.API_LINK_V2;

public class AddNewTripActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    EditText trip_name;
    TextView trip_date;
    Spinner trip_location;
    Button btn_place;
    Button save;
    LinearLayout mLinearLayout;
//  LottieAnimationView animationView;

    private String mStartdate;
    private String mTripname;
    private String mLocation;
    String mToken;

    private DatePickerDialog mDatePickerDialog;
    private MaterialDialog mDialog;
    private Handler mHandler;

    // Spinner
    ArrayAdapter<CharSequence> spinnerAdapter;
    boolean mInitSpinner;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);

        // 초기화
        trip_name = findViewById(R.id.tname);
        trip_date = findViewById(R.id.sdate);
//        trip_location = findViewById(R.id.spinner_location);
        save = findViewById(R.id.ok);
        mLinearLayout = findViewById(R.id.linear_layout);
//      animationView = findViewById(R.id.animation_view);

        //list button
        btn_place = findViewById(R.id.btn_place);
        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewTripActivity.this, PlaceList.class);
                startActivity(intent);
            }
        });

//        // spinner
//        trip_location.setPrompt("Select Item");
//
//        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.prompt_gu, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        trip_location.setAdapter(spinnerAdapter);

//      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mHandler = new Handler(Looper.getMainLooper());
//      mToken = sharedPreferences.getString(USER_TOKEN, null);

        calendar = Calendar.getInstance();

        mDatePickerDialog = new DatePickerDialog(
                AddNewTripActivity.this,
                AddNewTripActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        trip_date.setOnClickListener(this);
        save.setOnClickListener(this);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 뒤로가기 버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date currentDate = calendar.getTime();

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, dayOfMonth);

        Date selectedDate = selectedCalendar.getTime();

        if (selectedDate.compareTo(currentDate) > 0) {
            Log.d("Month", String.valueOf(month));
            mStartdate = Long.toString(selectedDate.getTime() / 1000);
            trip_date.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");

        } else if (selectedDate.compareTo(currentDate) == 0) {
            TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                    R.string.wrong_date_alert, Snackbar.LENGTH_LONG
            ).show();

        } else {
            TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                    R.string.wrong_date_alert, Snackbar.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Set Start date
            case R.id.sdate:
                mDatePickerDialog.show();
                break;

            // Add a new trip
            case R.id.ok:
                Utils.hideKeyboard(this);
                mTripname = trip_name.getText().toString();

                if (mTripname.trim().equals("")) {
                    TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                            R.string.trip_name_blank, Snackbar.LENGTH_LONG
                    ).show();

                } else if (trip_date == null || trip_date.getText().toString().equals("")) {
                    TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                            R.string.trip_date_blank, Snackbar.LENGTH_LONG
                    ).show();

                } else if (mLocation == null) {
                    TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                            R.string.trip_city_blank, Snackbar.LENGTH_LONG
                    ).show();

                } else {
                    addTrip();
                }

                break;

        }
    }

    /**
     * Calls API to add  new trip
     */
    private void addTrip() {
        // Show a mDialog box
        mDialog = new MaterialDialog.Builder(AddNewTripActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();

        String uri = API_LINK_V2 + "add-trip";

        Log.v("EXECUTING", uri);

        //Set up client
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("trip_name", mTripname)
                .addFormDataPart("location_id", mLocation)
                .addFormDataPart("start_date_tx", mStartdate)
                .build();

        //Execute request
        Request request = new Request.Builder()
                .header("Authorization", "Token " + mToken)
                .post(requestBody)
                .url(uri)
                .build();

        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mHandler.post(() -> {
                    mDialog.dismiss();
                    networkError();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
                try {
                    final String res = Objects.requireNonNull(response.body()).string();
                    final int responseCode = response.code();
                    mHandler.post(() -> {
                        if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                            TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                                    R.string.trip_added, Snackbar.LENGTH_LONG
                            ).show();
                            //Call back to MytripsFragment
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            TravelmateSnackbars.createSnackBar(findViewById(R.id.activityAddNewTrip),
                                    res, Snackbar.LENGTH_LONG
                            ).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    networkError();
                    mDialog.dismiss();
                }
                mDialog.dismiss();
            }
        });

    }

    /**
     * Plays the network lost animation in the view
     */
    private void networkError() {
        mLinearLayout.setVisibility(View.INVISIBLE);
//      animationView.setVisibility(View.VISIBLE);
//      animationView.setAnimation(R.raw.network_lost);
//      animationView.playAnimation();
    }

}