package concurrent;

import functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    private final TabulatedFunction tabulatedFunction;

    public MultiplyingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        synchronized (tabulatedFunction) {  // Синхронизируем доступ к табулированной функции
            for (int i = 0; i < tabulatedFunction.getCount(); i++) {
                double y = tabulatedFunction.getY(i);
                // Увеличиваем значение y в 2 раза и перезаписываем его
                tabulatedFunction.setY(i, y * 2);
            }
        }

        // Выводим информацию о завершении задачи
        System.out.println("Поток " + Thread.currentThread().getName() + " закончил выполнение задачи.");
    }
}