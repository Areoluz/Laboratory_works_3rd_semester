package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsIOTest {

    @Test
    void writeAndReadTabulatedFunction_Array() throws IOException {
        File tempFile = File.createTempFile("array_function", ".bin");
        tempFile.deleteOnExit(); // Удаляет файл после завершения теста

        TabulatedFunction originalFunction = new ArrayTabulatedFunction(
                new double[]{0.0, 0.5, 1.0},
                new double[]{0.0, 0.25, 1.0}
        );

        // Запись функции в файл
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            FunctionsIO.writeTabulatedFunction(outputStream, originalFunction);
        }

        // Чтение функции из файла
        TabulatedFunction readFunction;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile))) {
            readFunction = FunctionsIO.readTabulatedFunction(inputStream, new ArrayTabulatedFunctionFactory());
        }

        assertEquals(originalFunction.getCount(), readFunction.getCount());
        for (int i = 0; i < originalFunction.getCount(); i++) {
            assertEquals(originalFunction.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(originalFunction.getY(i), readFunction.getY(i), 1e-9);
        }
    }

    @Test
    void writeAndReadTabulatedFunction_LinkedList() throws IOException {
        File tempFile = File.createTempFile("linked_list_function", ".bin");
        tempFile.deleteOnExit();

        TabulatedFunction originalFunction = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            FunctionsIO.writeTabulatedFunction(outputStream, originalFunction);
        }

        TabulatedFunction readFunction;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile))) {
            readFunction = FunctionsIO.readTabulatedFunction(inputStream, new LinkedListTabulatedFunctionFactory());
        }

        assertEquals(originalFunction.getCount(), readFunction.getCount());
        for (int i = 0; i < originalFunction.getCount(); i++) {
            assertEquals(originalFunction.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(originalFunction.getY(i), readFunction.getY(i), 1e-9);
        }
    }

    @Test
    void testDeserialize() throws IOException, ClassNotFoundException {
        // Создаем временный файл для хранения сериализованных данных
        File tempFile = File.createTempFile("test_deserialize", ".bin");
        tempFile.deleteOnExit(); // Удаляет временный файл после завершения теста

        // Шаг 1: Создание функции и сериализация
        TabulatedFunction originalFunction = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            FunctionsIO.serialize(bufferedOutputStream, originalFunction);
        }

        // Шаг 2: Десериализация функции
        TabulatedFunction deserializedFunction;
        try (FileInputStream fileInputStream = new FileInputStream(tempFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            deserializedFunction = FunctionsIO.deserialize(bufferedInputStream);
        }

        // Шаг 3: Сравнение исходной и десериализованной функций
        assertTabulatedFunctionsEqual(originalFunction, deserializedFunction);
    }

    private void assertTabulatedFunctionsEqual(TabulatedFunction expected, TabulatedFunction actual) {
        assertEquals(expected.getCount(), actual.getCount(), "Количество точек не совпадает");

        for (int i = 0; i < expected.getCount(); i++) {
            assertEquals(expected.getX(i), actual.getX(i), 1e-9, "Значение X не совпадает на позиции " + i);
            assertEquals(expected.getY(i), actual.getY(i), 1e-9, "Значение Y не совпадает на позиции " + i);
        }
    }

}
