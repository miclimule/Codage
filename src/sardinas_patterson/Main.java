package sardinas_patterson;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Vector<String> l = new Vector<>();
        l.add("00");
        l.add("01");
        l.add("110");
        l.add("001");

        // ln
        for (int i = 0; i <= 10; i++) {
            System.out.println(i + " : " + ln(l, i));
        }
        System.out.println("------");

        // get repetition
        System.out.println(Arrays.toString(getRepetition(l)));
        System.out.println("------");

        // is code
        System.out.println(isCode(l));

    }

    public static Vector<String> getResiduel(String u, Vector<String> l) {
        Vector<String> result = new Vector<>();

        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).startsWith(u)) {
                String tmp = l.get(i).substring(u.length());

                if (tmp.equals("")) {
                    tmp = "E";
                }

                result.add(tmp);
            }
        }

        return result;
    }

    public static Vector<String> getQuotient(Vector<String> t, Vector<String> l) {
        Vector<String> result = new Vector<>();
        TreeSet<String> treeSet = null;

        for (int i = 0; i < t.size(); i++) {
            result.addAll(getResiduel(t.get(i), l));
        }

        treeSet = new TreeSet<String>(result);
        result.clear();
        result.addAll(treeSet);

        return result;
    }

    public static Vector<String> ln(Vector<String> l, int n) {
        Vector<String> result = new Vector<>();
        TreeSet<String> treeSet = null;

        if (n == 0) {
            result.addAll(l);
        } else if (n == 1) {
            result = getQuotient(l, l);
            result.removeElement("E");
        } else if (n >= 2) {
            Vector<String> tmp = ln(l, n - 1);
            Vector<String> res1 = getQuotient(l, tmp);
            Vector<String> res2 = getQuotient(tmp, l);

            result.addAll(res1);
            result.addAll(res2);
        }

        treeSet = new TreeSet<String>(result);
        result.clear();
        result.addAll(treeSet);

        return result;
    }

    public static boolean isEqual(Vector<String> vect1, Vector<String> vect2) {
        if (vect1.size() != vect2.size()) {
            return false;
        } else if (vect1.isEmpty() && vect2.isEmpty()) {
            return true;
        } else {
            for (int i = 0; i < vect1.size(); i++) {
                if (vect1.get(i).equals(vect2.get(i))) {
                    return true;
                }
            }
            return false;
        }
    }

    public static int[] getRepetition(Vector<String> l) {
        int[] result = new int[2];
        Vector<Vector<String>> vectPrevious = new Vector<>();
        vectPrevious.add(ln(l, 0));
        int i = 1;

        while (true) {
            Vector<String> tmp = ln(l, i);
            for (int j = 0; j < vectPrevious.size(); j++) {
                if (isEqual(tmp, vectPrevious.get(j))) {
                    result[0] = i;
                    result[1] = j;

                    return result;
                }
            }
            vectPrevious.add(ln(l, i));
            i++;
        }

    }

    public static boolean isCode(Vector<String> l) {
        int[] repetition = getRepetition(l);

        for (int i = 0; i < repetition[0]; i++) {

            if (ln(l, i).contains("E")) {
                return false;
            }
        }

        return true;
    }

}
