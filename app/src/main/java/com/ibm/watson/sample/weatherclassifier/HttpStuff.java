package com.ibm.watson.sample.weatherclassifier;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ajay on 1/20/2016.
 */
public class HttpStuff extends AsyncTask<String, Integer, String> {
    final String baseURL ="https://gateway.watsonplatform.net/natural-language-classifier/api/v1/classifiers/";

    //add data specific to your instance here
    final String classifierID = "";
    final String username="";
    final String password = "";

    MainActivity mainActivity = null;

    public HttpStuff(MainActivity act)
    {
        mainActivity = act;
    }
    @Override
    protected String doInBackground(String... question) {
        String retVal = "none";
        //build a request to talk to Watson
        OkHttpClient client = new OkHttpClient();
        String creds = Credentials.basic(username, password);
        Request request = new Request.Builder()
                .url(baseURL + classifierID + "/classify?text=" + question[0])
                .header("Authorization",creds)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            retVal = response.body().string();
        }
        catch (IOException ex)
        {
            retVal="Uh oh..:"+ ex.getMessage();
        }
        return retVal;
    }


    protected void onProgressUpdate(Integer... progress) {
    //TODO
    }

    @Override
    protected void onPostExecute(String retVal) {
        //network call came back. lets parse the resultant JSON
        String result = "none";
        String topClass = "none";
        try {
            JSONObject answer = new JSONObject(retVal);
            topClass = answer.get("top_class").toString();
            result = "Watson says, this relates to: "+topClass;
        }
        catch (JSONException ex)
        {
            //probably not a JSON
            result = "Hmm..."+retVal;
        }
        mainActivity.updateResult(result);
    }

    protected void onPreExecute()
    {
        mainActivity.updateResult("Asking Watson to classify...");
    }
}

