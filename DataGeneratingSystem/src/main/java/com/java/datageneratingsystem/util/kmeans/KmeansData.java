package com.java.datageneratingsystem.util.kmeans;

public class KmeansData {

    public int length;
    public int dim;
    public Double[][] sample;
    public Double maxValue;
    public Double minValue;
    public KmeansResult result;


    public KmeansData(Double[][] sample, int length, int dim) {
        this.sample = sample;
        this.length = length;
        this.dim = dim;
    }

}
