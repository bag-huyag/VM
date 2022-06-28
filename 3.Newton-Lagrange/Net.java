public class Net { //наша сетка
    public double[] x, y;
    // Инициализация сетки
    public Net(double nachalo, double konec, int n) { // n - кол-во шагов
        x = new double[n];
        y = new double[n];

        double shag = (konec - nachalo) / (n - 1);
        x[0] = nachalo;
        x[n - 1] = konec;

        for (int i = 1; i < n - 1; i++) x[i] = x[i - 1] + shag;
        for (int i = 0; i < n; i++) y[i] = func(x[i]);
    }

    // Функция в точке
    public static double func(double x) {
        return Math.sin(x);
    }

    public void print() {
        System.out.print("X: ");
        for (double x1 : x) System.out.printf("%15.6E", x1);
        System.out.print("\nY: ");
        for (double y1 : y) System.out.printf("%15.6E", y1);
        System.out.println();
    }
}
