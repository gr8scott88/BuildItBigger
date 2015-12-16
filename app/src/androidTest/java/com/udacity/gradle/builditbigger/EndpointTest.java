package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

/**
 * Created by Scott on 12/8/2015.
 */

//Test case adapter from http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
public class EndpointTest extends ApplicationTestCase<Application> {
    String mJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public EndpointTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testEndpointGetJoke() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask(getContext());
        task.setListener(new EndpointsAsyncTask.EndpointGetJokeListener() {
            @Override
            public void onComplete(String joke, Exception e) {
                mJoke = joke;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));


    }
}
