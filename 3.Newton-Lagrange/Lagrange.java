public class Lagrange {
    private Polynomial polynomial = new Polynomial(0, 0);

    public Lagrange(Net setka) {
        double y;
        Polynomial pol, skobka;
        for (int i = 0; i < setka.y.length; i++) {
            y = setka.y[i];
            pol = new Polynomial(1, 0);
            for (int j = 0; j < setka.x.length; j++) {
                if (i == j) continue;
                y /= setka.x[i] - setka.x[j];
                // (X - ...)
                skobka = new Polynomial(1, 1);
                // Меняем свободный член в скобке (X - X0), потом (X - X1) и т.д.
                skobka.sum(new Polynomial(-setka.x[j], 0));
                pol.mul(skobka);
            }
            pol.mul(y);
            polynomial.sum(pol);
        }
    }

    public void print() {
        polynomial.print();
    }

    public double hornersScheme(double value) {
        return polynomial.hornersScheme(value);
    }
}
