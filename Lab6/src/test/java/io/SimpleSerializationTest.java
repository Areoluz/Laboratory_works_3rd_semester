package io;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import io.FunctionsIO;

import java.io.*;

public class SimpleSerializationTest {

    public static void main(String[] args) {
        // Имя файла для временного хранения данных
        String filePath = "output/simple_serialization_test.bin";

        // Шаг 1: Создаем и сериализуем одну функцию
        try (FileOutputStream fileOutput = new FileOutputStream(filePath);
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput)) {

            TabulatedFunction function = new LinkedListTabulatedFunction(
                    new double[]{0.0, 0.5, 1.0},
                    new double[]{0.0, 0.25, 1.0}
            );

            System.out.println("Original Function:");
            System.out.println(function);  // Выводим исходную функцию

            // Сериализация
            FunctionsIO.serialize(bufferedOutput, function);
            System.out.println("Function serialized successfully.");

        } catch (IOException e) {
            System.err.println("Error during serialization:");
            e.printStackTrace();
        }

        // Шаг 2: Проверяем файл
        File serializedFile = new File(filePath);
        if (serializedFile.exists() && serializedFile.length() > 0) {
            System.out.println("Serialized file created: " + serializedFile.getAbsolutePath());
            System.out.println("File size: " + serializedFile.length() + " bytes");
        } else {
            System.err.println("Serialized file was not created or is empty.");
            return;
        }

        // Шаг 3: Десериализация
        try (FileInputStream fileInput = new FileInputStream(filePath);
             BufferedInputStream bufferedInput = new BufferedInputStream(fileInput)) {

            TabulatedFunction deserializedFunction = FunctionsIO.deserialize(bufferedInput);

            System.out.println("Deserialized Function:");
            System.out.println(deserializedFunction);  // Выводим десериализованную функцию

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization:");
            e.printStackTrace();
        }
    }
}
