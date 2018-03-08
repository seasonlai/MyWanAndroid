package com.example.wellhope.mywanandroid.ui.personal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void lazyInit() {

    }

}
