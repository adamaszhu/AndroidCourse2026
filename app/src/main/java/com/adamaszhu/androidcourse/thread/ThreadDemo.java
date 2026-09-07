package com.adamaszhu.androidcourse.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;

public class ThreadDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_thread;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                simpleRunnable(),
                loopRunnableCrash(),
                loopRunnable()
        };
    }

    @Override
    public void initialize() {

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
}
