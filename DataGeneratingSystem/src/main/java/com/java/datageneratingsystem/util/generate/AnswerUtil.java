package com.java.datageneratingsystem.util.generate;

import com.java.datageneratingsystem.util.kmeans.KmeansData;
import com.java.datageneratingsystem.util.kmeans.KmeansResult;
import com.java.datageneratingsystem.util.kmeans.MyKmeansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class AnswerUtil {
    Double bestE;
    Integer[] labels;
    Integer[] labelsCount;
    List<Double[]> centers;

    @Autowired
    MyKmeansUtil kmeansUtil;

    public void resolve(int k, Set<Double[]> generatedData) {
        AnswerUtil answer = new AnswerUtil();

        int length = generatedData.size();
        Double[][] samples = new Double[length][];
        List<Double[]> list = new ArrayList<>(generatedData);
        for (int i = 0; i < length; i++) {
            samples[i] = list.get(i);
        }
        int dim = samples[0].length;

        KmeansData data = new KmeansData(samples, length, dim);
        kmeansUtil.doKmeans(k, data, length * 10);

        answer.bestE = kmeansUtil.getBestE();

        KmeansResult kmeansResult = kmeansUtil.getKmeansResult();
        kmeansResult.print();
        answer.labels = kmeansResult.labels;
        answer.labelsCount = kmeansResult.labelsCount;
        answer.centers = kmeansResult.finalCenters;
    }
}
