package com.example.busanapp.home;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busanapp.Common.LoginSignup.RetailerStartUpScreen;
import com.example.busanapp.HelperClasses.Home.CategoriesAdapter;
import com.example.busanapp.HelperClasses.Home.CategoriesHelperClass;
import com.example.busanapp.HelperClasses.Home.FeaturedAdapter;
import com.example.busanapp.HelperClasses.Home.FeaturedHelperClass;
import com.example.busanapp.HelperClasses.Home.MostViewedAdapter;
import com.example.busanapp.HelperClasses.Home.MostViewedHelperClass;
import com.example.busanapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    RecyclerView featuredRecycler, mostViewdRecycler, categoriesRecycler;
    private GradientDrawable gradient1,gradient2,gradient3,gradient4;


   /* private ImageAdapter adapter = new ImageAdapter();
    private RecyclerView recyclerView;
    //추가
    List<Course> first_Course;*/


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


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


        /*LinearLayoutManager horizonalLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(horizonalLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItems(new ImageData().getItems());*/


        //course
/*
        first_Course = new ArrayList<>();
        first_Course.add(new Course("부산 2박 3일 여행코스", "여행코스", "깔깔", R.drawable.first));
        first_Course.add(new Course("부산 여행 코스 추천 6곳", "여행코스", "즐거워", R.drawable.second));
        first_Course.add(new Course("부산 1박 2일 밤도깨비 여행 코스", "여행코스", "조사", R.drawable.third));
        first_Course.add(new Course("부산 자유여행 1박 2일 여행코스", "여행코스", "해서 넣을 부분", R.drawable.fourth)); //오류
        first_Course.add(new Course("부산 겨울 당일치기 코스", "여행코스", "^^", R.drawable.fifth));
        first_Course.add(new Course("부산 핫스팟 여행 코스", "여행코스", "사진 출처 구글", R.drawable.sixth));

        RecyclerView myrv = view.findViewById(R.id.recyclerView);

        RecyclerView.Adapter myAdapter = new RecyclerViewAdapter(getActivity(), first_Course);

        //myrv.setLayoutManager(new GridLayoutManager(this, 3));

        // 가로 레이아웃
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        // 레이아웃 매니저 연결
        myrv.setLayoutManager(horizontalLayoutManager);

        myrv.setAdapter(myAdapter);*/

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

