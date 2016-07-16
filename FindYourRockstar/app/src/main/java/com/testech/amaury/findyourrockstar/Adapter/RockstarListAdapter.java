package com.testech.amaury.findyourrockstar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testech.amaury.findyourrockstar.DataModels.Rockstar;
import com.testech.amaury.findyourrockstar.R;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Amou on 16/07/16.
 */

public class RockstarListAdapter extends ArrayAdapter {

    ArrayList<Rockstar> modelItems = null;
    Context context;

    public RockstarListAdapter(Context context, ArrayList<Rockstar> resource) {
            super(context, R.layout.rockstarrowmain,resource);

            this.context = context;
            this.modelItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.rockstarrowmain, parent, false);

            //Get elements
            TextView name = (TextView) convertView.findViewById(R.id.textViewName);
            TextView status = (TextView) convertView.findViewById(R.id.textViewStatus);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxBookmark);
            ImageView pic = (ImageView) convertView.findViewById(R.id.imageViewPicture);

            //Checkbox event
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        Log.d("TEST","Checked");

                    }
                    else
                        Log.d("TEST","Unchecked");

                }
            });

            //Set values
            //Name
            name.setText(modelItems.get(position).getFirstName() + " " + modelItems.get(position).getLastName());
            //Status
            status.setText(modelItems.get(position).getStatus());
            //Checkbox
            if(modelItems.get(position).getValue() == 1)
                cb.setChecked(true);
            else
                cb.setChecked(false);
            //Image
            Picasso.with(context).load("http://54.72.181.8/yolo/" + modelItems.get(position).getHisFace()).into(pic);


            return convertView;
    }

    public void AddRockstar(Rockstar elmt){
        modelItems.add(elmt);
    }

    public void ClearRockstars()    {
        modelItems.clear();
    }

}