package io;

import functions.ArrayTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;

import java.io.*;

public class ArrayTabulatedFunctionSerializationXML {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter("output/serialized array functions.xml")) {

            BufferedWriter bw = new BufferedWriter(fw);
            SqrFunction sqr = new SqrFunction();
            ArrayTabulatedFunction atf = new ArrayTabulatedFunction(sqr, 1, 10, 10);


            FunctionsIO.serializeXml(bw, atf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(1);
        try (FileReader fr = new FileReader("output/serialized array functions.xml")) {

            BufferedReader br = new BufferedReader(fr);
            TabulatedFunction function = FunctionsIO.deserializeXml(br);
            System.out.println(function.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
