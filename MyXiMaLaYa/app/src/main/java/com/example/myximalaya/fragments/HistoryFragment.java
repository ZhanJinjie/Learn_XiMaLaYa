package com.example.myximalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myximalaya.R;
import com.example.myximalaya.base.BaseFragment;

public class HistoryFragment extends BaseFragment { // 历史
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_history, container, false);
        return rootView;
    }
}
