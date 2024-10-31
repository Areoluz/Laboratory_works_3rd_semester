package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try {
            // Создание файлового байтового потока записи с использованием try-with-resources
            File outputFile = new File("output/serialized_array_functions.bin").getAbsoluteFile();
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new double[]{0, 1, 2, 3, 4}, new double[]{0, 1, 4, 9, 16});

                // Найти первую и вторую производные
                TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();
                TabulatedFunction firstDerivative = differentialOperator.derive(arrayFunction);
                TabulatedFunction secondDerivative = differentialOperator.derive(firstDerivative);

                // Сериализация всех трёх функций
                FunctionsIO.serialize(bufferedOutputStream, arrayFunction);
                FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
                FunctionsIO.serialize(bufferedOutputStream, secondDerivative);
            }

            // Создание файлового байтового потока чтения с использованием try-with-resources
            try (FileInputStream fileInputStream = new FileInputStream(outputFile);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                // Десериализация всех трёх функций
                TabulatedFunction deserializedArrayFunction = FunctionsIO.deserialize(bufferedInputStream);
                TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(bufferedInputStream);
                TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(bufferedInputStream);

                // Вывод значений всех функций в консоль
                System.out.println("Original Function: " + deserializedArrayFunction);
                System.out.println("First Derivative: " + deserializedFirstDerivative);
                System.out.println("Second Derivative: " + deserializedSecondDerivative);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
