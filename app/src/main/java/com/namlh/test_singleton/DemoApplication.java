package com.namlh.test_singleton;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

/**
 * Created by namlh on 1/19/15.
 */
public class DemoApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new AppModule());
    }

    /**
     * inject an object
     * @param context
     * @param obj
     */
    public static void inject(Context context, Object obj){
        ((DemoApplication) context.getApplicationContext()).objectGraph.inject(obj);
    }
}
