package com.java.datageneratingsystem.util.kmeans;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Component
public class MyKmeansUtil {

    KmeansResult kmeansResult;
    KmeansData data;

    private List<Double> eList;

    public KmeansResult getKmeansResult() {
        return kmeansResult;
    }

    // 对外入口
    public void doKmeans(int k, KmeansData data, int attempt) {
        this.data = data;
        Double minE = Double.MAX_VALUE;
        List<Double> list = new ArrayList<>(attempt);
        for (int i = 0; i < attempt; i++) {
            System.out.println("==== attempt " + i + " ====");
            double temp = doKmeans(k);
            list.add(temp);
            if (temp <= minE) {
                kmeansResult = data.result;
                minE = temp;
            } // 当 E 值更小的时候，将结果保存在 kmeansResult 中
        }
        eList = list;

        System.out.println(
                "============================\n" +
                "============================\n" +
                "==== Final Result Below ====\n" +
                " -- E: " + minE + " --");
        kmeansResult.print();

        // E value analysis
        Map<Double, Integer> eMap = new TreeMap<>();
//        List<Double> noRepeatE = new ArrayList<>(eList.size());
//        Integer[] eCounts = new Integer[eList.size()];
        DecimalFormat df = new DecimalFormat("0.000000");
        for (Double d : eList) {
            Double e = Double.valueOf(df.format(d));
            if (!eMap.containsKey(e)) {
                eMap.put(e, 0);
            }
            eMap.put(e, eMap.get(e) + 1);
        } // count every E values show up times
        System.out.println(
                "=====================\n" +
                "==== E statistic ====\n" +
                "=====================\n");
        System.out.println(" -- How Many Different E: " + eMap.size() + " --\n");
        eMap.forEach((key, value) -> {
            System.out.println(" - " + key +": " + value + "/" + attempt +"\n");
        });


    }

    private Double doKmeans(int k) {

        // Step 1: 随机选取 k 个中心点
        List<Integer> centersIndex = randomPickCenters(k, data);
        Double[][] sampleSet = data.sample;
        List<Double[]> centers = new ArrayList<>();
        for (Integer i : centersIndex) {
            centers.add(sampleSet[i]);
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
//        System.out.println("\n\n-- initial marking done --\n\n");

        //Step 3: 迭代
        List<Double[]> newCenters = updateCenters(labels, sampleSet, k);
//        System.out.println("\n\n-- initial updateCenters() done --\n\n");
        Integer[] newLabels = new Integer[data.length];
        int times = 1;

        while (true) {
            // 接连两次分组情况并不一致则继续循环
//            System.out.println("\n\n---- No." + times + " ----\n");
            for (int i = 0; i < data.length; i++) {
                // 遍历每一个样本记录新中心点下的标记情况
                newLabels[i] = markSample(sampleSet[i], newCenters);
            }
//            System.out.println("\n\n-- No." + times++ + " mark done --\n\n");
            if (checkLabels(newLabels, labels)) {
                System.out.println("---- No." + times + " ----\n");
                System.out.println(" -- labels grouping same twice --\n\n");
                // 两次分组情况一致
                break;
            }
            Collections.copy(centers, newCenters);
            labels = Arrays.copyOf(newLabels, newLabels.length);
            newCenters = updateCenters(labels, sampleSet, k);
            times++;
        }

        KmeansResult result = new KmeansResult(k, data.length);
        result.labels = newLabels;
        result.finalCenters = newCenters;
        result.times = times;
        result.labelsCount = separateLabels(newLabels, k);
        data.result = result;
//        result.print();

        // 计算 E
        Double e = 0.0D;
        for (Double[] doubles : sampleSet) {
            for (int j = 0; j < k; j++) {
                e += distance(doubles, newCenters.get(j), data.dim);
            }
        }
        System.out.println("-- E: " + e + " --\n\n");
        return e;
    }

    private Double distance(Double[] pa, Double[] pb, int dim) {
        // 欧氏距离
        double temp = 0.0D;
        for (int i = 0; i < dim; i++) {
            temp += Math.pow(pa[i] - pb[i], 2);
        }
        return Math.sqrt(temp);
    }

    private Boolean checkLabels(Integer[] newLabels, Integer[] oldLabels) {
        // 顺序与值皆一致则返回 true
        for (int i = 0; i < newLabels.length; i++) {
            if (!newLabels[i].equals(oldLabels[i])) return false;
        }
        return true;
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

    private List<Double[]> updateCenters(Integer[] labels, Double[][] sampleSet, int k) {
        // 按当前的标记情况，更新中心点集合（不一定是样本点）
        // 返回更新后的 centers
        int dim = sampleSet[0].length;

        List<Double[]> result = new LinkedList<>();
        List<Set<Double[]>> groupedSamples = separateSamples(labels, sampleSet, k);
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
//        System.out.println("||updateCenters|| new centers : ");
//        result.forEach(center -> {
//            Arrays.stream(center).forEach(d -> System.out.print(d + " "));
//            System.out.println();
//        });
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
        System.out.println("||randomPickCenters|| centers : ");
        chosenIndex.forEach(i -> {
            System.out.print("index " + i + " : ");
            Arrays.stream(data.sample[i]).forEach(d -> System.out.print(d + " "));
            System.out.println();
        });
        System.out.println(); // 换行
        return new ArrayList<>(chosenIndex);
    }

    public void printGroupedSample(Integer[] labels, Double[][] sampleSet, int k) {
        List<Set<Double[]>> groups = separateSamples(labels, sampleSet, k);
        System.out.println(
                "=============================\n" +
                        "==== sample groups below ====\n" +
                        "=============================\n");
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("\n\n -- Group " + (i + 1) + " --\n" +
                    " -- Counts: " + groups.get(i).size() + " --\n\n");
            groups.get(i).forEach(sample -> {
                Arrays.stream(sample).forEach(d -> {
                    System.out.print(d + " ");
                });
                System.out.println();
            });
        }
        System.out.println("\n============= DONE =============");
    }
}
