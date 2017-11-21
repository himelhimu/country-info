package com.sabbir.android.country.info.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sabbir.android.country.info.R;
import com.sabbir.android.country.info.constans.AppConstants;
import com.sabbir.android.country.info.models.Country;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by sabbir on 11/21/17.
 */

public class CountryDetailsFragment extends BaseFragment {

    @BindView(R.id.tv_details)
    TextView tvDetails;

    public CountryDetailsFragment(){

    }

    public static CountryDetailsFragment newInstance(Country country){
        CountryDetailsFragment fragment=new CountryDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(AppConstants.COUNTRY_LIST_INTENT_KEY,country);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_country_details;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        Country  country= (Country) getArguments().getSerializable(AppConstants.COUNTRY_LIST_INTENT_KEY);
        for (Map.Entry<String, Object> entry:pojo2Map(country).entrySet()){
            TextView textView=new TextView(getContext());
            textView.setText((String)entry.getValue());
        }
       tvDetails.setText(country.toString());
    }

    public static Map<String, Object> pojo2Map(Object obj) {
        Map<String, Object> hashMap = new HashMap<>();
        try {
            Class<?> c = obj.getClass();
            Method m[] = c.getMethods();
            for (Method aM : m) {
                if (aM.getName().indexOf("get") == 0) {
                    String name = aM.getName().toLowerCase().substring(3, 4) + aM.getName().substring(4);
                    hashMap.put(name, aM.invoke(obj));
                }
            }
        } catch (Throwable e) {
            //log error
        }
        return hashMap;
    }
}
