package com.pennapps.arpitsabherwal.journal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by arpitsabherwal on 24/01/16.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private static final int TYPE_ITEM_TEXT = 1;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM_IMAGE = 2;

    private String mNavTitles[];
    private int mType[];// String Array to store the passed titles Value from MainActivity.java
    private Context mContext;
           //String Resource for header view email

    StoryAdapter(String Titles[],Context context,int type[]) { // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        mNavTitles = Titles;
        mContext=context;
        mType=type;//have seen earlier
        Log.d("Story adapter","constructor called");

    }


        @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("Story adapter","Create called");

            if (viewType == TYPE_ITEM_IMAGE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_image,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object


        } else if (viewType == TYPE_ITEM_TEXT) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_text,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Story adapter","Bind called");

        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.tvPara.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
        }
        else{
            Picasso.with(mContext).load(mNavTitles[position - 1]).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView tvPara;
        ImageView ivImage;



        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == TYPE_ITEM_TEXT) {
                tvPara = (TextView) itemView.findViewById(R.id.tvPara); // Creating TextView object with the id of textView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{
                tvPara = (TextView) itemView.findViewById(R.id.tvPara);         // Creating Text View object from header.xml for name
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);// Creating Image view object from header.xml for profile pic
                Holderid = 2;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }

    }

}
