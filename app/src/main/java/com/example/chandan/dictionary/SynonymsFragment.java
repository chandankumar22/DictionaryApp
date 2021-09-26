package com.example.chandan.dictionary;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SynonymsFragment extends Fragment {

static ListView lv;
    String word="";
    static Context context;
    static TextView wordText;

    public SynonymsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_synonyms, container, false);
    }

    public interface OnFragmentInteractionListener {
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        lv = getActivity().findViewById(R.id.list_synonyms);
        Log.d("Synonymonact","created");
        wordText = getActivity().findViewById(R.id.syn_word);

    }

    public void connectToNetwork(String query) {
    word=query;
        String url = "https://od-api.oxforddictionaries" +
                ".com:443/api/v1/entries/en/"+query+"/synonyms";
        Log.d("urlofantonymexecuted","");
        new SynonymsTask().execute(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SynonymOnResumecalled","");
    }

    public class SynonymsTask extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog p;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(context);
            p.setTitle("Wait..");
            p.setMessage("Finding Synonyms");
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            return BackgroundTask.FindSynonyms.synonymsFromServer(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            if(arrayList==null){ Log.d("nullsentence",arrayList+"");p.dismiss();
                Toast.makeText(context,"Unknown Error",Toast
                        .LENGTH_SHORT).show();}
            else if(arrayList.get(0)=="404"){Log.d("404",arrayList+"");p.dismiss();
                Toast.makeText(context,"No entry is found matching supplied word",Toast
                        .LENGTH_SHORT).show();}
            else if(arrayList.get(0)=="500"){Log.d("500",arrayList+"");p.dismiss();
                Toast.makeText(context,"Internal Error. An error occurred while processing the data",Toast.LENGTH_SHORT).show();}
            else {
                Log.d("SyninymFragmentcontext",""+context);p.dismiss();
                if(context!=null){
                    wordText.setText(word);
                 lv.setAdapter(new SynonymsAdapter(context,arrayList));}
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){if (!word.isEmpty()) connectToNetwork(word);}
    }
}
