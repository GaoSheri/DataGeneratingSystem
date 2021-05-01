package com.java.datageneratingsystem.service;

import com.java.datageneratingsystem.util.kmeans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KmeansService {

//    private KmeansUtil kmeansUtil;
    @Autowired
    private MyKmeansUtil myKmeansUtil;

    public void testForKmeans() {
        Double[][] source = {
                {1.0, 4.0},
                {1.0, 3.0},
                {1.0, 2.0},
                {4.0, 1.0}
        };

        KmeansData data = new KmeansData(source, 4, 2);
        myKmeansUtil.doKmeans(2, data);

//        KmeansParam param = new KmeansParam();
//        KmeansUtil.doKmeans(2, data, param);
    }
}
