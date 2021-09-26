package com.example.chandan.dictionary;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chandan on 06-07-2017.
 */

public class BackgroundTask {


    public static URL createtURL(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static String requestForHTTP(URL url) {
        HttpURLConnection oxfordConnection = null;
        String response = "";
        InputStream dataResponse = null;
        if (url == null) return response;

        try {
            oxfordConnection = (HttpURLConnection) url.openConnection();
            oxfordConnection.setRequestMethod("GET");
            oxfordConnection.setRequestProperty("Accept", "application/json");
            oxfordConnection.setRequestProperty("app_id", "94f7a33d");
            oxfordConnection.setRequestProperty("app_key", "6b9dfc1bcc980558a7d3cc8a570a3365");
            oxfordConnection.setConnectTimeout(10000);
            oxfordConnection.setReadTimeout(10000);
            oxfordConnection.connect();
            Log.d("Inside httprequest", "connected");
            if (oxfordConnection.getResponseCode() == 200) {
                Log.d("Inside httprequest", "success");
                dataResponse = oxfordConnection.getInputStream();
                response = byteToString(dataResponse);
            } else if(oxfordConnection.getResponseCode()==404){return"404";}
            else if(oxfordConnection.getResponseCode()==500){return"500";}
            else {
                Log.d("Inside httprequest", "unsuccess" + oxfordConnection.getResponseCode());
                return "";
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oxfordConnection != null)
                oxfordConnection.disconnect();
            Log.d("Inside httprequest", "disconnected");
            if (dataResponse != null)
                try {
                    dataResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return response;


    }

    public static String byteToString(InputStream is) throws IOException {
        StringBuffer data = new StringBuffer();
        String string;
        if (is != null) {
            InputStreamReader byteData = new InputStreamReader(is);
            BufferedReader bufferedData = new BufferedReader(byteData);
            string = bufferedData.readLine();
            while (string != null) {
                data.append(string);
                string = bufferedData.readLine();
            }
        }
        return data.toString();
    }

    public static class FindAntonyms {
        public static ArrayList antonymsFromServer(String url) {
            ArrayList<String> antonyms = new ArrayList<>();
            if (TextUtils.isEmpty(url)) return null;
            URL u = createtURL(url);
            Log.d("Inside datafromserver", "urlobjectcreated...reqforhttp");
            String jsonRespone = requestForHTTP(u);
            //Log.d("Inside datafromserver", "jsonresponserecd" + jsonRespone);

            if (jsonRespone.equals("404")) {
                Log.d("Inside FindAntonyms", "checkingfornulljsonresponse");
                antonyms.add("404");
                return antonyms;
            }
            if (jsonRespone.equals("500"))
            {
                antonyms.add("500");
                return antonyms;
            }
            if(TextUtils.isEmpty(jsonRespone)) return null;
            try {
                JSONObject jsonObject = new JSONObject(jsonRespone);
                JSONArray results = jsonObject.getJSONArray("results");
                //Log.d("result", results.length() + "");
                for (int results_array_pos = 0; results_array_pos < results.length(); results_array_pos++) {
                    JSONObject obj = results.getJSONObject(results_array_pos);
                    JSONArray lexical_entries = obj.getJSONArray("lexicalEntries");
                    //Log.d("respos ", results_array_pos + "lexicalentrieslength " + lexical_entries
                     //       .length()
                       //     + "");
                    for (int lexical_array_pos = 0; lexical_array_pos < lexical_entries.length();
                         lexical_array_pos++) {
                        JSONObject lexical_entries_obj = lexical_entries.getJSONObject
                                (lexical_array_pos);
                        JSONArray entries = lexical_entries_obj.getJSONArray("entries");
                        /*Log.d("respos ", results_array_pos + "lexpos " +
                                "" + lexical_array_pos + "entrieslen " +
                                "" + entries
                                .length() + "");*/
                        for (int entries_array_pos = 0; entries_array_pos < entries.length();
                             entries_array_pos++) {
                            JSONObject entries_obj = entries.getJSONObject(entries_array_pos);
                            JSONArray senses = entries_obj.getJSONArray("senses");
                           /* Log.d("respos ",
                                    results_array_pos + "lexpos " + lexical_array_pos + "entriespos " +
                                            entries_array_pos + "senslen " + senses.length() +
                                            "");*/
                            for (int senses_array_pos = 0; senses_array_pos < senses.length();
                                 senses_array_pos++) {
                                JSONObject senses_obj = senses.getJSONObject(senses_array_pos);
                                JSONArray antonym_array = senses_obj.getJSONArray("antonyms");
                                for (int antonym_array_pos = 0; antonym_array_pos < antonym_array
                                        .length(); antonym_array_pos++) {
                                    JSONObject antonym_arr_obj = antonym_array.getJSONObject
                                            (antonym_array_pos);
                                    String ant = antonym_arr_obj.getString("text");
                                    antonyms.add(ant);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return antonyms;
        }
    }

    public static class FindSynonyms {

        public static ArrayList synonymsFromServer(String url) {
            ArrayList<String> synonyms = new ArrayList<>();
            if (TextUtils.isEmpty(url)) return null;
            URL u = createtURL(url);
            Log.d("Inside FindSynonyms", "urlobjectcreated...reqforhttp");
            String jsonRespone = requestForHTTP(u);
           // Log.d("Inside FindSynonyms", "jsonresponserecd" + jsonRespone);

            if (jsonRespone.equals("404")) {
                Log.d("Inside FindAntonyms", "checkingfornulljsonresponse");
                synonyms.add("404");
                return synonyms;
            }
            if (jsonRespone.equals("500"))
            {
                synonyms.add("500");
                return synonyms;
            }
            if(TextUtils.isEmpty(jsonRespone)) return null;
            try {
                JSONObject jsonObject = new JSONObject(jsonRespone);
                JSONArray results = jsonObject.getJSONArray("results");
               // Log.d("result", results.length() + "");
                for (int results_array_pos = 0; results_array_pos < results.length(); results_array_pos++) {
                    JSONObject obj = results.getJSONObject(results_array_pos);
                    JSONArray lexical_entries = obj.getJSONArray("lexicalEntries");
                    /*Log.d("respos ", results_array_pos + "lexicalentrieslength " + lexical_entries
                            .length()
                            + "");*/
                    for (int lexical_array_pos = 0; lexical_array_pos < lexical_entries.length();
                         lexical_array_pos++) {
                        JSONObject lexical_entries_obj = lexical_entries.getJSONObject
                                (lexical_array_pos);
                        JSONArray entries = lexical_entries_obj.getJSONArray("entries");
                        /*Log.d("respos ", results_array_pos + "lexpos " +
                                "" + lexical_array_pos + "entrieslen " +
                                "" + entries
                                .length() + "");*/
                        for (int entries_array_pos = 0; entries_array_pos < entries.length();
                             entries_array_pos++) {
                            JSONObject entries_obj = entries.getJSONObject(entries_array_pos);
                            JSONArray senses = entries_obj.getJSONArray("senses");
                            /*Log.d("respos ", results_array_pos + "lexpos " + lexical_array_pos +
                                    "entriespos " +
                                            entries_array_pos + "senslen " + senses.length() +
                                            "");*/
                            for (int senses_array_pos = 0; senses_array_pos < senses.length();
                                 senses_array_pos++) {
                                JSONObject senses_obj = senses.getJSONObject(senses_array_pos);
                                JSONArray antonym_array = senses_obj.getJSONArray("synonyms");
                                for (int antonym_array_pos = 0; antonym_array_pos < antonym_array
                                        .length(); antonym_array_pos++) {
                                    JSONObject antonym_arr_obj = antonym_array.getJSONObject
                                            (antonym_array_pos);
                                    String ant = antonym_arr_obj.getString("text");
                                    synonyms.add(ant);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return synonyms;
        }

    }

    public static class FindSentences {
        public static ArrayList sentencesFromServer(String url) {
            if (TextUtils.isEmpty(url)) return null;
            URL u = createtURL(url);
            Log.d("Inside FinsSentences", "urlobjectcreated...reqforhttp");
            ArrayList res = new ArrayList();
            String jsonRespone = requestForHTTP(u);
            //Log.d("Inside FinsSentences", "jsonresponserecd" + jsonRespone);
            if (jsonRespone.equals("404")) {
                Log.d("Inside FindAntonyms", "checkingfornulljsonresponse");
                res.add("404");
                return res;
            }
            if (jsonRespone.equals("500"))
            {
                res.add("500");
                return res;
            }
            if(TextUtils.isEmpty(jsonRespone)) return null;

            ArrayList<String> heading = new ArrayList<>();
            HashMap<String, ArrayList<String>> sentences = new HashMap<>();

            try {
                JSONObject jsonObject = new JSONObject(jsonRespone);
                JSONArray results = jsonObject.getJSONArray("results");
               // Log.d("result", results.length() + "");
                for (int results_array_pos = 0; results_array_pos < results.length(); results_array_pos++) {
                    JSONObject obj = results.getJSONObject(results_array_pos);
                    JSONArray lexical_entries = obj.getJSONArray("lexicalEntries");
                 /*   Log.d("respos ", results_array_pos + "lexicalentrieslength " + lexical_entries
                            .length()
                            + "");*/
                    for (int lexical_array_pos = 0; lexical_array_pos < lexical_entries.length();
                         lexical_array_pos++) {
                        JSONObject lexical_arr_obj = lexical_entries.getJSONObject
                                (lexical_array_pos);
                        String lexical_category = lexical_arr_obj.getString("lexicalCategory");
                        heading.add(lexical_category);
                        JSONArray sentences_array = lexical_arr_obj.getJSONArray("sentences");
                        ArrayList<String> sen = new ArrayList<>();
                        for (int sentences_arr_pos = 0; sentences_arr_pos < sentences_array.length();
                             sentences_arr_pos++) {
                            JSONObject sentences_obj = sentences_array.getJSONObject
                                    (sentences_arr_pos);
                            String sentence = sentences_obj.getString("text");
                            sen.add(sentence);
                        }
                        sentences.put(lexical_category, sen);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            res.add(heading);
            res.add(sentences);

            return res;
        }
    }


    public static class FindWordMeanings {
        public static ArrayList meaningFromServer(String url) {
            if (TextUtils.isEmpty(url)) return null;
            URL u = createtURL(url);
            Log.d("Inside FinsWordMeanong", "urlobjectcreated...reqforhttp");
            String jsonRespone = requestForHTTP(u);
          //  Log.d("Inside FinsWordMeanong", "jsonresponserecd" + jsonRespone);

            ArrayList<String> definitions_headings = new ArrayList<>();
            ArrayList<String> examples_headings = new ArrayList<>();

            HashMap<String, ArrayList<String>> result = new HashMap<>();
            HashMap<String, ArrayList<String>> result_examples = new HashMap<>();
            ArrayList returner = new ArrayList();
            ArrayList returner_example = new ArrayList();

            ArrayList result_def_exm = new ArrayList();
            if (jsonRespone.equals("404")) {
                Log.d("Inside FindAntonyms", "checkingfornulljsonresponse");
                returner.add("404");
                return returner;
            }
            if (jsonRespone.equals("500"))
            {
                returner.add("500");
                return returner;
            }
            if(TextUtils.isEmpty(jsonRespone)) return null;


            try {
                JSONObject jsonObject = new JSONObject(jsonRespone);
                JSONArray results = jsonObject.getJSONArray("results");
                //Log.d("result", results.length() + "");
                for (int results_array_pos = 0; results_array_pos < results.length(); results_array_pos++) {
                    JSONObject obj = results.getJSONObject(results_array_pos);
                    JSONArray lexical_entries = obj.getJSONArray("lexicalEntries");
                  /*  Log.d("respos ", results_array_pos + "lexicalentrieslength " + lexical_entries
                            .length()
                            + "");*/
                    for (int lexical_array_pos = 0; lexical_array_pos < lexical_entries.length();
                         lexical_array_pos++) {
                        JSONObject lexical_entries_obj = lexical_entries.getJSONObject
                                (lexical_array_pos);
                        JSONArray entries = lexical_entries_obj.getJSONArray("entries");
                        /*Log.d("respos ", results_array_pos + "lexpos " +
                                "" + lexical_array_pos + "entrieslen " +
                                "" + entries
                                .length() + "");*/
                        for (int entries_array_pos = 0; entries_array_pos < entries.length();
                             entries_array_pos++) {
                            JSONObject entries_obj = entries.getJSONObject(entries_array_pos);
                            JSONArray senses = entries_obj.getJSONArray("senses");
                           /* Log.d("respos ",
                                    results_array_pos + "lexpos " + lexical_array_pos + "entriespos " +
                                            entries_array_pos + "senslen " + senses.length() +
                                            "");*/
                            for (int senses_array_pos = 0; senses_array_pos < senses.length();
                                 senses_array_pos++) {
                                String defn = "";
                                JSONObject senses_obj = senses.getJSONObject(senses_array_pos);
                                if (senses_obj.has("definitions")) {
                                    JSONArray definition = senses_obj.getJSONArray("definitions");
                                    defn = definition.getString(0);
                                    Log.d("defiatsenspos", senses_array_pos + " " + defn);
                                    definitions_headings.add(defn);
                                }
                                /*String exmpl="";
                                if(senses_obj.has("examples")) {
                                    JSONArray example = senses_obj.getJSONArray("examples");
                                    JSONObject example_obj = example.getJSONObject(0);
                                    exmpl = example_obj.getString("text");
                                    Log.d("examplesenspos", senses_array_pos+" "+exmpl);
                                    examples_headings.add(exmpl);
                                }else examples_headings.add("");*/

                                ArrayList<String> sub_def = new ArrayList<>();
                                // ArrayList<String> sub_exmpl = new ArrayList<>();
                                if (senses_obj.has("subsenses")) {
                                    JSONArray subsense_arr = senses_obj.getJSONArray("subsenses");
                                    for (int subsense_arr_pos = 0; subsense_arr_pos < subsense_arr
                                            .length(); subsense_arr_pos++) {
                                        JSONObject subsennses_obj = subsense_arr.getJSONObject
                                                (subsense_arr_pos);
                                        JSONArray subdef_arr = subsennses_obj.getJSONArray("definitions");
                                        String subdef = subdef_arr.getString(0);
                                        Log.d("subdefinintion", subdef);
                                        sub_def.add(subdef);
                                        /*if(subsennses_obj.has("examples")) {
                                            JSONArray exmpl_array = subsennses_obj.getJSONArray("examples");
                                            for (int example_array_pos = 0; example_array_pos < exmpl_array.length();
                                                 example_array_pos++) {
                                                JSONObject exampleobj = exmpl_array.getJSONObject
                                                        (example_array_pos);
                                                String exmple = exampleobj.getString("text");
                                                sub_exmpl.add(exmple);
                                                Log.d("subexample",exmple);
                                            }
                                        }else sub_exmpl.add("");*/
                                    }
                                } else {
                                    sub_def.add("");
                                }
                                result.put(defn, sub_def);
                                Log.d("resultat", senses_array_pos + "" + result.get(defn));
                            }
                        }
                    }
                }
                returner.add(definitions_headings);
                returner.add(result);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("resultsize", result.size() + "");
            Log.d("examplesize", result_examples.size() + "");
            /*for(int i=0;i<result.size();i++)
            {
                 Log.d("resultatoutside",i+""+result.get(definitions_headings.get(i)));
            }*/
            Log.d("result_def_exmsize", result_def_exm.size() + "");
            return returner;
        }
    }

}
