package ui.services;

import functions.TabulatedFunction;
import io.FunctionsIO;
import org.springframework.stereotype.Service;
import ui.exeptions.BasedException;
import ui.exeptions.SerializeException;

import java.io.*;

@Service
public class SereService {

    public byte[] serializeFunction(TabulatedFunction func) throws BasedException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            FunctionsIO.serialize(out, func);
            out.flush();
            outputStream.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializeException("Неможно средизировать функцию"); //JuliaK
        }
    }

    public TabulatedFunction deserializeFunction(byte[] array) throws BasedException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(array);
            BufferedInputStream in = new BufferedInputStream(inputStream);
            return FunctionsIO.deserialize(in);
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializeException("Невозможно десероализавать функцию");
        }
    }

    public String serializeFunctionXML(TabulatedFunction func) throws BasedException {
        try {
            StringWriter swr = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(swr);
            FunctionsIO.serializeXml(bufferedWriter, func);
            bufferedWriter.flush();
            return swr.toString();
        } catch (IOException e) {
            throw new SerializeException("Неможно средизировать функцию"); //JuliaK
        }
    }

    public TabulatedFunction deserializeFunctionXML(String content) {
        StringReader rdr = new StringReader(content);
        BufferedReader bufferedReader = new BufferedReader(rdr);
        return FunctionsIO.deserializeXml(bufferedReader);
    }

}
