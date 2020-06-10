package com.example.busanapp.mytrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.busanapp.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyTripFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mytrip, container, false);

        FloatingActionButton add = v.findViewById(R.id.fab_add);
        add.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddNewTripActivity.class);
            startActivity(intent);
        });

        return v;
    }
}