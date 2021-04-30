package com.java.datageneratingsystem.service;

import com.java.datageneratingsystem.util.kmeans.*;
import org.springframework.stereotype.Service;

@Service
public class KmeansService {

    private KmeansUtil kmeansUtil;

    public void testForKmeans() {
        double[][] source = {
                {1, 4},
                {1, 3},
                {1, 2},
                {4, 1}
        };

        KmeansData data = new KmeansData(source, 4, 2);
        KmeansParam param = new KmeansParam();
        KmeansUtil.doKmeans(2, data, param);
    }
}
