package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.scott.myapplicationjokebackend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Scott on 12/5/2015.
 */

//Code snipped adapter from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
//Testing listener adapter from http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    private EndpointGetJokeListener mListener = null;
    private Exception mError = null;

    public EndpointsAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {

            return myApiService.getJoke().execute().getData();
            //myApiService.sayHi()
            //return myApiService.getJoke.execute().getData();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.mListener != null){
            this.mListener.onComplete(result, mError);
        }
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    public EndpointsAsyncTask setListener(EndpointGetJokeListener listener) {
        this.mListener = listener;
        return this;
    }

    public static interface EndpointGetJokeListener {
        public void onComplete(String joke, Exception e);
    }

}