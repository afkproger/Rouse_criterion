package org.example.rouse_criterion.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rouse_criterion {
    private ArrayList<Double> coefficients;

    public ArrayList<Double> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(ArrayList<Double> coefficients) {
        this.coefficients = coefficients;
    }

    public Rouse_criterion(ArrayList<Double> coefficients) {
        this.coefficients = coefficients;
    }

    private static double roundToPrecision(double value, int precision) {
        return new BigDecimal(value).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    private static void calculateCoefficients(List<List<Double>> rouseMatrix) {
        int rowCount = rouseMatrix.size();
        int colCount = rouseMatrix.get(0).size() - 1;

        for (int i = 2; i < rowCount; i++) {
            double const_1 = rouseMatrix.get(i - 2).get(0); //an
            double const_2 = rouseMatrix.get(i - 1).get(0); //an-1
            for (int j = 0; j < colCount; j++) {
                double coeff = (-(1 / const_2)) * ((const_1 * rouseMatrix.get(i - 1).get(j + 1)) - (const_2 * rouseMatrix.get(i - 2).get(j + 1)));
                coeff = roundToPrecision(coeff, 5);
                rouseMatrix.get(i).set(j, coeff);
            }
        }
    }

    // метод для формирования таблицы Рауса
    private static List<List<Double>> generateMatrix(List<Double> parseData) {
        if (parseData != null && parseData.size() > 2) {
            List<List<Double>> matrix = new ArrayList<>();
            for (int i = 0; i < parseData.size(); i++) {
                List<Double> row = new ArrayList<>(Collections.nCopies(parseData.size() - 1, 0.0));
                matrix.add(row);
            }

            int k = 0; // счётчик для 1 строки
            int j = 0; // счётчик для 2 строки
            for (int i = 0; i < parseData.size(); i++) {
                if (i % 2 == 0) {
                    matrix.get(0).set(k, parseData.get(i));
                    k += 1;
                } else {
                    matrix.get(1).set(j, parseData.get(i));
                    j += 1;
                }
            }

            return matrix;
        } else {
            System.out.println("Ошибочка вышла ");
            return null;
        }
    }


    public static List<List<Double>> calculateRouseMatrix(String inputData) {
        try {
            List<Double> parseData = Validation.checkInputData(inputData);
            List<List<Double>> rouseMatrix = generateMatrix(parseData);
            calculateCoefficients(rouseMatrix);
            return rouseMatrix;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();

        }

    }

   public static void main(String[] args) {
        String inputData = "2, 5 ,4 ,2 , 1";
//       String inputData = "2, 5 ,4 ,2 , 1";
        List<List<Double>> rouseMatrix = calculateRouseMatrix(inputData);
       System.out.println("Система устойчива ? " + Validation.isSystemStable(rouseMatrix));
   }

}
