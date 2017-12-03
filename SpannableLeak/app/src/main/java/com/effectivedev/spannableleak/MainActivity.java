package com.effectivedev.spannableleak;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SpannableLeakFragment.OnSpanClickListener {

    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = getSupportFragmentManager();
        if (mManager.findFragmentById(R.id.container) == null) {
            mManager.beginTransaction().add(R.id.container, SpannableLeakFragment.newInstance()).commit();
        }
    }

    @Override
    public void onSpanClick() {
        mManager.beginTransaction().replace(R.id.container, WebInfoFragment.newInstance()).addToBackStack("").commit();
    }
}
