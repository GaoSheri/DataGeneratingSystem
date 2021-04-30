package com.java.datageneratingsystem.util.kmeans;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class KmeansUtil {

    public KmeansUtil() {
    }

    private static void setDouble2Zero(double[][] matrix, int highDim, int lowDim) {
        for (int i = 0; i < highDim; ++i) {
            for (int j = 0; j < lowDim; ++j) {
                matrix[i][j] = 0.0D;
            }
        }

    }

    private static void copyCenters(double[][] dests, double[][] sources, int highDim, int lowDim) {
        for (int i = 0; i < highDim; ++i) {
            for (int j = 0; j < lowDim; ++j) {
                dests[i][j] = sources[i][j];
            }
        }

    }

    private static void updateCenters(int k, KmeansData data) {
        // TODO: 修改指针式编程
        double[][] centers = data.centers; // 获取当前 centers
        setDouble2Zero(centers, k, data.dim); // 将 centers 全部清零
        int[] labels = data.labels; // 获取当前数据的标记记录
        int[] centerCounts = data.centerCounts; // 获取各中心点标记次数记录

        int i;
        int j;
        for (i = 0; i < data.dim; ++i) {
            for (j = 0; j < data.length; ++j) {
                centers[labels[j]][i] += data.data[j][i]; // ？
            }
        }

        for (i = 0; i < k; ++i) {
            for (j = 0; j < data.dim; ++j) {
                centers[i][j] /= (double) centerCounts[i];
            }
        }
        System.out.println("||METHOD||updateCenters(): centers updated! Following:");
        Arrays.stream(centers).forEach(center -> {
            Arrays.stream(center).forEach(System.out::print);
            System.out.println();
        });
    }

    public static double dist(double[] pa, double[] pb, int dim) {
        double rv = 0.0D;

        for (int i = 0; i < dim; ++i) {
            double temp = pa[i] - pb[i];
            temp *= temp;
            rv += temp;
        }

        return Math.sqrt(rv);
    }

    public static KmeansResult doKmeans(int k, KmeansData data, KmeansParam param) {
        double[][] centers = new double[k][data.dim];
        data.centers = centers;
        int[] centerCounts = new int[k];
        data.centerCounts = centerCounts;
        Arrays.fill(centerCounts, 0);
        int[] labels = new int[data.length];
        data.labels = labels;
        double[][] oldCenters = new double[k][data.dim];
        int m;
        int j;
        int i;
        int attempts;
        if (param.initCenterMehtod == 1) {
            // 初次进行中心点迭代
            Random rn = new Random();
            LinkedList seeds = new LinkedList();

            int tempSeed;
            // 放入 k 个不重复的随机数
            // TODO: 初始化时就控制距离
            while (seeds.size() < k) {
                // nextInt(bound) 上限（不包含），必须为正
                tempSeed = rn.nextInt(data.length);
                if (!seeds.contains(tempSeed)) {
                    seeds.add(tempSeed);
                }
            }
            // 排序放入的随机种子
            Collections.sort(seeds);

            for (i = 0; i < k; ++i) {
                m = (Integer) seeds.remove(0);
                // 即随机取出 data 数组中的几个成员存入 centers 数组
                for (j = 0; j < data.dim; ++j) {
                    centers[i][j] = data.data[m][j];
                }
            }
        } else {
            // 非初次进行中心点迭代
            for (i = 0; i < k; ++i) {
                for (attempts = 0; attempts < data.dim; ++attempts) {
                    centers[i][attempts] = data.data[i][attempts];
                }
            }
        }

        int var10002; // 辅助其他变量自加自减的变量
        for (i = 0; i < data.length; ++i) {
            // 第 i 个数据与第 1 个中心点的距离
            double minDist = dist(data.data[i], centers[0], data.dim);
            m = 0;

            for (j = 1; j < k; ++j) {
                double tempDist = dist(data.data[i], centers[j], data.dim);
                if (tempDist < minDist) {
                    minDist = tempDist;
                    m = j;
                }
            } // 得出所有中心点中与第 i 个数据距离最近的
            // 保存了 最近距离 minDist 和 最近中心点的下标 m

            labels[i] = m; // 将最近中心点存入 labels 数组标记该数据
            var10002 = centerCounts[m]++; // 为每个中心点标记的次数做记录
        } // 迭代结束时，所有的数据都获得了对于当前中心点列表的最近中心点标记

        updateCenters(k, data); // 结合各点到中心点的距离以及各中心点的标记次数来更新中心点
        System.out.println("||METHOD||doKmeans(): after #updateCenters(), centers in this method:");
        Arrays.stream(centers).forEach(center -> {
            Arrays.stream(center).forEach(System.out::print);
            System.out.println();
        });
        copyCenters(oldCenters, centers, k, data.dim); // 把更新前的 centers 复制到 oldCenters 变量中
        m = param.attempts > 0 ? param.attempts : 4000; // m 为回收再利用
        attempts = 1;
        double criteria = param.criteria > 0.0D ? param.criteria : 1.0D;    // ?
        double criteriaBreakCondition = 0.0D;
        boolean[] flags = new boolean[k];

        while (attempts < m) {
//            int i;
            for (i = 0; i < k; ++i) {
                flags[i] = false;
            } // 初始化所有 flag 为 false

            for (i = 0; i < data.length; ++i) {
                double minDist = dist(data.data[i], centers[0], data.dim);
                int label = 0;

                int oldLabel;
                for (oldLabel = 1; oldLabel < k; ++oldLabel) { // ? 此处使用存在误导
                    double tempDist = dist(data.data[i], centers[oldLabel], data.dim);
                    if (tempDist < minDist) {
                        minDist = tempDist;
                        label = oldLabel;
                    }
                }

                if (label != labels[i]) {
                    oldLabel = labels[i];
                    labels[i] = label;
                    var10002 = centerCounts[oldLabel]--;
                    var10002 = centerCounts[label]++;
                    flags[oldLabel] = true;
                    flags[label] = true;
                }
            }

            updateCenters(k, data);
            ++attempts;
            double maxDist = 0.0D;

            for (i = 0; i < k; ++i) {
                if (flags[i]) {
                    double tempDist = dist(centers[i], oldCenters[i], data.dim);
                    if (maxDist < tempDist) {
                        maxDist = tempDist;
                    }

                    for (j = 0; j < data.dim; ++j) {
                        oldCenters[i][j] = centers[i][j];
                    }
                }
            }

            if (maxDist < criteria) {
                criteriaBreakCondition = maxDist;
                break;
            }
        }

        KmeansResult rvInfo = new KmeansResult();
        rvInfo.attempts = attempts;
        rvInfo.criteriaBreakCondition = criteriaBreakCondition;
        if (param.isDisplay) {
            System.out.println("k=" + k);
            System.out.println("attempts=" + attempts);
            System.out.println("criteriaBreakCondition=" + criteriaBreakCondition);
            System.out.println("The number of each classes are: ");

            for (i = 0; i < k; ++i) {
                System.out.print(centerCounts[i] + " ");
            }

            System.out.print("\n\n");
        }

        return rvInfo;
    }
}