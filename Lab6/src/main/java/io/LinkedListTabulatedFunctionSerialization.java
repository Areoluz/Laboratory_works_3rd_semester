package io;

import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import functions.TabulatedFunction;
import functions.LinkedListTabulatedFunction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LinkedListTabulatedFunctionSerialization {

    public static void main(String[] args) {
        try (FileOutputStream fileOutput = new FileOutputStream("output/serialized linked list functions.bin");
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput)) {

            TabulatedFunction function = new LinkedListTabulatedFunction(new double[]{0.0, 0.5, 1.0}, new double[]{0.0, 0.25, 1.0});

            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());

            TabulatedFunction firstDerivative = operator.derive(function);
            TabulatedFunction secondDerivative = operator.derive(firstDerivative);

            // Сериализуем все три функции
            FunctionsIO.serialize(bufferedOutput, function);
            FunctionsIO.serialize(bufferedOutput, firstDerivative);
            FunctionsIO.serialize(bufferedOutput, secondDerivative);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream fileInput = new FileInputStream("output/serialized linked list functions.bin");
             BufferedInputStream bufferedInput = new BufferedInputStream(fileInput)) {

            // Десериализуем функции
            TabulatedFunction deserializedFunction = FunctionsIO.deserialize(bufferedInput);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(bufferedInput);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(bufferedInput);

            // Выводим функции в консоль
            System.out.println("Original Function:");
            System.out.println(deserializedFunction);
            System.out.println("First Derivative:");
            System.out.println(deserializedFirstDerivative);
            System.out.println("Second Derivative:");
            System.out.println(deserializedSecondDerivative);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

