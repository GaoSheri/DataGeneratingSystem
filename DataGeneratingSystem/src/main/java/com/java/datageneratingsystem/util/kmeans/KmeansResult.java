package com.java.datageneratingsystem.util.kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KmeansResult {

    public int k;

    public List<Double[]> finalCenters;
    public Integer[] labels;
    public Integer[] labelsCount;
    public Integer times;

    public KmeansResult(int k, int length) {
        this.k = k;
        finalCenters = new ArrayList<>(k);
        labels = new Integer[length];
        labelsCount = new Integer[k];
    }

    public void print() {
        // print...
        System.out.println("\n\n==== RESULT ====");
        for (int i = 0; i < k; i++) {
            // 输出 k 个分类的信息结果
            System.out.println("\n\nCategory " + (i + 1) + " :");
            System.out.print("Center : ");
            Arrays.stream(finalCenters.get(i))
                    .forEach(dim -> System.out.print(dim + " "));
            System.out.println("\nCounts : " + labelsCount[i]);
        }
        System.out.println("\n\n================\n");
    }
}
