package com.java.datageneratingsystem.util.generate;

import com.java.datageneratingsystem.util.kmeans.MyKmeansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GenerateUtil {
    /**
     * Step 1: 随机生成 k 个中心点
     * - 限制距离下限
     * <p>
     * Step 2: 围绕 k 个中心点来随机生成数据
     * = 携带标签
     * - 算法约束（详待知网论文调研完毕）
     * <p>
     * TODO: Step 2.5: 是否投入干扰数据？
     * - 【如若投入，需要在k-means包中添加清洗数据的环节】
     * <p>
     * Step 3: 将生成数据投入K-means算法得到分类结果，与标签信息核对
     * - 接受一定偏差，偏差范围内则修改数据集标签而非数据值
     * <p>
     * Step 4: 检验 E 值分布情况，来区分训练数据结果的等级
     */

    @Autowired
    MyKmeansUtil kmeansUtil;
    @Autowired
    AnswerUtil answerUtil;

    DataShape dataShape;
    int k;
    int length;

    Set<Double[]> allSamples;
    HashMap<Double[], Set<Double[]>> sampleMap;

    public void generate(int k, int length, DataShape ds) {
        this.k = k;
        this.length = length;
        dataShape = ds;
        allSamples = new HashSet<>(length);
        sampleMap = new HashMap<>(k);

        List<Double[]> initCenters = generateCenters(); // won't be the final data samples
        /*
            These centers are meant to represent the area of categories,
            as the initial center flag for generating the real sample set.
         */
        for (Double[] center : initCenters) {
            sampleMap.put(center, new HashSet<>());
        }

        // generate real samples surrounding the initial centers
        generateSamples(initCenters);
        printAllSamples();
//        return allSamples;
    }

    public void resolveTheSamples() {
        System.out.println("resolveTheSamples|| going to resolve the samples...");
        answerUtil.resolve(k, allSamples);
    }

    public AnswerUtil getAnswer() {
        return answerUtil;
    }

    /**
     * CENTERS
     */

    private List<Double[]> generateCenters() {
        List<Double[]> initCenters = new ArrayList<>();
        int failCount = 0;
        while (initCenters.size() < k) {
            if (failCount > 10) {
                // continuously fail reason could be the centers are too separated that no room for cross/k+1
                initCenters.removeAll(initCenters);
                failCount = 0;
            }
            Double[] center = generateCenter();
            if (checkValidCenter(center, initCenters)) {
                initCenters.add(center);
                System.out.print("generateCenters|| generate initial center: ");
                Arrays.stream(center).forEach(d -> System.out.print(d + " "));
                System.out.println();
            } else {
                failCount++;
            }
        }
        return initCenters;
    }

    private Double[] generateCenter() {
        Random rd = new Random();
        int dim = dataShape.dim;
        Double[] center = new Double[dim];
        double distToEdge = dataShape.getCrossDist() / (2.5 * k);

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

        double distBtwCenters = dataShape.getCrossDist() / (1.7 * k);

        for (Double[] center : initCenters) {
            // check distance between centers
            if (kmeansUtil.distance(center, preCenter, dataShape.dim) < distBtwCenters) {
                System.out.println("checkValidCenter|| drop a center because of the distance between it and others.");
                return false;
            }
        }
        return true;
    }

//    private boolean checkEdge(Double[] range, Double value, Double minDist) {
//        // true means safe, false means less than minimal distance
//        for (int i = 0; i < 2; i++) {
//            if (Math.abs(value - range[i]) < minDist) {
//                return false;
//            }
//        }
//        return true;
//    }

    private Double[] getRealRangeOfDim(Double value, Double[] range, Double radius) {
        Double[] result = {value - radius, value + radius};
        for (int i = 0; i < 2; i++) {
            if (Math.abs(value - range[i]) < radius) {
                result[i] = range[i];
            }
        }
        return result;
    }

    /**
     * SAMPLES
     */

    private void generateSamples(List<Double[]> initCenters) {
        // 生成主要围绕中心点的样本

        // 为每个中心点计算R
        List<Double> Rvalues = calculateR(initCenters);

        for (int i = 0; i < initCenters.size(); i++) {
            System.out.println("generateSamples|| generating samples for center " + i);
            generateSamplesSurroundCenter(initCenters.get(i), Rvalues.get(i), length / k - (length / 100));
        } // 至此应当剩下 3% * length (length > 100 的时候) 个样本待生成
        // 小于 100 的数据量时不随机分布

        System.out.println("generateSamples|| current sample set size: " + allSamples.size());
        if (allSamples.size() < length) {
            System.out.println("generateSamples|| ready to generate the rest of random samples...");
            // 生成随机分散的数据样本
            while (allSamples.size() < length) {
                allSamples.add(generateRandomSample(initCenters));
            }
        }
        System.out.println("generateSamples|| generate all " + length + " samples DONE!");
    }

    private List<Double> calculateR(List<Double[]> initCenters) {
        // R: the MINIMAL half distance between different centers
        int dim = dataShape.dim;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < initCenters.size(); i++) {
            Double[] center = initCenters.get(i);
            double minDist = initCenters.stream()
                    .mapToDouble(item -> kmeansUtil.distance(center, item, dim))
                    .filter(value -> value != 0.0) // 与其本身的距离将为 0
                    .min().getAsDouble();
            result.add(minDist / 2.0);
        }
        return result;
    }

    private void generateSamplesSurroundCenter(Double[] center, Double R, int count) {
        // 以下百分比基于传入数据，传入数据为 n / (k + 1)
        // 例：数据总量 400, k=3, 则传入一个中心点分配数据名额为 100

        Set<Double[]> sampleSetForCurCenter = sampleMap.get(center);
        // circle 1: 1/5R, 30%
        while (sampleSetForCurCenter.size() < Math.ceil(count * 0.3)) {
            Double[] sample = generateSampleWithinCircle(center, R / 5.0);
            sampleSetForCurCenter.add(sample);
        }

        // circle 2: 1/2R, 20%
        while (sampleSetForCurCenter.size() < Math.ceil(count * 0.5)) {
            Double[] sample = generateSampleWithinCircle(center, R / 2.0);
            sampleSetForCurCenter.add(sample);
        }

        // circle 3: 2/3R, 15%
        while (sampleSetForCurCenter.size() < Math.ceil(count * 0.65)) {
            Double[] sample = generateSampleWithinCircle(center, 2 * R / 3.0);
            sampleSetForCurCenter.add(sample);
        }

        // circle 4: R, 35%
        while (sampleSetForCurCenter.size() < count) {
            Double[] sample = generateSampleWithinCircle(center, R);
            sampleSetForCurCenter.add(sample);
        }

        allSamples.addAll(sampleSetForCurCenter);
        sampleMap.get(center).addAll(sampleSetForCurCenter);
    }


    private Double[] generateSampleWithinCircle(Double[] center, Double radius) {
        // generate sample that inside the given circle.
        int dim = dataShape.dim;
        Double[] sample = new Double[dim];
        for (int i = 0; i < dim; i++) {
            Double[] range = dataShape.getRangeOfDim(i);
            Double[] valueRange = getRealRangeOfDim(center[i], range, radius);
            double value = Math.random() * (valueRange[1] - valueRange[0]) + valueRange[0];
            sample[i] = value;
        }
        if (kmeansUtil.distance(sample, center, dim) < radius) {
            return sample;
        } else { // 保证只返回圆/球/...内的点
            return generateSampleWithinCircle(center, radius);
        }
    }


    private Double[] generateRandomSample(List<Double[]> initCenters) {
        // randomly generate sample inside the range
        // initCenters is for marking
        int dim = dataShape.dim;
        Double[] sample = new Double[dim];
        for (int i = 0; i < dim; i++) {
            Double[] range = dataShape.getRangeOfDim(i);
            sample[i] = Math.random() * (range[1] - range[0]) + range[0];
        }
        // deliver to the set
        int index = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            double temp = kmeansUtil.distance(sample, initCenters.get(i), dim);
            if (temp < min) {
                min = temp;
                index = i;
            }
        }
        sampleMap.get(initCenters.get(index)).add(sample);

        return sample;
    }

    public void printAllSamples() {
        allSamples.forEach(sample -> {
            Arrays.stream(sample).forEach(d -> System.out.print(d + " "));
            System.out.println();
        });
    }

}
