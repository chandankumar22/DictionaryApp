package com.example.chandan.dictionary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandan on 21-07-2017.
 */

public class SentencesAdapter extends BaseExpandableListAdapter {

    ArrayList get_data;
    HashMap<String,ArrayList<String>> sub_def;
    ArrayList<String> def;
    Context context;

    public SentencesAdapter(ArrayList get_data, Context context) {
        this.get_data = get_data;
        this.context = context;
        def = (ArrayList<String>) get_data.get(0);
        sub_def = (HashMap<String, ArrayList<String>>) get_data.get(1);
       // Log.d("sizeofgroup",def.size()+"");
        //Log.d("sizeofhashmap",sub_def.size()+"");
    }


    @Override
    public int getGroupCount() {
       // Log.d("no.ofgroups",def.size()+"");
        return def.size();
    }

    @Override
    public int getChildrenCount(int i) {
      //  Log.d("no.ofchildrenat"+i,sub_def.get(def.get(i)).size()+"");
        return sub_def.get(def.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        //Log.d("returnedgroupat"+i,def.get(i)+"");
        return def.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        //Log.d("returnedchildat"+i,sub_def.get(def.get(i)).get(i1)+"");
        return sub_def.get(def.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1   ;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String definition = (String) this.getGroup(i);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout
                    .definition_layout,null);
        }
        TextView t = view.findViewById(R.id.defid);
        t.setText(definition);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String definition = (String) this.getChild(i,i1);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout
                    .sub_def_layout,null);
        }
        TextView t = view.findViewById(R.id.subdef);
        t.setText(definition);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        String child = (String) getChild(i,i1);
        Log.d("childis",child);
       return true;
    }
}
