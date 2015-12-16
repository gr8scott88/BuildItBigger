/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Scott.myapplicationjokebackend;

import com.example.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "myapplicationjokebackend.Scott.example.com",
    ownerName = "myapplicationjokebackend.Scott.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    @ApiMethod(name = "getJoke")
    public MyJoke getJoke(){
        JokeProvider jokeProvider = new JokeProvider();
        MyJoke joke = new MyJoke();
        joke.setData(jokeProvider.getJoke());
        return joke;
    }

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);
        return response;
    }

}
