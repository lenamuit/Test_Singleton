package com.namlh.test_singleton;

import dagger.Module;

/**
 * Created by namlh on 1/19/15.
 */
@Module(
        injects = {
                MainActivity.class,
                MainActivity2.class,
        }
)
public class AppModule {

}
