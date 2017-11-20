package com.sabbir.android.country.info.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sabbir.android.country.info.R;
import com.sabbir.android.country.info.constans.AppConstants;
import com.sabbir.android.country.info.models.Country;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by sabbir on 11/20/17.
 */

public class AllCountryFragment extends BaseFragment {

    public AllCountryFragment(){

    }

    @BindView(R.id.country_list_view)
    ListView mListview;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_all_country;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Country> countries= (ArrayList<Country>) getArguments().getSerializable(AppConstants.COUNTRY_LIST_INTENT_KEY);

        ArrayList<String> names=new ArrayList<>();

        for (Country country:countries){
            names.add(country.getName());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,names);

        mListview.setAdapter(adapter);
    }

    public static AllCountryFragment newInstance(ArrayList<Country> countries){
        AllCountryFragment fragment=new AllCountryFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(AppConstants.COUNTRY_LIST_INTENT_KEY,countries);
        fragment.setArguments(bundle);
        return fragment;
    }
}
