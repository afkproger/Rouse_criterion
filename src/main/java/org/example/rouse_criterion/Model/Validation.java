package org.example.rouse_criterion.Model;

import java.util.ArrayList;
import java.util.List;

public class Validation {
    //метод для обкатки и проверки входящей строки коэффицентов
    public static List<Double> checkInputData(String inputData) {
        try {
            List<String> str = new ArrayList<>(List.of(inputData.split(",")));
            List<Double> result = str.stream().map(String::trim).map(Double::parseDouble).toList();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Boolean> analyzeRouseMatrix(List<List<Double>> rouseMatrix) {
        List<Boolean> result = new ArrayList<>();
        result.add(isSystemStable(rouseMatrix));
        result.add(isSystemOnTheVergeStability(rouseMatrix));
        result.add(isSystemOnOscillatoryStability(rouseMatrix));

        return result;
    }

    //метод для анализа матрицы рауса
    private static boolean isSystemStable(List<List<Double>> rouseMatrix) {
        for (List<Double> row : rouseMatrix) {
            if (row.get(0) <= 0) return false;
        }
        return true;
    }

    private static boolean isSystemOnTheVergeStability(List<List<Double>> rouseMatrix) {
        int zeroCount = 0;
        if (isSystemStable(rouseMatrix)) {
            for (List<Double> row : rouseMatrix) {
                if (row.get(0) == 0) zeroCount++;
            }
        } else {
            return false;
        }

        return zeroCount >= 1;
    }

    private static boolean isSystemOnOscillatoryStability(List<List<Double>> rouseMatrix) {
        if (isSystemOnTheVergeStability(rouseMatrix)) {
            return rouseMatrix.get(0).get(rouseMatrix.size()) == 0;
        } else {
            return false;
        }
    }
}
