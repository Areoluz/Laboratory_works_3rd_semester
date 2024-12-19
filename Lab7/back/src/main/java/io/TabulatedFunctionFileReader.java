package io;

import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class    TabulatedFunctionFileReader {
    public static void main(String[] args) {
        try {
            // Определение абсолютного пути к файлу input
            File inputFile = new File("G://input/function.txt").getAbsoluteFile();
            if (!inputFile.exists()) {
                System.err.println("Файл function.txt не найден: " + inputFile.getAbsolutePath());
                return;
            }

            // Создание файловых потоков чтения
            try (FileReader arrayReader = new FileReader(inputFile);
                 BufferedReader bufferedArrayReader = new BufferedReader(arrayReader);
                 FileReader linkedListReader = new FileReader(inputFile);
                 BufferedReader bufferedLinkedListReader = new BufferedReader(linkedListReader)) {

                System.out.println("Чтение из файла начато...");

                // Чтение функций из файла с использованием фабрик
                TabulatedFunctionFactory arrayFactory = new functions.factory.ArrayTabulatedFunctionFactory();
                TabulatedFunctionFactory linkedListFactory = new functions.factory.LinkedListTabulatedFunctionFactory();

                TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(bufferedArrayReader, arrayFactory);
                TabulatedFunction linkedListFunction = FunctionsIO.readTabulatedFunction(bufferedLinkedListReader, linkedListFactory);

                // Вывод функций в консоль
                System.out.println("Array Function: " + arrayFunction);
                System.out.println("Linked List Function: " + linkedListFunction);

                System.out.println("Чтение из файла завершено успешно.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
