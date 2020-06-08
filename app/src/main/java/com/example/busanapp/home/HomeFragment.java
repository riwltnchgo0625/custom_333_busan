package com.example.busanapp.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.busanapp.Common.LoginSignup.RetailerStartUpScreen;
import com.example.busanapp.HelperClasses.Home.CategoriesAdapter;
import com.example.busanapp.HelperClasses.Home.CategoriesHelperClass;
import com.example.busanapp.HelperClasses.Home.FeaturedAdapter;
import com.example.busanapp.HelperClasses.Home.FeaturedHelperClass;
import com.example.busanapp.HelperClasses.Home.MostViewedAdapter;
import com.example.busanapp.HelperClasses.Home.MostViewedHelperClass;
import com.example.busanapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    RecyclerView featuredRecycler, mostViewdRecycler, categoriesRecycler;
    private GradientDrawable gradient1,gradient2,gradient3,gradient4;
    private TextView tv_name;
    private Context mContext;

    //
    TextView t1_temp, t4_data;
    ImageView imageView;
    String my_longitude;
    String my_latitude;
    private String imgURL;


   /* private ImageAdapter adapter = new ImageAdapter();
    private RecyclerView recyclerView;
    //추가
    List<Course> first_Course;*/


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //날씨
        t1_temp = view.findViewById(R.id.weather_temp);
        t4_data = view.findViewById(R.id.weather_date);
        imageView = view.findViewById(R.id.weather_image);
        final LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
            my_longitude = Double.toString(longitude);
            my_latitude= Double.toString(latitude);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }

        find_weather();

        featuredRecycler = view.findViewById(R.id.featured_recycler);
        mostViewdRecycler = view.findViewById(R.id.Most_recycler);
        mostViewedRecycler();
        categoriesRecycler = view.findViewById(R.id.categories_recycler);
        categoriesRecycler();

        ImageView imageView = (ImageView) view.findViewById(R.id.login_image_button);
        imageView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.login_image_button){
                    Intent j = new Intent(getActivity().getApplicationContext(),RetailerStartUpScreen.class);
                    startActivity(j);
                }
            }
        });


        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.cafe1_1,"카페1","카페설명"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.cafe1_2,"카페2","카페설명"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.cafe1_3,"카페3","카페설명"));

        RecyclerView.Adapter adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        return view;
    }



    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();


        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private void find_weather() {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + my_latitude + "&lon="+ my_longitude+
                "&appid=d1ce79865abbaf66dc12b6ad6368826a&units=metric";
        Log.e("url",url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");
                    String icon = object.getString("icon");
                    Log.e("icon",icon);
                    imgURL = "http://openweathermap.org/img/w/" + icon + ".png";
                    Log.e("icon url",imgURL);

                    t1_temp.setText(temp);
                    Picasso.get().load(Uri.parse(imgURL)).into(imageView);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-dd");
                    String formatte_date = sdf.format(calendar.getTime());

                    t4_data.setText(formatte_date);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            private void into(ImageView imageView) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jor);

    }

    public void callRetailerScreens(View view){
        getActivity().startActivity(new Intent(getActivity().getApplicationContext(),RetailerStartUpScreen.class));
    }

    private void mostViewedRecycler(){ mostViewdRecycler.setHasFixedSize(true);
        mostViewdRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.cafe3_9, "dddd","ddddddddd's"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.cafe2_1, "Edenrobe","dddddddccccc"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.cafe2_2, "J.","ddddddd"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.cafe2_3, "Walmart","sdkjoqjk"));

        RecyclerView.Adapter adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewdRecycler.setAdapter(adapter);

    }

    private void categoriesRecycler(){
       //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.cafe1_3,"a", "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.cafe1_8,"b", "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.cafe1_5,"c", "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.cafe1_4,"d", "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.cafe1_9,"e", "Education"));


        categoriesRecycler.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}

