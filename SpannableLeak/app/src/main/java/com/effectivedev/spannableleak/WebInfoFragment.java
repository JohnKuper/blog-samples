package com.effectivedev.spannableleak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebInfoFragment extends Fragment {

    public static WebInfoFragment newInstance() {
        return new WebInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.effectivedev.spannableleak.R.layout.fragment_web_info, container, false);
    }
}
