import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

// храним решение в ссыл переменной. на объекте матрицы
public class Gauss {
    private double     epsilon = 0.000001f; // Точность вычислений
    private int        size; // Размер матрицы
    private double[][] matrix; // Наша матрица

    // Инициализация матрицы
    public void init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scanner = new Scanner(file); // Сканнер считывает необходимые для решения данные
        Pattern pattern = Pattern.compile("[ \t]+");
        String string = scanner.nextLine();
        String[] strings = pattern.split(string);
        size = Integer.parseInt(strings[0]); // parseInt() - преобразует строку в число
        create(size);
        for (int i = 0; i < size; i++) {
            string = scanner.nextLine().trim(); //trim - удаляем пробелы
            strings = pattern.split(string); //pattern.split - разделение заданной строки в соответствии с заданным шаблоном
            for (int j = 0; j < size + 1; j++) matrix[i][j] = Double.parseDouble(strings[j].replace(',', '.'));
        }
        scanner.close();
    }

    // Создание самой матрицы
    private void create(int size) {
        matrix = new double[size][];
        for (int i = 0; i < size; i++) matrix[i] = new double[size + 1];
    }

    // Вывод нашей матрицы
    public void print() {
        for (double[] doubles : matrix) {
            for (double d : doubles) System.out.printf("%15.6E", d); // форматированный вывод
            System.out.println();
        }
    }

    // Приведение к треугольному виду
    public int tr() {
        for (int k = 0; k < size; k++) {
            if (Math.abs(matrix[k][k]) <= epsilon) {
                int nonZero = findNonZero(k);
                if ((nonZero != -1)) swap(matrix, k, nonZero);
                else return 1;
            }

            for (int i = k + 1; i < size; i++) calcCoeff(i, k);
        }

        if (Math.abs(matrix[size - 1][size]) <= epsilon) return 1;
        if (Math.abs(matrix[size - 1][size]) <= epsilon && Math.abs(matrix[size - 1][size - 1]) <= epsilon) return 2;
        if (Math.abs(matrix[size - 1][size]) == epsilon) return 5;
        return 0;
    }

    //  Поиск индекса строки с не нулевым элементом
    private int findNonZero(int col) {
        for (int i = col + 1; i < size; i++)
            if (Math.abs(matrix[i][col]) > epsilon) return i;
        return -1;
    }

    // Подсчет получившихся коэф.
    private void calcCoeff(int i, int k) {
        double m = matrix[i][k] / matrix[k][k];
        matrix[i][k] = 0;
        for (int j = k + 1; j < size + 1; j++) {
            matrix[i][j] -= m * matrix[k][j];
            if (Math.abs(matrix[i][j]) <= epsilon) matrix[i][j] = 0;
        }
    }

    // Вывод полученного решения
    private void sol() {
        double[] ret = new double[size];

        for (int i = ret.length - 1; i >= 0; i--) {
            ret[i] = matrix[i][size];
            for (int j = 0; j < ret.length; j++)     // оптимизировать иф внутри 2 цикла
                if (i != j) ret[i] -= matrix[i][j] * ret[j];
            ret[i] /= matrix[i][i];
        }
        printForSol(ret);

//        for (double v : ret) System.out.printf("%15.6E ", v);//////////////-----------------
    }

    public void printForSol(double res[]){
        for (double v : res) System.out.printf("%15.6E ", v);
    }

    // Меняем местами строки
    private void swap(double[][] array, int line1, int line2) {
        double[] tmp = array[line1];
        array[line1] = array[line2];
        array[line2] = tmp;
    }

    public String proverka(int x){
        if (x == 0) sol();
        if (x == 1) return ("Матрица вырожденная");
        if (x == 2) return("Решений бесконечно много");
        if (x == 5) return("Решений нет");
        return null;
    }
}
