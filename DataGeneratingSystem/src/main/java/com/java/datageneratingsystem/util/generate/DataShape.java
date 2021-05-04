package com.java.datageneratingsystem.util.generate;

import java.util.Map;

public class DataShape {
    int dim;
    Double[] maxValues;
    Double[] minValues;

    public DataShape(int dim) {
        this.dim = dim;
        maxValues = new Double[dim];
        minValues = new Double[dim];
    }

    public void setUpRange(int dimIndex, Double min, Double max) throws Exception {
        if (max < min)
            throw new Exception("max value should greater than min value!");
        minValues[dimIndex] = min;
        maxValues[dimIndex] = max;
    }

    public Double[] getRangeOfDim(int dimIndex) {
        Double[] range = new Double[2];
        range[0] = minValues[dimIndex];
        range[1] = maxValues[dimIndex];
        return range;
    }

    public Double getCrossDist() {
        double result = 0.0D;
        for (int i = 0; i < dim; i++) {
            double temp = maxValues[i] - minValues[i];
            temp *= temp;
            result += temp;
        }
        return Math.sqrt(result);
    }
}
