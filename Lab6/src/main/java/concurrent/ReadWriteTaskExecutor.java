package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {

        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new ConstantFunction(-1), 1, 1000, 1000);

        // Создание задач
        ReadTask readTask = new ReadTask(tabulatedFunction);
        WriteTask writeTask = new WriteTask(tabulatedFunction, 0.5);

        // Создание потоков
        Thread readThread = new Thread(readTask);
        Thread writeThread = new Thread(writeTask);

        // Запуск потоков
        readThread.start();
        writeThread.start();
    }
}

