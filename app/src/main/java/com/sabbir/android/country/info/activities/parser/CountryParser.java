package com.sabbir.android.country.info.activities.parser;

import com.google.gson.Gson;
import com.sabbir.android.country.info.models.Country;
import com.sabbir.android.country.info.models.Currency;
import com.sabbir.android.country.info.models.Language;
import com.sabbir.android.country.info.models.RegionalBloc;
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
            country.setLatlng(getLatLang(gson,jsonObject.getJSONArray("latlng")));
            country.setRegionalBlocs(getRegionalBloc(gson,jsonObject.getJSONArray("regionalBlocs")));
            country.setAltSpellings(getAltSpellings(gson,jsonObject.getJSONArray("altSpellings")));
            countries.add(country);
        }

        return countries;
    }

    private static List<String> getAltSpellings(Gson gson, JSONArray altSpellings) throws JSONException {
        List<String> altStrings=new ArrayList<>();
        for (int i=0;i<altSpellings.length();i++){
            altStrings.add(altSpellings.getString(i));

        }
        return altStrings;
    }

    private static List<RegionalBloc> getRegionalBloc(Gson gson, JSONArray regionalBlocs) throws JSONException {
        List<RegionalBloc>  regionalBlocsList=new ArrayList<>();
        for (int i=0;i<regionalBlocs.length();i++){
            RegionalBloc regionalBloc=gson.fromJson(regionalBlocs.getJSONObject(i).toString(),RegionalBloc.class);
            regionalBlocsList.add(regionalBloc);
        }
        return regionalBlocsList;
    }

    private static List<Double> getLatLang(Gson gson, JSONArray latlng) throws JSONException {
        List<Double> integers=new ArrayList<>();
        for (int i=0;i<latlng.length();i++){
            integers.add(latlng.getDouble(i));
        }
        return integers;
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
