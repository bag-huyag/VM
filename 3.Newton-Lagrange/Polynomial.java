public class Polynomial {
    public static double zero = 0.00000000001f; //задаем точность
    private Uzel head; // максимальная степень, с котрой начинается полином

    // Конструктор с заданием начального монома
    public Polynomial(double znachenie, int stepen) {
        if (Math.abs(znachenie) <= zero) head = null;
        else head = new Uzel(znachenie, stepen);
    }

    public Uzel getHead() {
        return head;
    }


    // копирующий конструктор
    public Polynomial(Polynomial other){

        if (other.getHead() == null){head=null; return;} // если голова равна нулю то возвращаем нулевой полином
        Uzel uzel = other.getHead();
        Uzel tmp1 = new Uzel(uzel.znachenie, uzel.stepen); // tmp1- новая голова связанного списка, нам нужно знать ссылку на голову связанного списка, поэтому tmp1 неизменый
        Uzel tmp2 = tmp1; // начало связанного списка для возврата, tmp2- рабочий узел(моном) он меняется, потому что нам нужно проходить к след. элементу списка

        // проверка существует ли следующий элемент
        while (uzel.next != null) { //проходим по всем элементам списка    while(uzel != null)
            tmp1.next = new Uzel(uzel.next.znachenie, uzel.next.stepen);
            tmp1 = tmp1.next;
            uzel = uzel.next;
        }
        Polynomial copy = new Polynomial(0, 0); //задаем новый полином
        this.head = tmp2; // создаем голову в качестве tmp2
    }

    // Сделать копию этого полинома чтобы использовать в алгоритме Ньютона
//    public Polynomial copy() {
//        if (head == null) return new Polynomial(0, 0); // если голова равна нулю то возвращаем нулевой полином
//        Uzel uzel = head;
//        Uzel tmp1 = new Uzel(uzel.znachenie, uzel.stepen); // tmp1- новая голова связанного списка, нам нужно знать ссылку на голову связанного списка, поэтому tmp1 неизменый
//        Uzel tmp2 = tmp1; // начало связанного списка для возврата, tmp2- рабочий узел(моном) он меняется, потому что нам нужно проходить к след. элементу списка
//
//        // проверка существует ли следующий элемент
//        while (uzel.next != null) { //проходим по всем элементам списка    while(uzel != null)
//            tmp1.next = new Uzel(uzel.next.znachenie, uzel.next.stepen);
//            tmp1 = tmp1.next;
//            uzel = uzel.next;
//        }
//        Polynomial copy = new Polynomial(0, 0); //задаем новый полином
//        copy.head = tmp2; // создаем голову в качестве tmp2
//        return copy;
//    }

    // Вывести полином на экран
    public void print() {
        if (head == null) return;
        Uzel h = head;
        h.print();
        h = h.next; // убираем "+" перед первым мономом, если он есть (плюс)
        while (h != null) {
            if (h.znachenie > 0) System.out.print(" +"); // если значение больше нуля, то перед ним ставим плюс
            h.print();
            h = h.next;
        }
        System.out.println();
    }

    // Прибавление полинома к текущему
    public void sum(Polynomial polynomial) {
        Uzel first = head, second = polynomial.head;
        Uzel novayagolova = null; // значение голове (74 строка)
        Uzel rabochuzel = null; // рабочий узел
        if (second == null) return; // если второй полином равен нулю, то ничего не делаем
        while (first != null || second != null) {
            Uzel newUzel;
            if (first != null && second != null && first.stepen == second.stepen) { // если оба элемента не равны нулю и их степени равны, то сразу их складываем и переходим к следующим
                newUzel = new Uzel(first.znachenie + second.znachenie, first.stepen);
                first = first.next;
                second = second.next;
            } else { // иначе делаем следующее
                if (first != null && (second == null || first.stepen > second.stepen)) { // если первый элемент не равен нулю и второй элемент равен нулю или степень первого больше степени второго, то работаем только с первым
                    newUzel = new Uzel(first.znachenie, first.stepen);
                    first = first.next;
                } else { // иначе работаем только со вторым
                    newUzel = new Uzel(second.znachenie, second.stepen);
                    second = second.next;
                }
            }
            if (Math.abs(newUzel.znachenie) <= zero)
                continue; //если значение нового узла меньше нашего нуля, то ничего не делаем
            if (rabochuzel == null) { // если рабочий узел равен нулю, то novayagolova присваеваем значение нового узла
                novayagolova = newUzel;
                rabochuzel = novayagolova;
            } else { // иначе присваем следующему рабочему узлу значение нового узла
                rabochuzel.next = newUzel;
                rabochuzel = newUzel;
            }
        }
        head = novayagolova;
    }

    // Умножение полинома на текущий
    public void mul(Polynomial polynomial) {
        Uzel first = head, second = polynomial.head;
        Uzel novayagolova = null; // pезультирующий полином
        Uzel tmp = null;
        if (second == null) return;
        while (first != null) { // проходимся по всем узлам первого элемента
            Uzel uzel = second; // "сохраняем вторую скобку"
            while (uzel != null) { // проходимся по всем узлам второго элемента
                Uzel newUzel = new Uzel(first.znachenie * uzel.znachenie, first.stepen + uzel.stepen);
                if (Math.abs(newUzel.znachenie) <= zero) { // если только что созданный элемент равен нулю, то не станем дальше считать
                    uzel = uzel.next;
                    continue;
                }
                if (novayagolova == null) { // если возвратное значение ещё равно нулю, то сразу присваиваем ему значение и продолжаем
                    novayagolova = newUzel;
                    tmp = novayagolova;
                    uzel = uzel.next;
                    continue;
                }
                if (newUzel.stepen > tmp.stepen) {
                    newUzel.next = novayagolova;
                    novayagolova = newUzel;
                } else {
                    if (newUzel.stepen == tmp.stepen) {
                        tmp.znachenie += newUzel.znachenie;
                        if (Math.abs(tmp.znachenie) <= zero) {
                            Uzel u = novayagolova;
                            while (u.next != tmp) u = u.next;
                            u.next = u.next.next;
                        }
                    } else if(tmp != null) { // если степень нового элемента больше степени рабочего узла, то делаем следующее
                        while (tmp.next != null && newUzel.stepen < tmp.next.stepen) tmp = tmp.next; // while (tmp != null) ???????  - исправлено  if'ом в 131
                        if (tmp.next == null) tmp.next = newUzel;
                        else {
                            if (newUzel.stepen == tmp.next.stepen) {
                                tmp.next.znachenie += newUzel.znachenie;
                                if (Math.abs(tmp.next.znachenie) <= zero) tmp.next = tmp.next.next;
                            } else {
                                newUzel.next = tmp.next.next;
                                tmp.next = newUzel;
                            }
                        }
                    }
                }
                tmp = novayagolova;
                uzel = uzel.next;
            }
            first = first.next;
        }
        head = novayagolova;
    }

    // Умножение полинома на число
    public void mul(double znachenie) {
        if (Math.abs(znachenie) <= zero) head = null;
        Uzel uzel = head;
        while (uzel != null) {
            uzel.znachenie *= znachenie;
            uzel = uzel.next;
        }
    }

    // Схема Горнера          //переделать  //проверять не степени, а не кончился ли список - сделано
    public double hornersScheme(double znachenie) {
        // если "голова" равна нулю, то значение в точке тоже равно нулю
        if (head == null) return 0;
        Uzel uzel = head, pred = head;
        double sum = 0;

//        while (uzel != null){//проверять не кончился ли список
        for (int i = uzel.stepen; i >= 0; i--) {
            if (uzel != null) {
                if (pred.stepen - uzel.stepen <= 1) { //если степень предыдущего отличается от степени текущего меньше чем на 1, то ...
                    sum = uzel.znachenie + znachenie * sum;
                    pred = uzel;
                    uzel = uzel.next;
                } else { // иначе ...
                    sum *= sum;
                    pred = uzel;
                }
            } else sum *= znachenie;
        }
        if (Math.abs(sum) <= zero) sum = 0;
        return sum;
    }

    // Моном
    private static class Uzel {
        private double znachenie;
        private int stepen; //степень Х
        private Uzel next; //ссылка на следующий узел

        private Uzel(double znachenie, int stepen) {
            this.znachenie = znachenie;
            this.stepen = stepen;
            this.next = null;
        }

        private void print() {
            if (stepen == 0) System.out.printf(" %.6E", znachenie);
            else if (stepen == 1) System.out.printf(" %.6Ex", znachenie);
            else System.out.printf(" %.6Ex^%d", znachenie, stepen);
        }
    }
}