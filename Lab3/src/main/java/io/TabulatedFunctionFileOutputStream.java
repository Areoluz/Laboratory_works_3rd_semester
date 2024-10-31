package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) {
        try (
                FileOutputStream arrayFile = new FileOutputStream("output/binary function.bin");
                FileOutputStream linkedListFile = new FileOutputStream("output/linked list function.bin");
                BufferedOutputStream arrayBuffer = new BufferedOutputStream(arrayFile);
                BufferedOutputStream linkedListBuffer = new BufferedOutputStream(linkedListFile)
        ) {
            // Создаем функции
            TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new double[]{0.0, 0.5, 1.0}, new double[]{0.0, 0.25, 1.0});
            TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new double[]{0.0, 0.5, 1.0}, new double[]{0.0, 0.25, 1.0});

            // Записываем функции в файлы
            FunctionsIO.writeTabulatedFunction(arrayBuffer, arrayFunction);
            FunctionsIO.writeTabulatedFunction(linkedListBuffer, linkedListFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

