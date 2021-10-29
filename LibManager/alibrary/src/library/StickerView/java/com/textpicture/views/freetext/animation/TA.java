package com.textpicture.views.freetext.animation;

public interface TA<T> {
        void start();

        void stop();

        long getDuration();

        T getValue();
    }