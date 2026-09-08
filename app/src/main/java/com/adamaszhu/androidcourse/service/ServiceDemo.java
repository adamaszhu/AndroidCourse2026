package com.adamaszhu.androidcourse.service;

import android.app.Service;
import android.content.Intent;
import android.view.View;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;

public class ServiceDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_service;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                startService(),
                stopService()
        };
    }

    private DemoButton startService() {
        return new DemoButton(R.string.feature_service_start, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DownloadService.class);
                intent.putExtra(DownloadService.PROGRESS_TAG, 50);
                activity.startService(intent);
            }
        });
    }

    private DemoButton stopService() {
        return new DemoButton(R.string.feature_service_stop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DownloadService.class);
                activity.stopService(intent);
            }
        });
    }
    
    private DemoButton feature() {
        return new DemoButton(R.string.feature_service, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
