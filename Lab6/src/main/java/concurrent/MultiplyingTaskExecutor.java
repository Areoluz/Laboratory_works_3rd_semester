package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnitFunction;
import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 100);

        // Список для хранения потоков
        List<Thread> threads = new ArrayList<>();

        // задачи и потоки для их выполнения
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(tabulatedFunction);
            Thread thread = new Thread(task);
            threads.add(thread);
        }

        // Стартуем все потоки
        for (Thread thread : threads) {
            thread.start();
        }

        // Усыпляем текущий поток на пару секунд, чтобы дать другим потокам шанс выполниться
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Выводим значения табулированной функции после завершения всех потоков
        System.out.println("Результирующие значения табулированной функции:");
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            System.out.println("x: " + tabulatedFunction.getX(i) + ", y: " + tabulatedFunction.getY(i));
        }
    }
}