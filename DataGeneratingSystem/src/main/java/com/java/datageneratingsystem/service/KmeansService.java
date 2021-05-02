package com.java.datageneratingsystem.service;

import com.java.datageneratingsystem.util.kmeans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KmeansService {

    @Autowired
    private MyKmeansUtil myKmeansUtil;

    public void testForKmeans() {
//        Double[][] source = {
//                {1.0, 1.0},
//                {1.0, 2.0},
//                {2.0, 1.0},
//                {2.0, 2.0},
//                {3.0, 3.0},
//                {3.0, 4.0},
//                {4.0, 3.0},
//                {4.0, 4.0},
//        };
//
//        KmeansData data = new KmeansData(source, 8, 2);
//        myKmeansUtil.doKmeans(2, data, 4);

        // Glass Test
        String glassData =
                "1,1.52101,13.64,4.49,1.10,71.78,0.06,8.75,0.00,0.00,1\n" +
                        "2,1.51761,13.89,3.60,1.36,72.73,0.48,7.83,0.00,0.00,1\n" +
                        "3,1.51618,13.53,3.55,1.54,72.99,0.39,7.78,0.00,0.00,1\n" +
                        "4,1.51766,13.21,3.69,1.29,72.61,0.57,8.22,0.00,0.00,1\n" +
                        "5,1.51742,13.27,3.62,1.24,73.08,0.55,8.07,0.00,0.00,1\n" +
                        "6,1.51596,12.79,3.61,1.62,72.97,0.64,8.07,0.00,0.26,1\n" +
                        "7,1.51743,13.30,3.60,1.14,73.09,0.58,8.17,0.00,0.00,1\n" +
                        "8,1.51756,13.15,3.61,1.05,73.24,0.57,8.24,0.00,0.00,1\n" +
                        "9,1.51918,14.04,3.58,1.37,72.08,0.56,8.30,0.00,0.00,1\n" +
                        "10,1.51755,13.00,3.60,1.36,72.99,0.57,8.40,0.00,0.11,1\n" +
                        "11,1.51571,12.72,3.46,1.56,73.20,0.67,8.09,0.00,0.24,1\n" +
                        "12,1.51763,12.80,3.66,1.27,73.01,0.60,8.56,0.00,0.00,1\n" +
                        "13,1.51589,12.88,3.43,1.40,73.28,0.69,8.05,0.00,0.24,1\n" +
                        "14,1.51748,12.86,3.56,1.27,73.21,0.54,8.38,0.00,0.17,1\n" +
                        "15,1.51763,12.61,3.59,1.31,73.29,0.58,8.50,0.00,0.00,1\n" +
                        "16,1.51761,12.81,3.54,1.23,73.24,0.58,8.39,0.00,0.00,1\n" +
                        "17,1.51784,12.68,3.67,1.16,73.11,0.61,8.70,0.00,0.00,1\n" +
                        "18,1.52196,14.36,3.85,0.89,71.36,0.15,9.15,0.00,0.00,1\n" +
                        "19,1.51911,13.90,3.73,1.18,72.12,0.06,8.89,0.00,0.00,1\n" +
                        "20,1.51735,13.02,3.54,1.69,72.73,0.54,8.44,0.00,0.07,1\n" +
                        "21,1.51750,12.82,3.55,1.49,72.75,0.54,8.52,0.00,0.19,1\n" +
                        "22,1.51966,14.77,3.75,0.29,72.02,0.03,9.00,0.00,0.00,1\n" +
                        "23,1.51736,12.78,3.62,1.29,72.79,0.59,8.70,0.00,0.00,1\n" +
                        "24,1.51751,12.81,3.57,1.35,73.02,0.62,8.59,0.00,0.00,1\n" +
                        "25,1.51720,13.38,3.50,1.15,72.85,0.50,8.43,0.00,0.00,1\n" +
                        "26,1.51764,12.98,3.54,1.21,73.00,0.65,8.53,0.00,0.00,1\n" +
                        "27,1.51793,13.21,3.48,1.41,72.64,0.59,8.43,0.00,0.00,1\n" +
                        "28,1.51721,12.87,3.48,1.33,73.04,0.56,8.43,0.00,0.00,1\n" +
                        "29,1.51768,12.56,3.52,1.43,73.15,0.57,8.54,0.00,0.00,1\n" +
                        "30,1.51784,13.08,3.49,1.28,72.86,0.60,8.49,0.00,0.00,1\n" +
                        "31,1.51768,12.65,3.56,1.30,73.08,0.61,8.69,0.00,0.14,1\n" +
                        "32,1.51747,12.84,3.50,1.14,73.27,0.56,8.55,0.00,0.00,1\n" +
                        "33,1.51775,12.85,3.48,1.23,72.97,0.61,8.56,0.09,0.22,1\n" +
                        "34,1.51753,12.57,3.47,1.38,73.39,0.60,8.55,0.00,0.06,1\n" +
                        "35,1.51783,12.69,3.54,1.34,72.95,0.57,8.75,0.00,0.00,1\n" +
                        "36,1.51567,13.29,3.45,1.21,72.74,0.56,8.57,0.00,0.00,1\n" +
                        "37,1.51909,13.89,3.53,1.32,71.81,0.51,8.78,0.11,0.00,1\n" +
                        "38,1.51797,12.74,3.48,1.35,72.96,0.64,8.68,0.00,0.00,1\n" +
                        "39,1.52213,14.21,3.82,0.47,71.77,0.11,9.57,0.00,0.00,1\n" +
                        "40,1.52213,14.21,3.82,0.47,71.77,0.11,9.57,0.00,0.00,1\n" +
                        "41,1.51793,12.79,3.50,1.12,73.03,0.64,8.77,0.00,0.00,1\n" +
                        "42,1.51755,12.71,3.42,1.20,73.20,0.59,8.64,0.00,0.00,1\n" +
                        "43,1.51779,13.21,3.39,1.33,72.76,0.59,8.59,0.00,0.00,1\n" +
                        "44,1.52210,13.73,3.84,0.72,71.76,0.17,9.74,0.00,0.00,1\n" +
                        "45,1.51786,12.73,3.43,1.19,72.95,0.62,8.76,0.00,0.30,1\n" +
                        "46,1.51900,13.49,3.48,1.35,71.95,0.55,9.00,0.00,0.00,1\n" +
                        "47,1.51869,13.19,3.37,1.18,72.72,0.57,8.83,0.00,0.16,1\n" +
                        "48,1.52667,13.99,3.70,0.71,71.57,0.02,9.82,0.00,0.10,1\n" +
                        "49,1.52223,13.21,3.77,0.79,71.99,0.13,10.02,0.00,0.00,1\n" +
                        "50,1.51898,13.58,3.35,1.23,72.08,0.59,8.91,0.00,0.00,1\n" +
                        "51,1.52320,13.72,3.72,0.51,71.75,0.09,10.06,0.00,0.16,1\n" +
                        "52,1.51926,13.20,3.33,1.28,72.36,0.60,9.14,0.00,0.11,1\n" +
                        "53,1.51808,13.43,2.87,1.19,72.84,0.55,9.03,0.00,0.00,1\n" +
                        "54,1.51837,13.14,2.84,1.28,72.85,0.55,9.07,0.00,0.00,1\n" +
                        "55,1.51778,13.21,2.81,1.29,72.98,0.51,9.02,0.00,0.09,1\n" +
                        "56,1.51769,12.45,2.71,1.29,73.70,0.56,9.06,0.00,0.24,1\n" +
                        "57,1.51215,12.99,3.47,1.12,72.98,0.62,8.35,0.00,0.31,1\n" +
                        "58,1.51824,12.87,3.48,1.29,72.95,0.60,8.43,0.00,0.00,1\n" +
                        "59,1.51754,13.48,3.74,1.17,72.99,0.59,8.03,0.00,0.00,1\n" +
                        "60,1.51754,13.39,3.66,1.19,72.79,0.57,8.27,0.00,0.11,1\n" +
                        "61,1.51905,13.60,3.62,1.11,72.64,0.14,8.76,0.00,0.00,1\n" +
                        "62,1.51977,13.81,3.58,1.32,71.72,0.12,8.67,0.69,0.00,1\n" +
                        "63,1.52172,13.51,3.86,0.88,71.79,0.23,9.54,0.00,0.11,1\n" +
                        "64,1.52227,14.17,3.81,0.78,71.35,0.00,9.69,0.00,0.00,1\n" +
                        "65,1.52172,13.48,3.74,0.90,72.01,0.18,9.61,0.00,0.07,1\n" +
                        "66,1.52099,13.69,3.59,1.12,71.96,0.09,9.40,0.00,0.00,1\n" +
                        "67,1.52152,13.05,3.65,0.87,72.22,0.19,9.85,0.00,0.17,1\n" +
                        "68,1.52152,13.05,3.65,0.87,72.32,0.19,9.85,0.00,0.17,1\n" +
                        "69,1.52152,13.12,3.58,0.90,72.20,0.23,9.82,0.00,0.16,1\n" +
                        "70,1.52300,13.31,3.58,0.82,71.99,0.12,10.17,0.00,0.03,1\n" +
                        "71,1.51574,14.86,3.67,1.74,71.87,0.16,7.36,0.00,0.12,2\n" +
                        "72,1.51848,13.64,3.87,1.27,71.96,0.54,8.32,0.00,0.32,2\n" +
                        "73,1.51593,13.09,3.59,1.52,73.10,0.67,7.83,0.00,0.00,2\n" +
                        "74,1.51631,13.34,3.57,1.57,72.87,0.61,7.89,0.00,0.00,2\n" +
                        "75,1.51596,13.02,3.56,1.54,73.11,0.72,7.90,0.00,0.00,2\n" +
                        "76,1.51590,13.02,3.58,1.51,73.12,0.69,7.96,0.00,0.00,2\n" +
                        "77,1.51645,13.44,3.61,1.54,72.39,0.66,8.03,0.00,0.00,2\n" +
                        "78,1.51627,13.00,3.58,1.54,72.83,0.61,8.04,0.00,0.00,2\n" +
                        "79,1.51613,13.92,3.52,1.25,72.88,0.37,7.94,0.00,0.14,2\n" +
                        "80,1.51590,12.82,3.52,1.90,72.86,0.69,7.97,0.00,0.00,2\n" +
                        "81,1.51592,12.86,3.52,2.12,72.66,0.69,7.97,0.00,0.00,2\n" +
                        "82,1.51593,13.25,3.45,1.43,73.17,0.61,7.86,0.00,0.00,2\n" +
                        "83,1.51646,13.41,3.55,1.25,72.81,0.68,8.10,0.00,0.00,2\n" +
                        "84,1.51594,13.09,3.52,1.55,72.87,0.68,8.05,0.00,0.09,2\n" +
                        "85,1.51409,14.25,3.09,2.08,72.28,1.10,7.08,0.00,0.00,2\n" +
                        "86,1.51625,13.36,3.58,1.49,72.72,0.45,8.21,0.00,0.00,2\n" +
                        "87,1.51569,13.24,3.49,1.47,73.25,0.38,8.03,0.00,0.00,2\n" +
                        "88,1.51645,13.40,3.49,1.52,72.65,0.67,8.08,0.00,0.10,2\n" +
                        "89,1.51618,13.01,3.50,1.48,72.89,0.60,8.12,0.00,0.00,2\n" +
                        "90,1.51640,12.55,3.48,1.87,73.23,0.63,8.08,0.00,0.09,2\n" +
                        "91,1.51841,12.93,3.74,1.11,72.28,0.64,8.96,0.00,0.22,2\n" +
                        "92,1.51605,12.90,3.44,1.45,73.06,0.44,8.27,0.00,0.00,2\n" +
                        "93,1.51588,13.12,3.41,1.58,73.26,0.07,8.39,0.00,0.19,2\n" +
                        "94,1.51590,13.24,3.34,1.47,73.10,0.39,8.22,0.00,0.00,2\n" +
                        "95,1.51629,12.71,3.33,1.49,73.28,0.67,8.24,0.00,0.00,2\n" +
                        "96,1.51860,13.36,3.43,1.43,72.26,0.51,8.60,0.00,0.00,2\n" +
                        "97,1.51841,13.02,3.62,1.06,72.34,0.64,9.13,0.00,0.15,2\n" +
                        "98,1.51743,12.20,3.25,1.16,73.55,0.62,8.90,0.00,0.24,2\n" +
                        "99,1.51689,12.67,2.88,1.71,73.21,0.73,8.54,0.00,0.00,2\n" +
                        "100,1.51811,12.96,2.96,1.43,72.92,0.60,8.79,0.14,0.00,2\n" +
                        "101,1.51655,12.75,2.85,1.44,73.27,0.57,8.79,0.11,0.22,2\n" +
                        "102,1.51730,12.35,2.72,1.63,72.87,0.70,9.23,0.00,0.00,2\n" +
                        "103,1.51820,12.62,2.76,0.83,73.81,0.35,9.42,0.00,0.20,2\n" +
                        "104,1.52725,13.80,3.15,0.66,70.57,0.08,11.64,0.00,0.00,2\n" +
                        "105,1.52410,13.83,2.90,1.17,71.15,0.08,10.79,0.00,0.00,2\n" +
                        "106,1.52475,11.45,0.00,1.88,72.19,0.81,13.24,0.00,0.34,2\n" +
                        "107,1.53125,10.73,0.00,2.10,69.81,0.58,13.30,3.15,0.28,2\n" +
                        "108,1.53393,12.30,0.00,1.00,70.16,0.12,16.19,0.00,0.24,2\n" +
                        "109,1.52222,14.43,0.00,1.00,72.67,0.10,11.52,0.00,0.08,2\n" +
                        "110,1.51818,13.72,0.00,0.56,74.45,0.00,10.99,0.00,0.00,2\n" +
                        "111,1.52664,11.23,0.00,0.77,73.21,0.00,14.68,0.00,0.00,2\n" +
                        "112,1.52739,11.02,0.00,0.75,73.08,0.00,14.96,0.00,0.00,2\n" +
                        "113,1.52777,12.64,0.00,0.67,72.02,0.06,14.40,0.00,0.00,2\n" +
                        "114,1.51892,13.46,3.83,1.26,72.55,0.57,8.21,0.00,0.14,2\n" +
                        "115,1.51847,13.10,3.97,1.19,72.44,0.60,8.43,0.00,0.00,2\n" +
                        "116,1.51846,13.41,3.89,1.33,72.38,0.51,8.28,0.00,0.00,2\n" +
                        "117,1.51829,13.24,3.90,1.41,72.33,0.55,8.31,0.00,0.10,2\n" +
                        "118,1.51708,13.72,3.68,1.81,72.06,0.64,7.88,0.00,0.00,2\n" +
                        "119,1.51673,13.30,3.64,1.53,72.53,0.65,8.03,0.00,0.29,2\n" +
                        "120,1.51652,13.56,3.57,1.47,72.45,0.64,7.96,0.00,0.00,2\n" +
                        "121,1.51844,13.25,3.76,1.32,72.40,0.58,8.42,0.00,0.00,2\n" +
                        "122,1.51663,12.93,3.54,1.62,72.96,0.64,8.03,0.00,0.21,2\n" +
                        "123,1.51687,13.23,3.54,1.48,72.84,0.56,8.10,0.00,0.00,2\n" +
                        "124,1.51707,13.48,3.48,1.71,72.52,0.62,7.99,0.00,0.00,2\n" +
                        "125,1.52177,13.20,3.68,1.15,72.75,0.54,8.52,0.00,0.00,2\n" +
                        "126,1.51872,12.93,3.66,1.56,72.51,0.58,8.55,0.00,0.12,2\n" +
                        "127,1.51667,12.94,3.61,1.26,72.75,0.56,8.60,0.00,0.00,2\n" +
                        "128,1.52081,13.78,2.28,1.43,71.99,0.49,9.85,0.00,0.17,2\n" +
                        "129,1.52068,13.55,2.09,1.67,72.18,0.53,9.57,0.27,0.17,2\n" +
                        "130,1.52020,13.98,1.35,1.63,71.76,0.39,10.56,0.00,0.18,2\n" +
                        "131,1.52177,13.75,1.01,1.36,72.19,0.33,11.14,0.00,0.00,2\n" +
                        "132,1.52614,13.70,0.00,1.36,71.24,0.19,13.44,0.00,0.10,2\n" +
                        "133,1.51813,13.43,3.98,1.18,72.49,0.58,8.15,0.00,0.00,2\n" +
                        "134,1.51800,13.71,3.93,1.54,71.81,0.54,8.21,0.00,0.15,2\n" +
                        "135,1.51811,13.33,3.85,1.25,72.78,0.52,8.12,0.00,0.00,2\n" +
                        "136,1.51789,13.19,3.90,1.30,72.33,0.55,8.44,0.00,0.28,2\n" +
                        "137,1.51806,13.00,3.80,1.08,73.07,0.56,8.38,0.00,0.12,2\n" +
                        "138,1.51711,12.89,3.62,1.57,72.96,0.61,8.11,0.00,0.00,2\n" +
                        "139,1.51674,12.79,3.52,1.54,73.36,0.66,7.90,0.00,0.00,2\n" +
                        "140,1.51674,12.87,3.56,1.64,73.14,0.65,7.99,0.00,0.00,2\n" +
                        "141,1.51690,13.33,3.54,1.61,72.54,0.68,8.11,0.00,0.00,2\n" +
                        "142,1.51851,13.20,3.63,1.07,72.83,0.57,8.41,0.09,0.17,2\n" +
                        "143,1.51662,12.85,3.51,1.44,73.01,0.68,8.23,0.06,0.25,2\n" +
                        "144,1.51709,13.00,3.47,1.79,72.72,0.66,8.18,0.00,0.00,2\n" +
                        "145,1.51660,12.99,3.18,1.23,72.97,0.58,8.81,0.00,0.24,2\n" +
                        "146,1.51839,12.85,3.67,1.24,72.57,0.62,8.68,0.00,0.35,2\n" +
                        "147,1.51769,13.65,3.66,1.11,72.77,0.11,8.60,0.00,0.00,3\n" +
                        "148,1.51610,13.33,3.53,1.34,72.67,0.56,8.33,0.00,0.00,3\n" +
                        "149,1.51670,13.24,3.57,1.38,72.70,0.56,8.44,0.00,0.10,3\n" +
                        "150,1.51643,12.16,3.52,1.35,72.89,0.57,8.53,0.00,0.00,3\n" +
                        "151,1.51665,13.14,3.45,1.76,72.48,0.60,8.38,0.00,0.17,3\n" +
                        "152,1.52127,14.32,3.90,0.83,71.50,0.00,9.49,0.00,0.00,3\n" +
                        "153,1.51779,13.64,3.65,0.65,73.00,0.06,8.93,0.00,0.00,3\n" +
                        "154,1.51610,13.42,3.40,1.22,72.69,0.59,8.32,0.00,0.00,3\n" +
                        "155,1.51694,12.86,3.58,1.31,72.61,0.61,8.79,0.00,0.00,3\n" +
                        "156,1.51646,13.04,3.40,1.26,73.01,0.52,8.58,0.00,0.00,3\n" +
                        "157,1.51655,13.41,3.39,1.28,72.64,0.52,8.65,0.00,0.00,3\n" +
                        "158,1.52121,14.03,3.76,0.58,71.79,0.11,9.65,0.00,0.00,3\n" +
                        "159,1.51776,13.53,3.41,1.52,72.04,0.58,8.79,0.00,0.00,3\n" +
                        "160,1.51796,13.50,3.36,1.63,71.94,0.57,8.81,0.00,0.09,3\n" +
                        "161,1.51832,13.33,3.34,1.54,72.14,0.56,8.99,0.00,0.00,3\n" +
                        "162,1.51934,13.64,3.54,0.75,72.65,0.16,8.89,0.15,0.24,3\n" +
                        "163,1.52211,14.19,3.78,0.91,71.36,0.23,9.14,0.00,0.37,3\n" +
                        "164,1.51514,14.01,2.68,3.50,69.89,1.68,5.87,2.20,0.00,5\n" +
                        "165,1.51915,12.73,1.85,1.86,72.69,0.60,10.09,0.00,0.00,5\n" +
                        "166,1.52171,11.56,1.88,1.56,72.86,0.47,11.41,0.00,0.00,5\n" +
                        "167,1.52151,11.03,1.71,1.56,73.44,0.58,11.62,0.00,0.00,5\n" +
                        "168,1.51969,12.64,0.00,1.65,73.75,0.38,11.53,0.00,0.00,5\n" +
                        "169,1.51666,12.86,0.00,1.83,73.88,0.97,10.17,0.00,0.00,5\n" +
                        "170,1.51994,13.27,0.00,1.76,73.03,0.47,11.32,0.00,0.00,5\n" +
                        "171,1.52369,13.44,0.00,1.58,72.22,0.32,12.24,0.00,0.00,5\n" +
                        "172,1.51316,13.02,0.00,3.04,70.48,6.21,6.96,0.00,0.00,5\n" +
                        "173,1.51321,13.00,0.00,3.02,70.70,6.21,6.93,0.00,0.00,5\n" +
                        "174,1.52043,13.38,0.00,1.40,72.25,0.33,12.50,0.00,0.00,5\n" +
                        "175,1.52058,12.85,1.61,2.17,72.18,0.76,9.70,0.24,0.51,5\n" +
                        "176,1.52119,12.97,0.33,1.51,73.39,0.13,11.27,0.00,0.28,5\n" +
                        "177,1.51905,14.00,2.39,1.56,72.37,0.00,9.57,0.00,0.00,6\n" +
                        "178,1.51937,13.79,2.41,1.19,72.76,0.00,9.77,0.00,0.00,6\n" +
                        "179,1.51829,14.46,2.24,1.62,72.38,0.00,9.26,0.00,0.00,6\n" +
                        "180,1.51852,14.09,2.19,1.66,72.67,0.00,9.32,0.00,0.00,6\n" +
                        "181,1.51299,14.40,1.74,1.54,74.55,0.00,7.59,0.00,0.00,6\n" +
                        "182,1.51888,14.99,0.78,1.74,72.50,0.00,9.95,0.00,0.00,6\n" +
                        "183,1.51916,14.15,0.00,2.09,72.74,0.00,10.88,0.00,0.00,6\n" +
                        "184,1.51969,14.56,0.00,0.56,73.48,0.00,11.22,0.00,0.00,6\n" +
                        "185,1.51115,17.38,0.00,0.34,75.41,0.00,6.65,0.00,0.00,6\n" +
                        "186,1.51131,13.69,3.20,1.81,72.81,1.76,5.43,1.19,0.00,7\n" +
                        "187,1.51838,14.32,3.26,2.22,71.25,1.46,5.79,1.63,0.00,7\n" +
                        "188,1.52315,13.44,3.34,1.23,72.38,0.60,8.83,0.00,0.00,7\n" +
                        "189,1.52247,14.86,2.20,2.06,70.26,0.76,9.76,0.00,0.00,7\n" +
                        "190,1.52365,15.79,1.83,1.31,70.43,0.31,8.61,1.68,0.00,7\n" +
                        "191,1.51613,13.88,1.78,1.79,73.10,0.00,8.67,0.76,0.00,7\n" +
                        "192,1.51602,14.85,0.00,2.38,73.28,0.00,8.76,0.64,0.09,7\n" +
                        "193,1.51623,14.20,0.00,2.79,73.46,0.04,9.04,0.40,0.09,7\n" +
                        "194,1.51719,14.75,0.00,2.00,73.02,0.00,8.53,1.59,0.08,7\n" +
                        "195,1.51683,14.56,0.00,1.98,73.29,0.00,8.52,1.57,0.07,7\n" +
                        "196,1.51545,14.14,0.00,2.68,73.39,0.08,9.07,0.61,0.05,7\n" +
                        "197,1.51556,13.87,0.00,2.54,73.23,0.14,9.41,0.81,0.01,7\n" +
                        "198,1.51727,14.70,0.00,2.34,73.28,0.00,8.95,0.66,0.00,7\n" +
                        "199,1.51531,14.38,0.00,2.66,73.10,0.04,9.08,0.64,0.00,7\n" +
                        "200,1.51609,15.01,0.00,2.51,73.05,0.05,8.83,0.53,0.00,7\n" +
                        "201,1.51508,15.15,0.00,2.25,73.50,0.00,8.34,0.63,0.00,7\n" +
                        "202,1.51653,11.95,0.00,1.19,75.18,2.70,8.93,0.00,0.00,7\n" +
                        "203,1.51514,14.85,0.00,2.42,73.72,0.00,8.39,0.56,0.00,7\n" +
                        "204,1.51658,14.80,0.00,1.99,73.11,0.00,8.28,1.71,0.00,7\n" +
                        "205,1.51617,14.95,0.00,2.27,73.30,0.00,8.71,0.67,0.00,7\n" +
                        "206,1.51732,14.95,0.00,1.80,72.99,0.00,8.61,1.55,0.00,7\n" +
                        "207,1.51645,14.94,0.00,1.87,73.11,0.00,8.67,1.38,0.00,7\n" +
                        "208,1.51831,14.39,0.00,1.82,72.86,1.41,6.47,2.88,0.00,7\n" +
                        "209,1.51640,14.37,0.00,2.74,72.85,0.00,9.45,0.54,0.00,7\n" +
                        "210,1.51623,14.14,0.00,2.88,72.61,0.08,9.18,1.06,0.00,7\n" +
                        "211,1.51685,14.92,0.00,1.99,73.06,0.00,8.40,1.59,0.00,7\n" +
                        "212,1.52065,14.36,0.00,2.02,73.42,0.00,8.44,1.64,0.00,7\n" +
                        "213,1.51651,14.38,0.00,1.94,73.61,0.00,8.48,1.57,0.00,7\n" +
                        "214,1.51711,14.23,0.00,2.08,73.36,0.00,8.62,1.67,0.00,7";

        String flowerData = "1 5.1 3.5 1.4 0.2\n" +
                "2 4.9 3.0 1.4 0.2\n" +
                "3 4.7 3.2 1.3 0.2\n" +
                "4 4.6 3.1 1.5 0.2\n" +
                "5 5.0 3.6 1.4 0.2\n" +
                "6 5.4 3.9 1.7 0.4\n" +
                "7 4.6 3.4 1.4 0.3\n" +
                "8 5.0 3.4 1.5 0.2\n" +
                "9 4.4 2.9 1.4 0.2\n" +
                "10 4.9 3.1 1.5 0.1\n" +
                "11 5.4 3.7 1.5 0.2\n" +
                "12 4.8 3.4 1.6 0.2\n" +
                "13 4.8 3.0 1.4 0.1\n" +
                "14 4.3 3.0 1.1 0.1\n" +
                "15 5.8 4.0 1.2 0.2\n" +
                "16 5.7 4.4 1.5 0.4\n" +
                "17 5.4 3.9 1.3 0.4\n" +
                "18 5.1 3.5 1.4 0.3\n" +
                "19 5.7 3.8 1.7 0.3\n" +
                "20 5.1 3.8 1.5 0.3\n" +
                "21 5.4 3.4 1.7 0.2\n" +
                "22 5.1 3.7 1.5 0.4\n" +
                "23 4.6 3.6 1.0 0.2\n" +
                "24 5.1 3.3 1.7 0.5\n" +
                "25 4.8 3.4 1.9 0.2\n" +
                "26 5.0 3.0 1.6 0.2\n" +
                "27 5.0 3.4 1.6 0.4\n" +
                "28 5.2 3.5 1.5 0.2\n" +
                "29 5.2 3.4 1.4 0.2\n" +
                "30 4.7 3.2 1.6 0.2\n" +
                "31 4.8 3.1 1.6 0.2\n" +
                "32 5.4 3.4 1.5 0.4\n" +
                "33 5.2 4.1 1.5 0.1\n" +
                "34 5.5 4.2 1.4 0.2\n" +
                "35 4.9 3.1 1.5 0.2\n" +
                "36 5.0 3.2 1.2 0.2\n" +
                "37 5.5 3.5 1.3 0.2\n" +
                "38 4.9 3.6 1.4 0.1\n" +
                "39 4.4 3.0 1.3 0.2\n" +
                "40 5.1 3.4 1.5 0.2\n" +
                "41 5.0 3.5 1.3 0.3\n" +
                "42 4.5 2.3 1.3 0.3\n" +
                "43 4.4 3.2 1.3 0.2\n" +
                "44 5.0 3.5 1.6 0.6\n" +
                "45 5.1 3.8 1.9 0.4\n" +
                "46 4.8 3.0 1.4 0.3\n" +
                "47 5.1 3.8 1.6 0.2\n" +
                "48 4.6 3.2 1.4 0.2\n" +
                "49 5.3 3.7 1.5 0.2\n" +
                "50 5.0 3.3 1.4 0.2\n" +
                "51 7.0 3.2 4.7 1.4\n" +
                "52 6.4 3.2 4.5 1.5\n" +
                "53 6.9 3.1 4.9 1.5\n" +
                "54 5.5 2.3 4.0 1.3\n" +
                "55 6.5 2.8 4.6 1.5\n" +
                "56 5.7 2.8 4.5 1.3\n" +
                "57 6.3 3.3 4.7 1.6\n" +
                "58 4.9 2.4 3.3 1.0\n" +
                "59 6.6 2.9 4.6 1.3\n" +
                "60 5.2 2.7 3.9 1.4\n" +
                "61 5.0 2.0 3.5 1.0\n" +
                "62 5.9 3.0 4.2 1.5\n" +
                "63 6.0 2.2 4.0 1.0\n" +
                "64 6.1 2.9 4.7 1.4\n" +
                "65 5.6 2.9 3.9 1.3\n" +
                "66 6.7 3.1 4.4 1.4\n" +
                "67 5.6 3.0 4.5 1.5\n" +
                "68 5.8 2.7 4.1 1.0\n" +
                "69 6.2 2.2 4.5 1.5\n" +
                "70 5.6 2.5 3.9 1.1\n" +
                "71 5.9 3.2 4.8 1.8\n" +
                "72 6.1 2.8 4.0 1.3\n" +
                "73 6.3 2.5 4.9 1.5\n" +
                "74 6.1 2.8 4.7 1.2\n" +
                "75 6.4 2.9 4.3 1.3\n" +
                "76 6.6 3.0 4.4 1.4\n" +
                "77 6.8 2.8 4.8 1.4\n" +
                "78 6.7 3.0 5.0 1.7\n" +
                "79 6.0 2.9 4.5 1.5\n" +
                "80 5.7 2.6 3.5 1.0\n" +
                "81 5.5 2.4 3.8 1.1\n" +
                "82 5.5 2.4 3.7 1.0\n" +
                "83 5.8 2.7 3.9 1.2\n" +
                "84 6.0 2.7 5.1 1.6\n" +
                "85 5.4 3.0 4.5 1.5\n" +
                "86 6.0 3.4 4.5 1.6\n" +
                "87 6.7 3.1 4.7 1.5\n" +
                "88 6.3 2.3 4.4 1.3\n" +
                "89 5.6 3.0 4.1 1.3\n" +
                "90 5.5 2.5 5.0 1.3\n" +
                "91 5.5 2.6 4.4 1.2\n" +
                "92 6.1 3.0 4.6 1.4\n" +
                "93 5.8 2.6 4.0 1.2\n" +
                "94 5.0 2.3 3.3 1.0\n" +
                "95 5.6 2.7 4.2 1.3\n" +
                "96 5.7 3.0 4.2 1.2\n" +
                "97 5.7 2.9 4.2 1.3\n" +
                "98 6.2 2.9 4.3 1.3\n" +
                "99 5.1 2.5 3.0 1.1\n" +
                "100 5.7 2.8 4.1 1.3\n" +
                "101 6.3 3.3 6.0 2.5\n" +
                "102 5.8 2.7 5.1 1.9\n" +
                "103 7.1 3.0 5.9 2.1\n" +
                "104 6.3 2.9 5.6 1.8\n" +
                "105 6.5 3.0 5.8 2.2\n" +
                "106 7.6 3.0 6.6 2.1\n" +
                "107 4.9 2.5 4.5 1.7\n" +
                "108 7.3 2.9 6.3 1.8\n" +
                "109 6.7 2.5 5.8 1.8\n" +
                "110 7.2 3.6 6.1 2.5\n" +
                "111 6.5 3.2 5.1 2.0\n" +
                "112 6.4 2.7 5.3 1.9\n" +
                "113 6.8 3.0 5.5 2.1\n" +
                "114 5.7 2.5 5.0 2.0\n" +
                "115 5.8 2.8 5.1 2.4\n" +
                "116 6.4 3.2 5.3 2.3\n" +
                "117 6.5 3.0 5.5 1.8\n" +
                "118 7.7 3.8 6.7 2.2\n" +
                "119 7.7 2.6 6.9 2.3\n" +
                "120 6.0 2.2 5.0 1.5\n" +
                "121 6.9 3.2 5.7 2.3\n" +
                "122 5.6 2.8 4.9 2.0\n" +
                "123 7.7 2.8 6.7 2.0\n" +
                "124 6.3 2.7 4.9 1.8\n" +
                "125 6.7 3.3 5.7 2.1\n" +
                "126 7.2 3.2 6.0 1.8\n" +
                "127 6.2 2.8 4.8 1.8\n" +
                "128 6.1 3.0 4.9 1.8\n" +
                "129 6.4 2.8 5.6 2.1\n" +
                "130 7.2 3.0 5.8 1.6\n" +
                "131 7.4 2.8 6.1 1.9\n" +
                "132 7.9 3.8 6.4 2.0\n" +
                "133 6.4 2.8 5.6 2.2\n" +
                "134 6.3 2.8 5.1 1.5\n" +
                "135 6.1 2.6 5.6 1.4\n" +
                "136 7.7 3.0 6.1 2.3\n" +
                "137 6.3 3.4 5.6 2.4\n" +
                "138 6.4 3.1 5.5 1.8\n" +
                "139 6.0 3.0 4.8 1.8\n" +
                "140 6.9 3.1 5.4 2.1\n" +
                "141 6.7 3.1 5.6 2.4\n" +
                "142 6.9 3.1 5.1 2.3\n" +
                "143 5.8 2.7 5.1 1.9\n" +
                "144 6.8 3.2 5.9 2.3\n" +
                "145 6.7 3.3 5.7 2.5\n" +
                "146 6.7 3.0 5.2 2.3\n" +
                "147 6.3 2.5 5.0 1.9\n" +
                "148 6.5 3.0 5.2 2.0\n" +
                "149 6.2 3.4 5.4 2.3\n" +
                "150 5.9 3.0 5.1 1.8";


        String sourceData = flowerData;

        String[] dataArray = sourceData.split("\n");
        List<Double[]> samples = new ArrayList<>();
        List<Double[]> printSamples = new ArrayList<>();
        for (String dataRecord : dataArray) {
            String[] doubles = dataRecord.split(" ");
            Double[] sample = new Double[doubles.length - 1];
            Double[] printSample = new Double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                printSample[i] = Double.valueOf(doubles[i]);
                if (i == 0) continue;
                sample[i - 1] = Double.valueOf(doubles[i]);
            }
            samples.add(sample);
            printSamples.add(printSample);
        }
        Double[][] dataSample = new Double[samples.size()][];
        for (int i = 0; i < samples.size(); i++) {
            dataSample[i] = samples.get(i);
        }
        myKmeansUtil.doKmeans(3, new KmeansData(dataSample, 150, 4), 1200);

        Double[][] printSamplesArray = new Double[printSamples.size()][];
        for (int i = 0; i < printSamples.size(); i++) {
            printSamplesArray[i] = printSamples.get(i);
        }
        myKmeansUtil.printGroupedSample(myKmeansUtil.getKmeansResult().labels, printSamplesArray, 3);
    }
}
