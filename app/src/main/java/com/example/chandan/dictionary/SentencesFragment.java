package com.example.chandan.dictionary;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class SentencesFragment extends Fragment {
    static Context con;
    static TextView wordText;
    static ExpandableListView expandableListView;
String word="";
    public SentencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sentences, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        expandableListView = getActivity().findViewById(R.id.exp_list_sentences);
        Log.d("Sentencesonact",":created");
        wordText = getActivity().findViewById(R.id.sen_word);
    }
    public void connectToNetwork(String query) {
        word=query;
        String url = "https://od-api.oxforddictionaries" +
                ".com:443/api/v1/entries/en/"+query+"/sentences";
        Log.d("urlofsentenceexecuted","");
        new SentencesTask().execute(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SentebcesOnResumecalled","");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
    }


    public class SentencesTask extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog p;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(con);
            p.setTitle("Wait..");
            p.setMessage("Finding Sentences");
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            return BackgroundTask.FindSentences.sentencesFromServer(strings[0]);
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
                expandableListView.setAdapter(new SentencesAdapter(arrayList,con));
            }
        }
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){if (!word.isEmpty()) connectToNetwork(word);}
    }
}

