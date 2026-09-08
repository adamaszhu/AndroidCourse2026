package com.adamaszhu.androidcourse.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ThreadDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_thread;
    }

    private DownloadThread downloadThread;
    private Integer progress = 0;

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                simpleRunnable(),
                loopRunnableCrash(),
                loopRunnable(),
                handlerThread(),
                downloadEvent(),
                eventBus(),
                customThread(),
                stopCustomThread(),
                futureTask(),
                threadPool()
        };
    }

    @Override
    public void show() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void hide() {
        EventBus.getDefault().unregister(this);
    }

    private DemoButton simpleRunnable() {
        return new DemoButton(R.string.feature_thread_simple_runnable, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            getListener().updateOutput("Completed");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                thread.run();
            }
        });
    }

    private DemoButton loopRunnableCrash() {
        return new DemoButton(R.string.feature_thread_loop_runnable_crash, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    Boolean hasFinished = false;
                    Integer progress = 0;
                    @Override
                    public void run() {
                       while (!hasFinished) {
                           try {
                               Thread.sleep(100);
                           } catch (InterruptedException e) {
                               hasFinished = true;
                           }
                           progress++;
                           getListener().updateOutput(String.valueOf(progress));
                           if (progress == 100) {
                               hasFinished = true;
                           }
                       }
                    }
                });
                thread.start();
            }
        });
    }

    private DemoButton loopRunnable() {
        return new DemoButton(R.string.feature_thread_loop_runnable, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Integer progress = (Integer) msg.obj;
                        getListener().updateOutput(String.valueOf(progress));
                    }
                };
                Thread thread = new Thread(new Runnable() {
                    Boolean hasFinished = false;
                    Integer progress = 0;
                    @Override
                    public void run() {
                        while (!hasFinished) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                hasFinished = true;
                            }
                            progress++;
                            Message msg = new Message();
                            msg.obj = progress;
                            handler.sendMessage(msg);
                            if (progress == 100) {
                                hasFinished = true;
                            }
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private DemoButton handlerThread() {
        return new DemoButton(R.string.feature_thread_handler, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandlerThread handlerThread = new HandlerThread("Background thread");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Integer progress = (Integer) msg.obj;
                        getListener().updateOutput(String.valueOf(progress));
                    }
                };
                Thread thread = new Thread(new Runnable() {
                    Boolean hasFinished = false;
                    Integer progress = 0;

                    @Override
                    public void run() {
                        while (!hasFinished) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                hasFinished = true;
                            }
                            progress++;
                            Message msg = new Message();
                            msg.obj = progress;
                            handler.sendMessage(msg);
                            if (progress == 100) {
                                hasFinished = true;
                            }
                        }
                    }
                });
                thread.start();
            }
        });
    }
    private DemoButton downloadEvent() {
        return new DemoButton(R.string.feature_thread_download_event, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        DownloadEvent event = (DownloadEvent) msg.obj;
                        switch (event.getState()) {
                            case DOWNLOADING:
                                getListener().updateOutput(String.valueOf(event.getProgress()));
                                break;
                            case FINISHED:
                                getListener().updateOutput("Done");
                                break;
                        }
                    }
                };
                Thread thread = new Thread(new Runnable() {
                    Boolean hasFinished = false;
                    Integer progress = 0;

                    @Override
                    public void run() {
                        while (!hasFinished) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                hasFinished = true;
                            }
                            progress++;
                            Message msg = new Message();
                            if (progress == 100) {
                                hasFinished = true;
                                DownloadEvent event = new DownloadEvent(progress, DownloadEvent.State.FINISHED);
                                msg.obj = event;
                            } else {
                                DownloadEvent event = new DownloadEvent(progress, DownloadEvent.State.DOWNLOADING);
                                msg.obj = event;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                });
                thread.start();
            }
        });
    }


    private DemoButton eventBus() {
        return new DemoButton(R.string.feature_thread_event_bus, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    Boolean hasFinished = false;
                    Integer progress = 0;

                    @Override
                    public void run() {
                        while (!hasFinished) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                hasFinished = true;
                            }
                            progress++;
                            DownloadEvent event;
                            if (progress == 100) {
                                hasFinished = true;
                                event = new DownloadEvent(progress, DownloadEvent.State.FINISHED);
                            } else {
                                event = new DownloadEvent(progress, DownloadEvent.State.DOWNLOADING);
                            }
                            EventBus.getDefault().post(event);
//                            EventBus.getDefault().post(progress);
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private DemoButton customThread() {
        return new DemoButton(R.string.feature_thread_custom_thread, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ThreadDemo.this.downloadThread != null) {
                    return;
                }
                DownloadThread thread = new DownloadThread(progress);
                thread.setDownloadListener(new DownloadThread.DownloadListener() {
                    @Override
                    public void updateProgress(Integer progress) {
                        EventBus.getDefault().post(new DownloadEvent(progress, DownloadEvent.State.DOWNLOADING));
                        ThreadDemo.this.progress = progress;
                    }

                    @Override
                    public void finish() {
                        EventBus.getDefault().post(new DownloadEvent(100, DownloadEvent.State.FINISHED));
                    }
                });
                thread.start();
                ThreadDemo.this.downloadThread = thread;
            }
        });
    }

    private DemoButton stopCustomThread() {
        return new DemoButton(R.string.feature_thread_stop_custom_thread, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ThreadDemo.this.downloadThread != null) {
                    ThreadDemo.this.downloadThread.pauseDownload();
                    ThreadDemo.this.downloadThread = null;
                }
            }
        });
    }

    private DemoButton futureTask() {
        return new DemoButton(R.string.feature_thread_future_task, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(5000);
                        return 100;
                    }
                });
                Thread thread = new Thread(task);
                thread.start();
// Block UI
//                try {
//                    Integer value = task.get();
//                    getListener().updateOutput(String.valueOf(value));
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                Thread waitThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Integer value = task.get();
                            EventBus.getDefault().post(value);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                waitThread.start();
            }
        });
    }

    private DemoButton threadPool() {
        return new DemoButton(R.string.feature_thread_pool, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executor = Executors.newFixedThreadPool(2);
                Future<Integer> task = executor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(5000);
                        return 100;
                    }
                });
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Integer value = task.get();
                            EventBus.getDefault().post(value);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DownloadEvent event) {
        switch (event.getState()) {
            case DOWNLOADING:
                getListener().updateOutput("DownloadEvent+" + String.valueOf(event.getProgress()));
                break;
            case FINISHED:
                getListener().updateOutput("Done");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Integer progress) {
        getListener().updateOutput("Progress:" +String.valueOf(progress));
    }
}
