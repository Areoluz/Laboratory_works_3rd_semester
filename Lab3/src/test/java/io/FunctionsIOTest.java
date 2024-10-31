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
    void serializeAndDeserializeFunction() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("serialized_function", ".bin");
        tempFile.deleteOnExit();

        TabulatedFunction originalFunction = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            FunctionsIO.serialize(outputStream, originalFunction);
        }

        TabulatedFunction deserializedFunction;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile))) {
            deserializedFunction = FunctionsIO.deserialize(inputStream);
        }

        assertEquals(originalFunction.getCount(), deserializedFunction.getCount());
        for (int i = 0; i < originalFunction.getCount(); i++) {
            assertEquals(originalFunction.getX(i), deserializedFunction.getX(i), 1e-9);
            assertEquals(originalFunction.getY(i), deserializedFunction.getY(i), 1e-9);
        }
    }
}
