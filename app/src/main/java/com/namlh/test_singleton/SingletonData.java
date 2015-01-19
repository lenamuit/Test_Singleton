package com.namlh.test_singleton;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by namlh on 1/19/15.
 */
@Singleton
public class SingletonData {

    private String savedText;

    @Inject
    public SingletonData(){}

    public String getSavedText() {
        return savedText;
    }

    public void setSavedText(String savedText) {
        this.savedText = savedText;
    }
}
