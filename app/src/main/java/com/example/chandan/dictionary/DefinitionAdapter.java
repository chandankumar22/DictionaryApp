package com.example.chandan.dictionary;

import android.content.Context;
import android.text.TextUtils;
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

public class DefinitionAdapter extends BaseExpandableListAdapter {

    ArrayList get_data;
    HashMap<String,ArrayList<String>> sub_def;
    ArrayList<String> def;
    Context context;

    public DefinitionAdapter(ArrayList get_data, Context context) {
        this.get_data = get_data;
        this.context = context;
        def = (ArrayList<String>) get_data.get(0);
        sub_def = (HashMap<String, ArrayList<String>>) get_data.get(1);
    }


    @Override
    public int getGroupCount() {
        return def.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return sub_def.get(def.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return def.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
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
        if(!TextUtils.isEmpty(child))
            return true;
        else
        {
            Log.d("returningfalse","");
            return false;
        }
    }
}
