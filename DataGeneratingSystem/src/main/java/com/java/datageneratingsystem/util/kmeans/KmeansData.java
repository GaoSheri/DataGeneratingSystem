package com.java.datageneratingsystem.util.kmeans;

public class KmeansData {
    public double[][] data; // [数据个数][数据维度]
    public int length;  // data 数组长度，即数据个数
    public int dim;
    public int[] labels;
    public double[][] centers;
    public int[] centerCounts;

    public Double[][] sample;
    public Double maxValue;
    public Double minValue;

    public KmeansData(Double[][] sample, double[][] data, int length, int dim) {
        this.sample = sample;
        this.data = data;
        this.length = length;
        this.dim = dim;
    }
}
