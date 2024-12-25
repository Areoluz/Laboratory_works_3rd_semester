package io;

import functions.TabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {
    // Приватный конструктор, чтобы запретить создание экземпляров класса
    private FunctionsIO() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        var inp = new ObjectInputStream(stream);
        return (TabulatedFunction) inp.readObject();
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutput = new ObjectOutputStream(stream);
        objectOutput.writeObject(function);
        stream.flush(); // Пробрасываем данные из буфера
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(function.getCount());
        for (var point : function) {
            printWriter.printf("%f %f%n", point.x, point.y);
        }
        writer.flush();
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        try (DataOutputStream dataOutput = new DataOutputStream(outputStream)) {
            dataOutput.writeInt(function.getCount());
            for (var point : function) {
                dataOutput.writeDouble(point.x);
                dataOutput.writeDouble(point.y);
            }
            dataOutput.flush(); // Пробрасываем данные из буфера
        }
    }

    static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        int size = Integer.parseInt(reader.readLine());
        double[] xValues = new double[size];
        double[] yValues = new double[size];
        NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        for (int i = 0; i < size; i++) {
            String line = reader.readLine();
            String[] values = line.split(" ");
            try {
                xValues[i] = formatter.parse(values[0]).doubleValue();
                yValues[i] = formatter.parse(values[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
        return factory.create(xValues, yValues);
    }

    static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        var binput = new DataInputStream(inputStream);
        int size = binput.readInt();
        double[] xValues = new double[size];
        double[] yValues = new double[size];
        for (int i = 0; i < size; ++i) {
            xValues[i] = binput.readDouble();
            yValues[i] = binput.readDouble();
        }
        return factory.create(xValues, yValues);
    }

}

