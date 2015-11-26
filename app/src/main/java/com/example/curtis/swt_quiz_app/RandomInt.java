package com.example.curtis.swt_quiz_app;

import java.util.Random;

/**
 * Created by curtis on 26/11/15.
 */
public class RandomInt {

    public int getRandInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;

    }
}
