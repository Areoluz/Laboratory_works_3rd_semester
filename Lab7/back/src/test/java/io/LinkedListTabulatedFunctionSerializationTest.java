package io;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListTabulatedFunctionSerializationTest {

    @Test
    void testSerializeAndDeserializeLinkedListTabulatedFunction() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("serialized_linked_list_functions_test", ".bin");
        tempFile.deleteOnExit(); // Автоматически удаляет файл после завершения теста

        // Создание исходной функции и её производных
        TabulatedFunction originalFunction = new LinkedListTabulatedFunction(
                new double[]{0.0, 0.5, 1.0},
                new double[]{0.0, 0.25, 1.0}
        );

        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction firstDerivative = operator.derive(originalFunction);
        TabulatedFunction secondDerivative = operator.derive(firstDerivative);

        // Сериализация всех трёх функций
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

            FunctionsIO.serialize(bufferedOutputStream, originalFunction);
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);
        }

        // Десериализация всех трёх функций
        TabulatedFunction deserializedFunction;
        TabulatedFunction deserializedFirstDerivative;
        TabulatedFunction deserializedSecondDerivative;

        try (FileInputStream fileInputStream = new FileInputStream(tempFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

            deserializedFunction = FunctionsIO.deserialize(bufferedInputStream);
            deserializedFirstDerivative = FunctionsIO.deserialize(bufferedInputStream);
            deserializedSecondDerivative = FunctionsIO.deserialize(bufferedInputStream);
        }

        // Проверка оригинальной функции и её производных
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
