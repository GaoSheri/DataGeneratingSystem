package com.java.datageneratingsystem.util.generate;

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
}
