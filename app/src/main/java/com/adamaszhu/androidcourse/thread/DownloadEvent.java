package com.adamaszhu.androidcourse.thread;

public class DownloadEvent {
    public enum State {
        DOWNLOADING, FINISHED, CANCELLED, FAILED
    }

    private Integer progress;

    private State state;

    public DownloadEvent(Integer progress,
                         State state) {
        this.progress = progress;
        this.state = state;
    }

    public Integer getProgress() {
        return progress;
    }

    public State getState() {
        return state;
    }
}
