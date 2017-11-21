package com.sabbir.android.country.info.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sabbir.android.country.info.R;
import com.sabbir.android.country.info.constans.AppConstants;
import com.sabbir.android.country.info.models.Country;
import com.sabbir.android.country.info.tasks.GetAllCountryTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sabbir on 11/20/17.
 */

public class AllCountryFragment extends BaseFragment implements GetAllCountryTask.GetAllCountryTaskListener {

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

        mListview.setOnItemClickListener((adapterView, view1, i, l) -> {
            String name=names.get(i);
            GetAllCountryTask getAllCountryTask=new GetAllCountryTask(getContext(),this);
            getAllCountryTask.execute(makeUrl(name));
        });
    }

    private String makeUrl(String name) {
        return AppConstants.SINGLE_COUNTRY_QUERY_URL+name;
    }

    public static AllCountryFragment newInstance(ArrayList<Country> countries){
        AllCountryFragment fragment=new AllCountryFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(AppConstants.COUNTRY_LIST_INTENT_KEY,countries);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void receivedCountryList(List<Country> countries) {
        Country country=countries.get(0);

        Fragment fragment=CountryDetailsFragment.newInstance(country);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}
