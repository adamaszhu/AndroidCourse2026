package com.adamaszhu.androidcourse.thread;

import android.os.Message;

public class DownloadThread extends Thread {

    public interface DownloadListener {
        void updateProgress(Integer progress);
        void finish();
    }
    private Boolean hasFinished = false;
    private Integer progress;

    private DownloadListener listener;

    public DownloadThread(Integer progress) {
        this.progress = progress;
    }

    public void setDownloadListener(DownloadListener listener) {
        this.listener = listener;
    }

    public void pauseDownload() {
        hasFinished = true;
    }

    @Override
    public void run() {
        while (!hasFinished) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                hasFinished = true;
            }
            progress++;
            listener.updateProgress(progress);
            if (progress >= 100) {
                hasFinished = true;
                listener.finish();
            }
        }
    }
}
