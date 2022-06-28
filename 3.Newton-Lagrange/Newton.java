public class Newton {
    private Polynomial polynomial = new Polynomial(0, 0);

    public Newton(Net setka) {
        int size = setka.y.length;
        double[] diff = new double[size]; //разницы
        double[] coeffs = new double[size]; //коэффициенты

        for (int i = 0; i < size; i++) diff[i] = setka.y[i];
        coeffs[0] = diff[0];
        for (int i = size - 1; i > 0; i--) { //циклы созданные по формулам с лекций
            for (int j = 0; j < i; j++) {
                double chislitel = diff[j] - diff[j + 1];
                double znamenatel = setka.x[j] - setka.x[j + size - i];
                diff[j] = chislitel / znamenatel;
            }
            coeffs[size - i] = diff[0];
        }

        //разделенные разности отдельным методом
        Polynomial skobka;
        Polynomial pol1 = new Polynomial(1, 0);
        Polynomial pol2;
        skobka = new Polynomial(coeffs[0], 0);
        polynomial.sum(skobka);
        skobka.sum(new Polynomial(1, 1));
        for (int i = 1; i < size; i++) {
            skobka = new Polynomial(1, 1); // (Х - ...)
            skobka.sum(new Polynomial(-setka.x[i - 1], 0)); // меняем свободный член в скобке (X - X0), потом (X - X1) и т.д.
            pol1.mul(skobka); // перемножение скобок

            //использовать копирующий конструктор, чтобы не использовать метод копи - сделано
//            pol2 = pol1.copy();
            pol2=new Polynomial(pol1);
            pol2.mul(coeffs[i]);
            polynomial.sum(pol2);
        }
    }

    public void print() {
        polynomial.print();
    }

    public double hornersScheme(double znachenie) {
        return polynomial.hornersScheme(znachenie);
    }
}
