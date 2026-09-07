package com.adamaszhu.androidcourse.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.utility.BaseActivity;
import com.adamaszhu.androidcourse.databinding.ActivityFragmentBinding;

public class FragmentActivity extends BaseActivity {

    private ActivityFragmentBinding binding;

    @Override
    public void load() {
        binding = ActivityFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void setup() {
        binding.btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLayoutFragment(String.valueOf(binding.btnList.getText()));
            }
        });
        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLayoutFragment(String.valueOf(binding.btnMap.getText()));
            }
        });
    }

    private void setLayoutFragment(String layoutType) {
        LayoutFragment fragment = LayoutFragment.newInstant(layoutType);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(layoutType);
        transaction.commit();
    }
}