package lab0;

public class Var1 {
    /*
     * Дана відстань L у сантиметрах.
     * Використовуючи операцію поділу націло,
     * визначити кількість повних метрів у ньому (1 метр = 100 см).
     */
    /**
     * Finding integer part of number of meters converted from l centimeters
     *
     * @param l distance in cm
     * @return l in m, integer part
     */
    public int integerNumbersTask(int l) {
        assert l > 0: "Distance <= 0";
        return l / 100;
    }

    /*
     * Дано ціле число A. Перевірити істинність висловлювання: «Число A є додатним»
     */
    /**
     * @param a integer number
     * @return true if a > 0, else false
     */
    public boolean booleanTask(int a) {
        return a > 0;
    }

    /*
     * Дано ціле число.
     * Якщо воно є додатним, додати до нього 1; інакше не змінювати його.
     * Вивести отримане число.
     */
    /**
     * Outputting intNum + 1, if intNum > 0, else output intNum
     *
     * @param intNum integer
     */
    public int ifTask(int intNum) {
        if (intNum > 0) {
            ++intNum;
        }
        return intNum;
    }

    /*
     * Дано ціле число у діапазоні 1–7.
     * Вивести рядок - назву дня тижня, що відповідає даному числу
     * (1 - "понеділок", 2 - "вівторок" і т. д.).
     */
    /**
     * Outputting the name of the weekday corresponding to the number weekdayNumber
     * (1 - Monday, ..., 7 - Sunday)
     * In case when weekdayNumber not in {1, 2, ..., 7} - AssertionError
     *
     * @param weekdayNumber weekday number
     */
    public String switchTask(int weekdayNumber) {
        assert weekdayNumber >= 1 && weekdayNumber <= 7: "Invalid parameter weekdayNumber value";

        String weekdayName = null;
        switch (weekdayNumber)
        {
            case 1: {
                weekdayName = "Monday";
                break;
            }
            case 2: {
                weekdayName = "Tuesday";
                break;
            }
            case 3: {
                weekdayName = "Wednesday";
                break;
            }
            case 4: {
                weekdayName = "Thursday";
                break;
            }
            case 5: {
                weekdayName = "Friday";
                break;
            }
            case 6: {
                weekdayName = "Saturday";
                break;
            }
            case 7: {
                weekdayName = "Sunday";
                break;
            }
        }
        return weekdayName;
    }

    /*
     * Дані цілі числа K і N (N > 0). Вивести N раз число K.
     */
    /**
     * Outputting integer k n times
     *
     * @param k integer
     * @param n integer > 0
     */
    public int[] forTask(int k, int n) {
        assert n > 0: "n should be more than zero";

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = k;
        }
        return result;
    }

    /*
     * Дано додатні числа A і B (A > B).
     * На відрізку довжини A розміщено максимально можливу кількість відрізків довжини B (без накладень).
     * Не використовуючи операції множення та ділення, знайти довжину незайнятої частини відрізка A.
     */
    /**
     * @param a positive integer
     * @param b positive integer, a > b
     * @return the unoccupied part of segment A
     */
    public int whileTask(int a, int b) {
        assert (a > 0 && b > 0): "Arguments should be more than zero";
        assert (a > b): "a <= b";

        while((a - b) >= 0) {
            a -= b;
        }
        return a;
    }


    /*
     * Дано ціле число N (> 0).
     * Сформувати і вивести цілий масив розміру N,
     * що містить N перших позитивних непарних чисел: 1, 3, 5, … .
     */
    /**
     * Outputting and returning first n odd natural numbers
     *
     * @param n integer > 0
     * @return first n odd natural numbers
     */
    public int[] arrayTask(int n) {
        assert n > 0: "n should be more than zero";

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = 2 * i + 1;
        }
        return array;
    }


    /*
     * Дано цілі додатні числа M і N.
     * Сформувати цілочисленну матрицю розміру M на N,
     * у якої всі елементи i-го рядка мають значення 10·i (i = 1, …, M).
     */
    /**
     * @param m positive integer, matrix rows number
     * @param n positive integer, matrix columns number
     * @return matrix m x n where all matrix[i] row elements are equal 10 * i, i = 1, 2, ..., m
     */
    public int[][] twoDimensionArrayTask(int m, int n) {
        assert n > 0 && m > 0: "n <= 0 or m <= 0";

        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = 10 * (i + 1);
            }
        }
        return result;
    }
}
