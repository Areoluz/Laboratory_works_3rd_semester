package io;

import functions.TabulatedFunction;

import java.io.*;

public final class FunctionsIO {
    // Приватный конструктор, чтобы запретить создание экземпляров класса
    private FunctionsIO() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }
    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(function.getCount());
        for (var point : function) {
            printWriter.printf("%f %f%n", point.x, point.y);
        }
        writer.flush();
    }
}