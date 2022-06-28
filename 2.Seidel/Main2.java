import java.io.FileNotFoundException;

public class Main2 {
    public static void main(String[] args) {
        try {
            Seidel seidel = new Seidel();
            seidel.init("C:\\Users\\B1\\Desktop\\выч мат\\2\\input.txt");
            seidel.print();
            System.out.println();
            int x = seidel.check();
            seidel.print();
            System.out.println();
            seidel.proverka(x);
//            if (x == 0) seidel.solution(false);
//            if (x == 1) seidel.solution(true);
//            if (x == 2) System.out.println("Решений нет");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
