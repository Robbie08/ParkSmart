package com.example.robert.parksmart.listServices;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robert.parksmart.Fragments.Fragment_Map;
import com.example.robert.parksmart.enteties.SchoolName;
import com.example.robert.parksmart.R;
import java.util.ArrayList;

/**
 * Created by Roberto on 7/27/2017.
 */



public class AdapterSchoolNameCardView extends RecyclerView.Adapter<AdapterSchoolNameCardView.MyViewHolderSchooName> {

     ArrayList<SchoolName> schoolNamesList = new ArrayList<>();
    Context ctx;

   public AdapterSchoolNameCardView(ArrayList<SchoolName> schoolNamesList, Context ctx){
       Log.d("vh","constructor");


       this.schoolNamesList = schoolNamesList; //Connect our array items with the view
       this.ctx = ctx;
   }
    @Override
    public MyViewHolderSchooName onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schools_cardview,parent,false);
        Log.d("vh","oncreatevh");

        return new MyViewHolderSchooName(view,ctx,schoolNamesList); // return our cardview
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

    public static class MyViewHolderSchooName extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvSchoolName; // set our variable for the CardView
        ArrayList<SchoolName> schoolName = new ArrayList<>();
        Context ctx;
        String schoolValue;
        OnLocationSelected onLocationSelected;



        public MyViewHolderSchooName(View itemView, Context ctx, ArrayList<SchoolName> schoolName) {
            super(itemView);
            Log.d("vh","myvhschoolnamecalled");
            this.schoolName = schoolName; // set our arrayList
            this.ctx = ctx; // set our context
            itemView.setOnClickListener(this);
            tvSchoolName = (TextView) itemView.findViewById(R.id.tvCardViewSchoolName); //inflate our TextView

        }


        @Override
        public void onClick(View view) {
            /*when the item in the recycler is clicked*/

            //get the position of the list, get particular object from arrayList
            int position = getAdapterPosition();
            final SchoolName schoolName = this.schoolName.get(position);
            final Fragment_Map fMap = Fragment_Map.newInstance();

            Log.d("AdapterSchoolName","Name: " +schoolName.getSchool());
            schoolValue = schoolName.getSchool();

            if(schoolValue.length() == 0 || TextUtils.isEmpty(schoolValue)){
                Log.d("The SchoolValue is ", ": "+schoolValue);
                schoolValue = "UCSD";
            }else{

                Log.d("DebugTest","Passed");

                    onLocationSelected.sendData(schoolValue); // send value to FragmentMap

                ((FragmentActivity)ctx).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fMap).commit();
            }



            /*START, PACKAGE AND BUNDLE TO MAIN ACTIVITY*/
//            //Start the map fragment
//            Intent intent = new Intent(this.ctx,MainActivity.class);
//            //pass the location of the list through the intent
//            intent.putExtra("schoolName",schoolName.getSchool());
//            this.ctx.startActivity(intent); //via intent start our activity
//            ((Activity)ctx).finish();

        }

    }

    /*FIX IT*/
    public interface OnLocationSelected {;
        void sendData(String location);
    }



    public void setFilter(ArrayList<SchoolName> newList){

        schoolNamesList = new ArrayList<>();
        schoolNamesList.addAll(newList);
        notifyDataSetChanged();

    }

}
