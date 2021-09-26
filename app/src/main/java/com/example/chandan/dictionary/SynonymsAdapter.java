package com.example.chandan.dictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by chandan on 22-07-2017.
 */

public class SynonymsAdapter extends ArrayAdapter<ArrayList<String>> {
    LayoutInflater layoutInflater;
    ArrayList<String> data;
    Context context;
    public SynonymsAdapter(@NonNull Context context, ArrayList data) {
        super(context,0,data);
        this.context=context;
        Log.d("contextis",context+"");
        this.data=data;
        Log.d("datasizeis",data.size()+"");
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null) {
            layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.row_layout, parent, false);
        }

        TextView t = convertView.findViewById(R.id.text_id);
        Log.d("setted text synonyms is",data.get(position));
        t.setText(data.get(position));
        return convertView;
    }
}
