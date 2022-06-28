import java.io.FileNotFoundException;

public class Main1 {
    public static void main(String[] args) {
        try {
            Gauss gauss = new Gauss();
            gauss.init("C:\\Users\\B1\\Desktop\\выч мат\\1\\input.txt");
            gauss.print();
            System.out.println();
            int x = gauss.tr();
            gauss.print();
            System.out.println();
            gauss.proverka(x);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //отдельно отк.печать решения закр.отдельно подстановку
        // перенести ифы из мейна в новый метод
//        if (x == 0) gauss.sol();
//        if (x == 1) System.out.println("Матрица вырожденная");
//        if (x == 2) System.out.println("Решений бесконечно много");

        // ещё один if
    }
}
