package com.sabbir.android.country.info.activities.parser;

import com.google.gson.Gson;
import com.sabbir.android.country.info.models.Country;
import com.sabbir.android.country.info.models.Currency;
import com.sabbir.android.country.info.models.Language;
import com.sabbir.android.country.info.models.Translations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 11/20/17.
 */

public class CountryParser {

    public static List<Country> getAllCountries(String json) throws JSONException {
        Gson gson=new Gson();
        JSONArray jsonArray=new JSONArray(json);
        List<Country> countries=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Country country= new Gson().fromJson(jsonObject.toString(),Country.class);
            country.setTranslations(getTranslations(gson,jsonObject.getJSONObject("translations")));
            country.setLanguages(getLanguages(gson,jsonObject.getJSONArray("languages")));
            country.setCurrencies(getCurrencies(gson,jsonObject.getJSONArray("currencies")));
            countries.add(country);
        }

        return countries;
    }

    private static List<Currency> getCurrencies(Gson gson, JSONArray currencies) throws JSONException {
        List<Currency> currencies1=new ArrayList<>();
        JSONObject jsonObject=currencies.getJSONObject(0);
        Currency currency=gson.fromJson(jsonObject.toString(),Currency.class);
        currencies1.add(currency);
        return currencies1;
    }

    private static List<Language> getLanguages(Gson gson, JSONArray languages) throws JSONException {
        JSONObject jsonObject=languages.getJSONObject(0);
        List<Language> languageList=new ArrayList<>();
        Language language=gson.fromJson(jsonObject.toString(),Language.class);
        languageList.add(language);
        return languageList;

    }

    private static Translations getTranslations(Gson gson, JSONObject translations) {
        return gson.fromJson(translations.toString(),Translations.class);

    }


}
