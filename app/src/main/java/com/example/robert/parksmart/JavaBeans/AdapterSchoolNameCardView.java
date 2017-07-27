package com.example.robert.parksmart.JavaBeans;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.robert.parksmart.R;
import java.util.ArrayList;

/**
 * Created by Roberto on 7/27/2017.
 */



public class AdapterSchoolNameCardView extends RecyclerView.Adapter<AdapterSchoolNameCardView.MyViewHolderSchooName> {

     ArrayList<SchoolName> schoolNamesList = new ArrayList<>();

   public AdapterSchoolNameCardView(ArrayList<SchoolName> schoolNamesList){
       Log.d("vh","constructor");

       this.schoolNamesList = schoolNamesList; //Connect our array items with the view
    }
    @Override
    public MyViewHolderSchooName onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schools_cardview,parent,false);
        Log.d("vh","oncreatevh");

        return new MyViewHolderSchooName(view); // return our cardview
    }

    @Override
    public void onBindViewHolder(MyViewHolderSchooName holder, int position) {
        //pass in our list item to our view
        Log.d("vh","onBind called");
        Log.d("vh","text: " + schoolNamesList.get(position).getSchool());

        holder.tvSchoolName.setText(schoolNamesList.get(position).getSchool());
    }

    @Override
    public int getItemCount() {
        Log.d("vh","getItemCount called");
        Log.d("vh","size: " + schoolNamesList.size());

        return schoolNamesList.size(); // set the size of the list
    }

    public static class MyViewHolderSchooName extends RecyclerView.ViewHolder{

        TextView tvSchoolName; // set our variable for the CardView

        public MyViewHolderSchooName(View itemView) {
            super(itemView);
            Log.d("vh","myvhschoolnamecalled");

            tvSchoolName = (TextView) itemView.findViewById(R.id.tvCardViewSchoolName); //inflate our TextView

        }


    }

    public void setFilter(ArrayList<SchoolName> newList){

        schoolNamesList = new ArrayList<>();
        schoolNamesList.addAll(newList);
        notifyDataSetChanged();

    }

}
