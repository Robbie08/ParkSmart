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
import com.example.robert.parksmart.MainActivity;
import com.example.robert.parksmart.R;

import java.util.ArrayList;


/**
 * Created by Roberto on 8/3/2017.
 */


public class Fragment_SchoolsList extends Fragment implements MainActivity.SearchSchoolList{

    /*Create our list that we will pass in to the RecyclerView*/
    public String[] schoolNames = {
            "California State University, Los Angeles",
            "California State University, Long Beach", "California State University, San Marcos",
            "Grossmont Community College","San Diego State University", "San Diego Mesa College",
            "San Francisco State University", "University of California, Berkeley",
            "University of California, Los Angeles", "University of California, San Diego","University of San Diego"};




    String qText;
    RecyclerView recyclerViewSchools;
    AdapterSchoolNameCardView adapterSchoolNameCardView;
    RecyclerView.LayoutManager layoutManagerSchool;
    ArrayList<SchoolName> schoolList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;





    public static Fragment_SchoolsList newInstance(){

        Log.d("fragmentCall","Fragment is called" );
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
        createList();


        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.action_bar, menu);
//        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
//        searchView.setQueryHint("Enter School...");
//        searchView.setOnQueryTextListener(this);
//        super.onCreateOptionsMenu(menu,inflater);
////        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//    }


    private void createList(){


        Log.d("QText","Value is: "+qText);


        /*Add the school names to the Recycler View */
        for(int i = 0 ; i < schoolNames.length; i++){
            if(schoolNames[i].contains(qText)) {
                //Log.d("ListLoop", "passed!");
                schoolList.add(new SchoolName(schoolNames[i]));
            }

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



    }

    @Override
    public void searchQuery(String val) {
        Log.d("SchoolCaught", "Value: "+val);
        this.qText = val;
    }
}
