public class Main3 {
    public static void main(String[] args) {
        Net setka = new Net(0, 2, 7);
        setka.print();

        Lagrange lagrange = new Lagrange(setka);
        Newton newton = new Newton(setka);

        System.out.print("Полином Лагранжа: ");
        lagrange.print();
        System.out.print("Полином Ньютона: ");
        newton.print();

        double polovina = (setka.x[1] - setka.x[0]) / 2;
        System.out.print("        X             Y(X)           L(X)           N(X)\n");
        int i = 0;
        for (; i < setka.y.length - 1; i++) {
            System.out.printf("%15.6E%15.6E%15.6E%15.6E\n", setka.x[i], setka.y[i], lagrange.hornersScheme(setka.x[i]), newton.hornersScheme(setka.x[i]));
            System.out.printf("%15.6E%15.6E%15.6E%15.6E\n", setka.x[i] + polovina, Net.func(setka.x[i] + polovina), lagrange.hornersScheme(setka.x[i] + polovina), newton.hornersScheme(setka.x[i] + polovina));
        }
        System.out.printf("%15.6E%15.6E%15.6E%15.6E\n", setka.x[i], setka.y[i], lagrange.hornersScheme(setka.x[i]), newton.hornersScheme(setka.x[i]));
    }
}
