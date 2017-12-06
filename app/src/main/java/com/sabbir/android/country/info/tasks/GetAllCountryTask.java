package com.sabbir.android.country.info.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sabbir.android.country.info.activities.parser.CountryParser;
import com.sabbir.android.country.info.models.Country;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sabbir on 11/20/17.
 */

public class GetAllCountryTask extends AsyncTask<String,Void,List<Country>> {

    public interface GetAllCountryTaskListener{
        void receivedCountryList(List<Country> countries);
    }

    private Context mContext;
    private GetAllCountryTaskListener listener;
    private ProgressDialog progressDialog;

    public GetAllCountryTask(Context context,GetAllCountryTaskListener listener){
        this.listener=listener;
        this.mContext=context;

    }

    @Override
    protected List<Country> doInBackground(String... strings) {
        String json=getData(strings[0]);
        try {
            return getAllCountries(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Country> getAllCountries(String json) throws JSONException {
        return CountryParser.getAllCountries(json);

    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        super.onPostExecute(countries);
        progressDialog.dismiss();
        listener.receivedCountryList(countries);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting data..Please Wait");
        progressDialog.show();
    }



    String getData(String url){
        String json;
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url(url)
                .build();

        try {
            Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
