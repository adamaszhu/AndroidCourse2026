package com.adamaszhu.androidcourse.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.adamaszhu.androidcourse.thread.DownloadThread;

public class DownloadService extends Service {

    public static final String PROGRESS_TAG = "PROGRESS";
    private static final String LOG_TAG = DownloadService.class.getName();

    private DownloadThread downloadThread;

    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Integer progress = intent.getIntExtra(PROGRESS_TAG, 0);
        startDownloading(progress);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopDownloading();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startDownloading(Integer progress) {
        if (downloadThread != null) {
            return;
        }
        downloadThread = new DownloadThread(progress);
        downloadThread.setDownloadListener(new DownloadThread.DownloadListener() {
            @Override
            public void updateProgress(Integer progress) {
                Log.i(LOG_TAG, "New progress: " + String.valueOf(progress));
            }

            @Override
            public void finish() {
                Log.i(LOG_TAG, "Finished");
            }
        });
        downloadThread.start();
    }

    private void stopDownloading() {
        if (downloadThread != null) {
            downloadThread.pauseDownload();
            downloadThread = null;
        }
    }
}