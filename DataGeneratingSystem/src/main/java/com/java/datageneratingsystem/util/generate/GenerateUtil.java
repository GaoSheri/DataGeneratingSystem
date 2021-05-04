package com.java.datageneratingsystem.util.generate;

import com.java.datageneratingsystem.util.kmeans.MyKmeansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class GenerateUtil {
    /**
     * Step 1: 随机生成 k 个中心点
     *  - 限制距离下限
     *
     * Step 2: 围绕 k 个中心点来随机生成数据
     *  = 携带标签
     *  - 算法约束（详待知网论文调研完毕）
     *
     * TODO: Step 2.5: 是否投入干扰数据？
     *  - 【如若投入，需要在k-means包中添加清洗数据的环节】
     *
     * Step 3: 将生成数据投入K-means算法得到分类结果，与标签信息核对
     *  - 接受一定偏差，偏差范围内则修改数据集标签而非数据值
     *
     * Step 4: 检验 E 值分布情况，来区分训练数据结果的等级
     *
     */

    @Autowired
    MyKmeansUtil kmeansUtil;

    Set<Double[]> AllSamples;
    AnswerUtil answerUtil;
    DataShape dataShape;
    int k;

    public void generate(int k, int length, DataShape ds) {
        this.k = k;
        dataShape = ds;
        List<Double[]> initCenters = generateCenters(); // won't be the final data samples
        /*
            These centers are meant to represent the area of categories,
            as the initial center flag for generating the real sample set.
         */

        // generate real samples surrounding the initial centers
        generateSamples();


    }


    private List<Double[]> generateCenters() {
        List<Double[]> initCenters = new ArrayList<>();
        while (initCenters.size() < k) {
            Double[] center = generateCenter();
            if (checkValidCenter(center, initCenters)){
                initCenters.add(center);
            }
        }
        return initCenters;
    }

    private Double[] generateCenter() {
        Random rd = new Random();
        int dim = dataShape.dim;
        Double[] center = new Double[dim];
        double distToEdge = dataShape.getCrossDist() / (2.0 * k);

        for (int i = 0; i < dim; i++) {
            Double[] range = dataShape.getRangeOfDim(i);
            double value = Math.random() * (range[1] - range[0] - 2.0 * distToEdge) + range[0] + distToEdge;
            center[i] = value;
        }
        return center;
    }

    private boolean checkValidCenter(Double[] preCenter, List<Double[]> initCenters) {
        // true 为可行 / false 为不可行
        if (initCenters.isEmpty())
            return true;
        if (initCenters.contains(preCenter))
            return false;

        // check distance between centers
        double distBtwCenters = dataShape.getCrossDist() / (k + 1.0);
        for (Double[] center : initCenters) {
            if (kmeansUtil.distance(center, preCenter, dataShape.dim) < distBtwCenters)
                return false;
        }
        return true;
    }


    private void generateSamples() {
        //
        generateSample();
    }

    private Double[] generateSample() {
        return null;
    }



}
