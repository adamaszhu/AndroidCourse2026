package com.adamaszhu.androidcourse.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;
import com.adamaszhu.androidcourse.thread.DownloadThread;

public class ServiceDemo extends Demo {

    private static final String LOG_TAG = ServiceDemo.class.getName();

    private ServiceConnection serviceConnection;
    private ServiceConnection secondServiceConnection;
    private DownloadService.DownloadBinder serviceBinder;
    private DownloadService.DownloadBinder secondServiceBinder;

    private DownloadThread.DownloadListener downloadListener;

    @Override
    public int getTitleId() {
        return R.string.feature_service;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                startService(),
                stopService(),
                bindService(),
                unbindService(),
                holdService(),
                broadcast(),
                notification()
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

    private DemoButton bindService() {
        return new DemoButton(R.string.feature_service_bind, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DownloadService.class);
                activity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    private DemoButton unbindService() {
        return new DemoButton(R.string.feature_service_unbind, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceBinder.deregisterListener(downloadListener);
                serviceBinder = null;
                activity.unbindService(serviceConnection);
            }
        });
    }
    
    private DemoButton holdService() {
        return new DemoButton(R.string.feature_service_hold, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DownloadService.class);
                activity.bindService(intent, secondServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    private DemoButton broadcast() {
        return new DemoButton(R.string.feature_service_broadcast, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(DownloadService.BROADCAST_ACTION);
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String message = intent.getStringExtra(DownloadService.BROADCAST_RESULT);
                        getListener().updateOutput(message);
                    }
                };
                activity.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
            }
        });
    }

    private DemoButton notification() {
        return new DemoButton(R.string.feature_service_notification, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager manager = activity.getSystemService(NotificationManager.class);
                NotificationChannel channel = new NotificationChannel(
                        "ID",
                        "Download Service",
                        NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.au"));
                PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                Notification notification = new NotificationCompat.Builder(activity, "ID")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Test")
                        .setContentText("This is a test message")
                        .setContentIntent(pendingIntent)
                        .build();
                if(activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    manager.notify(10000, notification);
                }
            }
        });
    }

    @Override
    public void show() {
        if(activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String> launcher = activity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean o) {
                    if (o) {
                        Log.i(LOG_TAG, "Notification permission granted");
                    } else {
                        Log.i(LOG_TAG, "Notification permission rejected");
                    }
                }
            });
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    @Override
    public void initialize() {
        downloadListener = new DownloadThread.DownloadListener() {
            @Override
            public void updateProgress(Integer progress) {
                getListener().updateOutput(String.valueOf(progress));
            }

            @Override
            public void finish() {
                getListener().updateOutput("Done");
            }
        };
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(LOG_TAG, "Connected to service from " + ServiceDemo.class.getName());
                serviceBinder = (DownloadService.DownloadBinder) iBinder;
                serviceBinder.registerListener(downloadListener);
                serviceBinder.startDownloading();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(LOG_TAG, "Disconnect service from " + ServiceDemo.class.getName());
                serviceBinder.deregisterListener(downloadListener);
                serviceBinder = null;
            }
        };
        secondServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                secondServiceBinder = (DownloadService.DownloadBinder) iBinder;
                secondServiceBinder.startDownloading();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
    }
}
