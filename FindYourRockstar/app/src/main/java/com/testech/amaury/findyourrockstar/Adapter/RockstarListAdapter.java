package com.testech.amaury.findyourrockstar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.testech.amaury.findyourrockstar.DataModels.Rockstar;
import com.testech.amaury.findyourrockstar.R;

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
            TextView name = (TextView) convertView.findViewById(R.id.textView1);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
            name.setText(modelItems.get(position).getFirstName());
            if(modelItems.get(position).getValue() == 1)
            cb.setChecked(true);
            else
            cb.setChecked(false);
            return convertView;
    }

    public void AddRockstar(Rockstar elmt){
        modelItems.add(elmt);
    }

    public void ClearRockstars()    {
        modelItems.clear();
    }

}