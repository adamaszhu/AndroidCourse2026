package com.adamaszhu.androidcourse.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.thread.DownloadEvent;
import com.adamaszhu.androidcourse.thread.DownloadThread;

import java.util.ArrayList;

public class DownloadService extends Service {

    public class DownloadBinder extends Binder {
        public void startDownloading() {
            DownloadService.this.startDownloading(0);
        }

        public void stopDownloading() {

        }

        public void registerListener(DownloadThread.DownloadListener listener) {
            if(!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }

        public void deregisterListener(DownloadThread.DownloadListener listener) {
            if(listeners.contains(listener)) {
                listeners.remove(listener);
            }
        }
    }

    public static final String PROGRESS_TAG = "PROGRESS";
    public static final String BROADCAST_ACTION = "DOWNLOAD";
    public static final String BROADCAST_RESULT = "RESULT";
    private static final String LOG_TAG = DownloadService.class.getName();
    private DownloadThread downloadThread;

    private DownloadBinder binder;

    private Handler handler;

    private ArrayList<DownloadThread.DownloadListener> listeners;

    public DownloadService() {
        binder = new DownloadBinder();
        listeners = new ArrayList<DownloadThread.DownloadListener>();
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                DownloadEvent event = (DownloadEvent)msg.obj;
                switch (event.getState()) {
                    case DOWNLOADING:
                        for(DownloadThread.DownloadListener listener : listeners) {
                            listener.updateProgress(event.getProgress());
                        }
                        break;
                    case FINISHED:
                        for(DownloadThread.DownloadListener listener : listeners) {
                            listener.finish();
                        }
                        break;
                }
            }
        };
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
        return binder;
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
                Message msg = new Message();
                msg.obj = new DownloadEvent(progress, DownloadEvent.State.DOWNLOADING);
                handler.sendMessage(msg);
            }

            @Override
            public void finish() {
                Log.i(LOG_TAG, "Finished");
                Message msg = new Message();
                msg.obj = new DownloadEvent(100, DownloadEvent.State.FINISHED);
                handler.sendMessage(msg);
                stopForeground();
                // Broadcast
                Intent intent = new Intent();
                intent.setAction(BROADCAST_ACTION);
                intent.putExtra(BROADCAST_RESULT, "Download Finished");
                sendBroadcast(intent);
            }
        });
        downloadThread.start();
        beginForeground();
    }

    private void stopDownloading() {
        if (downloadThread != null) {
            downloadThread.pauseDownload();
            downloadThread = null;
        }
    }

    @SuppressLint("ForegroundServiceType")
    private void beginForeground() {
        NotificationManager manager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(
                "ID",
                "Download Service",
                NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(channel);
        Notification notification = new NotificationCompat.Builder(this, "ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test")
                .setContentText("This is a test message")
                .setOngoing(true)
                .build();
        if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            startForeground(1, notification);
        }
    }

    private void stopForeground() {
        stopForeground(Service.STOP_FOREGROUND_REMOVE);
    }
}