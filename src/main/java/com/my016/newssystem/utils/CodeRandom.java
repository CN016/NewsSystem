package com.my016.newssystem.utils;

import java.util.Random;

public class CodeRandom {
    private static final Random random = new Random();
    public static String getCode(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        stringBuilder.append(random.nextInt(10));
        return stringBuilder.toString();
    }

    public static int getIntCode(){
        int sum = 0 ;
        for (int i = 0; i < 8; i++) {
            sum = sum*10 + random.nextInt(10);
        }
        return sum;
    }
}
