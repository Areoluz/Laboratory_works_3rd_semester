package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import io.FunctionsIO;
import operations.TabulatedDifferentialOperator;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTabulatedFunctionSerializationTest {

    @Test
    void testSerializeAndDeserializeArrayTabulatedFunction() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("serialized_array_functions_test", ".bin");
        tempFile.deleteOnExit();

        TabulatedFunction originalFunction = new ArrayTabulatedFunction(
                new double[]{0, 1, 2, 3, 4},
                new double[]{0, 1, 4, 9, 16}
        );
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        TabulatedFunction firstDerivative = operator.derive(originalFunction);
        TabulatedFunction secondDerivative = operator.derive(firstDerivative);

        // Сериализация
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

            System.out.println("Serializing original function...");
            FunctionsIO.serialize(bufferedOutputStream, originalFunction);
            System.out.println("Serializing first derivative...");
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            System.out.println("Serializing second derivative...");
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);
        }

        System.out.println("File size after serialization: " + tempFile.length() + " bytes");

        // Десериализация всех трёх функций
        TabulatedFunction deserializedFunction;
        TabulatedFunction deserializedFirstDerivative;
        TabulatedFunction deserializedSecondDerivative;
        try (FileInputStream fileInputStream = new FileInputStream(tempFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

            System.out.println("Deserializing original function...");
            deserializedFunction = FunctionsIO.deserialize(bufferedInputStream);
            System.out.println("Deserialized function point count: " + deserializedFunction.getCount());

            System.out.println("Deserializing first derivative...");
            deserializedFirstDerivative = FunctionsIO.deserialize(bufferedInputStream);
            System.out.println("First derivative point count: " + deserializedFirstDerivative.getCount());

            System.out.println("Deserializing second derivative...");
            deserializedSecondDerivative = FunctionsIO.deserialize(bufferedInputStream);
            System.out.println("Second derivative point count: " + deserializedSecondDerivative.getCount());
        }

        // Проверка оригинальной функции и десериализованной
        assertTabulatedFunctionsEqual(originalFunction, deserializedFunction);
        assertTabulatedFunctionsEqual(firstDerivative, deserializedFirstDerivative);
        assertTabulatedFunctionsEqual(secondDerivative, deserializedSecondDerivative);
    }

    private void assertTabulatedFunctionsEqual(TabulatedFunction expected, TabulatedFunction actual) {
        assertEquals(expected.getCount(), actual.getCount(), "Количество точек не совпадает");

        for (int i = 0; i < expected.getCount(); i++) {
            assertEquals(expected.getX(i), actual.getX(i), 1e-9, "Значение X не совпадает на позиции " + i);
            assertEquals(expected.getY(i), actual.getY(i), 1e-9, "Значение Y не совпадает на позиции " + i);
        }
    }
}
