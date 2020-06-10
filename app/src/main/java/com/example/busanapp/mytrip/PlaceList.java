package com.example.busanapp.mytrip;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.busanapp.R;

public class PlaceList extends AppCompatActivity implements
        View.OnClickListener{

    Spinner trip_location;
    private String mLocation;
    private ListView listview;
    private MyAdapter adapter;
    private int[] img = {R.drawable.daytrip1, R.drawable.daytrip1, R.drawable.daytrip1_3};
    private String [] Title = {"해운대 해수욕장", "관광지", "관광지"};
    private String [] Context = {"해운대 해수욕장에 대한 한줄 설명", "관광지 한 줄 설명", "관광지 한 줄 설명"};

    ArrayAdapter<CharSequence> spinnerAdapter;
    boolean mInitSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        adapter = new MyAdapter();
        listview = (ListView) findViewById(R.id.listView);
        trip_location = findViewById(R.id.spinner_location);

        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.prompt_gu, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trip_location.setAdapter(spinnerAdapter);

      /*  try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            ListPopupWindow window = (ListPopupWindow)popup.get(trip_location);
            window.setHeight(700); //pixel
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        listview.setAdapter(adapter);

        for (int i = 0 ; i < img.length ; i++){
            adapter.addItem(ContextCompat.getDrawable(this, img[i]), Title[i], Context[i]);
        }


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.spinner_location:
                trip_location.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        trip_location.setSelection(0);

                        if (!mInitSpinner) {
                            mInitSpinner = true;
                        }

                        mLocation = trip_location.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
        }
    }

}
