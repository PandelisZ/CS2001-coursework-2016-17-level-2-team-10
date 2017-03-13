package com.example.com.reelreviews;

/**
 * Created by subam on 2/15/17.
 */

/**
 * This is a useful callback mechanism so we can abstract our AsyncTasks out into separate, re-usable
 * and testable classes yet still retain a hook back into the calling activity. Basically, it'll make classes
 * cleaner and easier to unit test.
 *
 * @param <T>
 */

public interface AsyncTaskCompleteListener<T> {

    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
     void onTaskComplete(T result);

}
