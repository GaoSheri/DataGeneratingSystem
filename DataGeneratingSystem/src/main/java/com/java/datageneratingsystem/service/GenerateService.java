package com.java.datageneratingsystem.service;

import com.java.datageneratingsystem.util.generate.DataShape;
import com.java.datageneratingsystem.util.generate.GenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateService {

    @Autowired
    GenerateUtil generateUtil;

    public void generateTest() {
        int dim = 3;
        int length = 40;
        int k = 3;

        DataShape dataShape = new DataShape(dim);
        Double[][] ranges = new Double[][]{
                {2.0, 100.0},   //dim 1
                {0.0, 50.0},    //dim 2
                {50.0, 70.0}    //dim 3
        };
        try {
            for (int i = 0; i < dim; i++) {
                dataShape.setUpRange(i, ranges[i][0], ranges[i][1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        generateUtil.generate(k, length, dataShape);
        generateUtil.resolveTheSamples();
    }
}
