package com.java.datageneratingsystem.util.kmeans;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.*;

@Component
public class MyKmeansUtil {
    public void doKmeans(int k, KmeansData data) {
        // 进行 k-means 算法的入口

        // Step 1: 随机选取 k 个中心点
        List<Integer> centersIndex = randomPickCenters(k, data);
        Double[][] sampleSet = data.sample;
        List<Double[]> centers = new ArrayList<>();
        for (Integer integer : centersIndex) {
            centers.add(sampleSet[integer]);
        }

        // Step 2: 分类
        Integer[] labels = new Integer[data.length];
        for (int i = 0; i < k; i++) {
            // 事先将中心点标记好
            labels[centersIndex.get(i)] = i;
        }
        for (int i = 0; i < sampleSet.length; i++) {
            if (centersIndex.contains(i)) continue;
            // 依次计算并标记每个样本归属于哪个中心点
            labels[i] = markSample(sampleSet[i], centers);
        }

        //Step 3: 迭代
        List<Double[]> newCenters = updateCenters(labels, sampleSet, centers);
        Integer[] newLabels = new Integer[data.length];

        while (newCenters != null) {
            // 接连两次分组情况并不一致则继续循环
            for (int i = 0; i < data.length; i++) {
                // 遍历每一个样本记录新中心点下的标记情况
                newLabels[i] = markSample(sampleSet[i], newCenters);
            }
            if (Arrays.equals(newLabels, labels)) {
                // 两次分组情况一致
                break;
            }
            centers = newCenters;
            labels = newLabels;
            newCenters = updateCenters(labels, sampleSet, centers);
        }
        Integer[] labelsCounts = separateLabels(newLabels, k);
        for (int i = 0; i < k; i++) {
            // 输出 k 个分类的信息结果
            System.out.println("\n\nCategory " + (i + 1) + " :");
            System.out.print("Center : ");
            Arrays.stream(centers.get(i))
                    .forEach(dim -> System.out.print(dim + " "));
            System.out.println("\nCounts : " + labelsCounts[i]);
        }
    }

    private Integer[] separateLabels(Integer[] newLabels, int k) {
        // 将所有标记按组别划分计数
        // 返回数组的 下标与数值 对应 组别与组别内样本计数
        Integer[] result = new Integer[k];
        Arrays.fill(result, 0);
        for (Integer label : newLabels) {
            result[label]++;
        }
        return result;
    }

    private List<Double[]> updateCenters(Integer[] labels, Double[][] sampleSet, List<Double[]> centers) {
        // 按当前的标记情况，更新中心点集合（不一定是样本点）
        // 返回更新后的 centers
        int dim = sampleSet[0].length;

        List<Double[]> result = new LinkedList<>();
        List<Set<Double[]>> groupedSamples = separateSamples(labels, sampleSet, centers.size());
        for (Set<Double[]> group : groupedSamples) {
            Double[] center = new Double[dim];
            for (int j = 0; j < dim; j++) {
                double temp = 0.0D;
                for (Double[] sample : group) {
                    temp += sample[j];
                }
                temp /= group.size();
                center[j] = temp;
            }
            result.add(center);
        }
        if (new HashSet<>(result).equals(new HashSet<>(centers))) {
            // 两次的中心点一致，即分组结果也会一致
            return null;
        }
        return result;
    }

    private List<Set<Double[]>> separateSamples(Integer[] labels, Double[][] sampleSet, int k) {
        // 将样本数据按照已给的标记划分成 k 个样本集合，并以列表的形式一并返回
        List<Set<Double[]>> result = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            result.add(new HashSet<>());
        }
        for (int i = 0; i < sampleSet.length; i++) {
            result.get(labels[i]).add(sampleSet[i]);
        }
        return result;
    }

    private Integer markSample(Double[] sample, List<Double[]> centers) {
        // 为传入的单个样本标记归属中心点
        int dim = sample.length;
        double dist = Double.MAX_VALUE;
        int index = 0, result = 0;
        for (Double[] center : centers) {
            double temp = 0.0D;
            for (int i = 0; i < dim; i++) {
                temp += Math.pow(sample[i] - center[i], 2);
            }
            temp = Math.sqrt(temp); // 得到样本与当前中心点的距离
            if (temp < dist) {
                dist = temp;
                result = index; // 获取最近距离中心点的下标
            }
            index++;
        }
        return result;
    }

    private List<Integer> randomPickCenters(int k, KmeansData data) {
        // 返回选取的中心点的下标
        // TODO: 创建规则约束随机点之间的距离下限
        int bound = data.length;
//        double range = data.maxValue - data.minValue;

        Random random = new Random();
        Set<Integer> chosenIndex = new HashSet<>();
        while (chosenIndex.size() < k) {
            chosenIndex.add(random.nextInt(bound));
            System.out.println("||randomPickCenters|| current counts : " + chosenIndex.size());
        }
        System.out.println("||randomPickCenters|| random index generating accomplished!");
        System.out.print("||randomPickCenters|| indexs : ");
        chosenIndex.forEach(i -> System.out.print(i + " "));
        System.out.println(); // 换行
        return new ArrayList<>(chosenIndex);
    }
}
