package com.sabbir.android.country.info.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
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
        JSONArray jsonArray=new JSONArray(json);
        List<Country> countries=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            Country country= new Gson().fromJson(jsonArray.getJSONObject(i).toString(),Country.class);
            country.setTranslations(getTranslations());
            countries.add(country);
        }
        return countries;
    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        super.onPostExecute(countries);
        listener.receivedCountryList(countries);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mContext);
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
