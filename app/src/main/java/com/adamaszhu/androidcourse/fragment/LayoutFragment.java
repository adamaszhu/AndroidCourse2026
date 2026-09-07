package com.adamaszhu.androidcourse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adamaszhu.androidcourse.R;

public class LayoutFragment extends Fragment {

    private static final String LAYOUT_TYPE = "Layout";

    private String layoutType;

    public static LayoutFragment newInstant(String type) {
        LayoutFragment fragment = new LayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LAYOUT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutType = getArguments().getString(LAYOUT_TYPE);
        } else {
            layoutType = "Unknown";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView tvLayoutType = view.findViewById(R.id.tv_layout_type);
        tvLayoutType.setText(layoutType);
        return view;
    }
}
