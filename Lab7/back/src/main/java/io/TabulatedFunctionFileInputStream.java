package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) throws FileNotFoundException {
        try (var inputf = new BufferedInputStream(new FileInputStream("input/binary function.bin"))) {
            TabulatedFunction arr = FunctionsIO.readTabulatedFunction(inputf, new ArrayTabulatedFunctionFactory());
            System.out.println(arr);
        } catch (IOException excep) {
            excep.printStackTrace();
        }
        System.out.println("Введите размер и значения функции");
        try {
            var inputc = new BufferedReader((new InputStreamReader(System.in)));
            TabulatedFunction list = FunctionsIO.readTabulatedFunction(inputc, new LinkedListTabulatedFunctionFactory());
            System.out.println(new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory()).derive(list));
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }
}