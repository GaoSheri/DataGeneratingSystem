package com.java.datageneratingsystem;

import com.java.datageneratingsystem.service.KmeansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {

    @Autowired
    private KmeansService kmeansService;

    @Test
    public void kmeansTest() {
        kmeansService.testForKmeans();
    }
}