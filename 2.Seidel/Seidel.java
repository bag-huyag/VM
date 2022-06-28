import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Seidel {
    private double     epsilon       = 0.000001f; // Точность вычислений
    private int        size; // Размер матрицы
    private double[][] matrix; // Матрица
    private int        maxIterations = 100; // Максимальное количество итераций
    private int[]      ints; // Короткий массив с индексами строк
    private double[]   sums; // Сумма строк

    // Инициализация матрицы
    public void init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scanner = new Scanner(file);
        Pattern pattern = Pattern.compile("[ \t]+");
        String string = scanner.nextLine();
        String[] strings = pattern.split(string);
        size = Integer.parseInt(strings[0]);
        create(size);
        for (int i = 0; i < size; i++) {
            string = scanner.nextLine().trim();
            strings = pattern.split(string);
            for (int j = 0; j < size + 1; j++) matrix[i][j] = Double.parseDouble(strings[j].replace(',', '.'));
        }
        scanner.close();
    }

    // Создание матрицы
    private void create(int size) {
        matrix = new double[size][];
        for (int i = 0; i < size; i++) matrix[i] = new double[size + 1];
    }

    // Вывод матрицы на экран
    public void print() {
        for (double[] doubles : matrix) {
            for (double d : doubles) System.out.printf("%15.6E", d);
            System.out.println();
        }
    }

    // Проверка матрицы.
    public int check() {
        ints = new int[size];
        for (int i = 0; i < size; i++) ints[i] = i;
        countSum();

        if (checkDiagonal()) {
            boolean condition = findBestCombination();
            createNewArray();
            return condition ? 0 : 2; // Возвращаем 0, если ДУС выполняется и 2, если от нулей на диагонали не удалось избавиться
        } // Сначала проверяем диагональ на нули
        if (DUS(ints)) return 2; // Потом на ДУС
        else return 1;
    }

    // Проверить диагональ матрицы на нули
    private boolean checkDiagonal() {
        for (int i = 0; i < size; i++) if (Math.abs(matrix[i][i]) <= epsilon) return true;
        return false;
    }

    // Найти перестановку, которая удовлетворяет ДУС
    private boolean findBestCombination() {
        boolean bool = false;
        int[] temp = new int[size];
        for (int i = 0; i < size; i++) temp[i] = i;

        do {
            if (DUS(temp)) {
                for (int i = 0; i < size; i++) ints[i] = temp[i];
                return true;
            }
            if (!bool && !checkDiagonal()) {
                bool = true;
                for (int i = 0; i < size; i++) ints[i] = temp[i];
            }
        } while (nextPermutation(temp));

        return bool;
    }

    // Решить матрицу и вывести её на экран
    public void solution(boolean control) {
        int counter = 0;
        double[] approx = new double[size];
        int i = 0;
        if (control) { // Решение с контролем
            i = 1;
            double max = iter(approx);
            for (; i < 10; i++) {
                double temp = iter(approx);
                if (max > temp || temp < epsilon) {
                    max = temp;
                    counter++;
                    if (Math.abs(max) <= epsilon || counter == 4) break;
                } else counter = 0;
            }
            if (counter != 4) {
                System.out.println("Решения нет");
                return;
            }
        }
        // Решение без контроля
        for (; i < maxIterations; i++) {
            if (!(Math.abs(iter(approx)) <= epsilon)) {
                continue;
            }
            break;
        }
        for (double a : approx) System.out.printf("%15.6E", a);
    }

    // Посчитать суммы строк
    private void countSum() {
        sums = new double[size];
        for (int i = 0; i < size; i++) {
            sums[i] = 0;
            for (int j = 0; j < size; j++) sums[i] += Math.abs(matrix[i][j]);
        }
    }

    // Вычислить максимальную абсолютную разность между приближениями
    private double iter(double[] approx) {
        double max = 0;
        //метод 1 итерации
        double a;
        for (int i = 0; i < size; i++) {
            a = approx[i];
            approx[i] = matrix[i][size];
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    continue;
                }
                approx[i] -= matrix[i][j] * approx[j];
            }
            approx[i] /= matrix[i][i];
            //макс. разность
            max = Math.max(Math.abs(approx[i] - a), Math.abs(max));
        }
        return max;
    }

    // Проверка на выполнение ДУС
    private boolean DUS(int[] indexes) {
        int check = 0;
        for (int i = 0; i < size; i++) {
            if (sums[indexes[i]] > 2 * Math.abs(matrix[indexes[i]][i])) return false;
            if (sums[indexes[i]] == 2 * Math.abs(matrix[indexes[i]][i])) {
                if (check != size - 1) check++;
                else return false;
            }
        }
        return true;
    }

    // Найти следующую перестановку перебором всех комбинаций в массиве
    private boolean nextPermutation(int[] array) {
        if (array.length <= 1) return false;

        int last = array.length - 2;
        while (last >= 0) {
            if (array[last] < array[last + 1]) break;
            last--;
        }
        if (last < 0) return false;
        int nextGreater = array.length - 1;
        for (int i = array.length - 1; i > last; i--) {
            if (array[i] > array[last]) {
                nextGreater = i;
                break;
            }
        }
        swap(array, nextGreater, last);
        int l = last + 1, r = array.length - 1;
        while (l < r) {
            int temp = array[l];
            array[l++] = array[r];
            array[r--] = temp;
        }
        return true;
    }

    // Поменять местами строки в массиве
    private void swap(int[] array, int line1, int line2) {
        int tmp = array[line1];
        array[line1] = array[line2];
        array[line2] = tmp;
    }

    // Переставить матрицу на основе короткой матрицы
    private void createNewArray() {
        double[][] tmp = new double[size][];
        for (int i = 0; i < size; i++) {
            tmp[i] = new double[size + 1];
            tmp[i] = matrix[ints[i]];
        }
        matrix = tmp;
    }


    public void proverka(int x) {
        if (x == 0) solution(false);
        if (x == 1) solution(true);
        if (x == 2) System.out.println("Решений нет");
    }
}
