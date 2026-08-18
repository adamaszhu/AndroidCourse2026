package com.adamaszhu.androidcourse.fragment;

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
    }
}