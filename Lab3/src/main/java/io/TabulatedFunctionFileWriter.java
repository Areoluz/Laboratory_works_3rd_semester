package io;

import functions.TabulatedFunction;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;

import java.io.*;

import static io.FunctionsIO.writeTabulatedFunction;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) {
        try (FileWriter arrayWriter = new FileWriter("G://output/array_function.txt"); // костыль из-за OneDrive'a
             BufferedWriter bufferedArrayWriter = new BufferedWriter(arrayWriter);
             FileWriter linkedListWriter = new FileWriter("G://output/linked_list_function.txt"); //костыль из-за OneDrive'a
             BufferedWriter bufferedLinkedListWriter = new BufferedWriter(linkedListWriter)) {

            TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 4, 9, 16, 25});
            TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{5, 4, 3, 2, 1});

            writeTabulatedFunction(bufferedArrayWriter, arrayFunction);
            writeTabulatedFunction(bufferedLinkedListWriter, linkedListFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
