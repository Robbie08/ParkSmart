package com.example.robert.parksmart.Fragments;


import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.robert.parksmart.JavaBeans.AdapterSchoolNameCardView;
import com.example.robert.parksmart.JavaBeans.SchoolName;
import com.example.robert.parksmart.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Roberto on 8/3/2017.
 */


public class Fragment_SchoolsList extends Fragment{

    /*Create our list that we will pass in to the RecyclerView*/
    public String[] schoolNames = {"University of California, San Diego","University of San Diego",
            "California State University, San Marcos","San Diego State University",
            "San Diego Mesa College", "Grossmont Community College", "University of California, Berkeley",
            "California State University, Long Beach", "University of California, Los Angeles"};

    private String qText;

    RecyclerView recyclerViewSchools;
    AdapterSchoolNameCardView adapterSchoolNameCardView;
    RecyclerView.LayoutManager layoutManagerSchool;
    ArrayList<SchoolName> schoolList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;



    public static Fragment_SchoolsList newInstance(){

        Log.d("fragmentCall","Fragment is called");
        //create new instance method
        Fragment_SchoolsList fragment_schoolList = new Fragment_SchoolsList();
        Bundle args = new Bundle();
        fragment_schoolList.setArguments(args);
        return fragment_schoolList;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_list,container,false);

        recyclerViewSchools = (RecyclerView) view.findViewById(R.id.recyclerViewSchools);
        layoutManagerSchool = new LinearLayoutManager(getContext());

        recyclerViewSchools.setLayoutManager(layoutManager);
        recyclerViewSchools.setHasFixedSize(true);


        /*Add the school names to the Recycler View */
        for(int i = 0 ; i < schoolNames.length; i++){
            //if(schoolNames[i].contains(newText))
                schoolList.add(new SchoolName(schoolNames[i]));
        }


        if(schoolList.size() == 0){
            Log.d("test","newList is 0");
        }else{
            Log.d("test","nvm");
            adapterSchoolNameCardView = new AdapterSchoolNameCardView(schoolList);
            recyclerViewSchools.setAdapter(adapterSchoolNameCardView);
            recyclerViewSchools.setLayoutManager(layoutManagerSchool);
            recyclerViewSchools.setHasFixedSize(true);
        }

        return view;
    }


}
