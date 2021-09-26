package com.example.chandan.dictionary;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * A simple {@link Fragment} subclass.
 */
public class MeaningFragment extends Fragment {

     String word="";
    static TextView wordText;
static ExpandableListView expandableListView;
static Context con;
    public MeaningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meaning, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
       expandableListView = getActivity().findViewById(R.id.exp_list);
        Log.d("Meaningonact","created");
        wordText = getActivity().findViewById(R.id.mean_word);
       // if(TextUtils.isEmpty(word)){expandableListView.setEmptyView(MainActivity.emptyView);}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void connectToNetwork(String query) {
        word = query;
        String url = "https://od-api.oxforddictionaries" +
                ".com:443/api/v1/entries/en/"+query+"/definitions";
        Log.d("urlofmeaning","");
        new WordMeaningTask().execute(url);
    }


    public interface OnFragmentInteractionListener {
    }

    public class WordMeaningTask extends AsyncTask<String,Void,ArrayList>
    {
        ProgressDialog p;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(con);
            p.setTitle("Wait..");
            p.setMessage("Finding Meaning");
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList doInBackground(String... url) {
           ArrayList result = BackgroundTask.FindWordMeanings.meaningFromServer
                   (url[0]);
            if(result==null) Log.d("resltis",result+"");
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            if(arrayList==null){ Log.d("nullsentence",arrayList+"");p.dismiss();
                Toast.makeText(con,"Unknown Error",Toast
                        .LENGTH_SHORT).show();}
            else if(arrayList.get(0)=="404"){Log.d("404",arrayList+"");p.dismiss();
                Toast.makeText(con,"No entry is found matching supplied word",Toast
                        .LENGTH_SHORT).show();}
            else if(arrayList.get(0)=="500"){Log.d("500",arrayList+"");p.dismiss();
                Toast.makeText(con,"Internal Error. An error occurred while processing the data",
                        Toast.LENGTH_SHORT).show();}
            else {
                wordText.setText(word);p.dismiss();
                expandableListView.setAdapter(new DefinitionAdapter(arrayList, con));
            }
        }
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){if (!word.isEmpty()) connectToNetwork(word);}
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MeaningOnResumecalled","");
    }
}
