package com.java.datageneratingsystem.util.kmeans;

public class KmeansParam {
    public static final int CENTER_ORDER = 0;
    public static final int CENTER_RANDOM = 1;
    public static final int MAX_ATTEMPTS = 4000;
    public static final double MIN_CRITERIA = 1.0D;
    public double criteria = 1.0D;
//    public int attempts = 4000;
    public int attempts = 5;
    public int initCenterMehtod = 0;
    public boolean isDisplay = true;

    public KmeansParam() {
    }
}
