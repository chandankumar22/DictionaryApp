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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AntonymsFragment extends Fragment {

    static ListView lv;
    static Context context;
    static TextView wordText;
String word="";
    public AntonymsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_antonyms, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();Log.d("AFonactivitycreated","contextis"+context);
        lv = getActivity().findViewById(R.id.list_antonyms);
        Log.d("Antonymsonact","created");
        wordText = getActivity().findViewById(R.id.ant_word);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("AntonymOnResumecalled","");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void connectToNetwork(String query) {
        word=query;
        String url = "https://od-api.oxforddictionaries" +
                ".com:443/api/v1/entries/en/"+query+"/antonyms";
        Log.d("urlofantonymexecuted","");
        new AntonymsTask().execute(url);
    }


    public class AntonymsTask extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog p;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(context);
            p.setTitle("Wait..");
            p.setMessage("Finding Antonyms");
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            Log.d("doiantonymnbg",strings[0]);
            return BackgroundTask.FindAntonyms.antonymsFromServer(strings[0]);
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
                Log.d("AntonymFragmentcontext",""+context);p.dismiss();
                if(context!=null){
                    wordText.setText(word);
                 lv.setAdapter(new SynonymsAdapter(context,arrayList));}
            }
        }
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){if (!word.isEmpty()) connectToNetwork(word);}
    }
}
