package lab0Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lab0.Var1;

public class Var1Tests {
    // integerNumbersTask
    // valid l
    @Test(dataProvider = "integerProvider")
    public void inputTest(int l) {
        assertEquals(new Var1().integerNumbersTask(l), l / 100);
    }

    @DataProvider
    public Object[] integerProvider() {
        return new Object[] { 1, 10, 37, 134, 5070 };
    }

    // invalid l
    @Test(dataProvider = "badIntegerProvider", expectedExceptions = AssertionError.class)
    public void negativeInputTest(int badL) {
        new Var1().integerNumbersTask(badL);
    }

    @DataProvider
    public Object[] badIntegerProvider() {
        return new Object[] { -537, -1, 0 };
    }


    // booleanTask
    // a > 0
    @Test
    public void positiveNumberTest() { assertTrue(new Var1().booleanTask(10)); }

    // a <= 0
    @Test(dataProvider = "notPositiveNumbersProvider")
    public void notPositiveNumberTest(int notPositiveNumber) { assertFalse(new Var1().booleanTask(notPositiveNumber)); }

    @DataProvider
    public Object[] notPositiveNumbersProvider() { return new Object[] {0, -1, -10};}


    // ifTask
    @Test(dataProvider = "ifTaskIntegersProvider")
    public void ifTaskTest(int a, int expectedResult) { assertEquals(new Var1().ifTask(a), expectedResult); }

    @DataProvider
    public Object[][] ifTaskIntegersProvider() {
        return new Object[][] { {-10, -10}, {-1, -1}, {0, 0}, {1, 2}, {10, 11} };
    }


    // switchTask
    // weekday number is not in {1, 2, ..., 7}
    @Test(dataProvider = "badWeekdayNumberProvider", expectedExceptions = AssertionError.class)
    public void switchTaskBadWeekdayNumberTest(int badWeekdayNumber) {
        new Var1().switchTask(badWeekdayNumber);
    }

    @DataProvider
    public Object[] badWeekdayNumberProvider() { return new Object[] {-100, -1, 0, 8, 9, 100}; }

    // weekday number is in {1, 2, ..., 7}
    @Test(dataProvider = "validWeekdayNumberProvider")
    public void validWeekdayNumberTest(int validWeekdayNumber, String expectedResult) {
        assertEquals(new Var1().switchTask(validWeekdayNumber), expectedResult);
    }

    @DataProvider
    public Object[][] validWeekdayNumberProvider() { return new Object[][] {
            {1, "Monday"}, {3, "Wednesday"}, {7, "Sunday"}};
    }


    // forTask
    // n <= 0
    @Test(dataProvider = "badNProvider", expectedExceptions = AssertionError.class)
    public void forTaskBadNTest(int badN) {
        new Var1().forTask(10, badN);
    }

    @DataProvider
    public Object[] badNProvider() { return new Object[] {-100, -1, 0}; }

    // n > 0
    @Test(dataProvider = "forTaskValidArgsProvider")
    public void forTaskValidNTest(int k, int validN, int[] expectedResult) {
        assertEquals(new Var1().forTask(k, validN), expectedResult);
    }

    @DataProvider
    public Object[][] forTaskValidArgsProvider() {
        return new Object[][] {
                { -10, 5, new int[] {-10, -10, -10, -10, -10} },
                { 0, 2, new int[] {0, 0} },
                { 1, 1, new int[] {1} },
        };
    }


    // whileTask
    // a <= 0 or b <= 0 or b <= a
    @Test(dataProvider = "whileTaskBadArgumentsProvider", expectedExceptions = AssertionError.class)
    public void whileTaskBadArgumentsTest(int badA, int badB) {
        new Var1().whileTask(badA, badB);
    }

    @DataProvider
    public Object[][] whileTaskBadArgumentsProvider() {
        return new Object[][] {
                {-10, -10}, {-10, 0}, {0, -10}, {0, 0},
                {-10, 10}, {0, -10}, {10, -10}, {10, 0},
                {0, 10}, {1, 10}, {10, 10}
        };
    }

    // a > b > 0
    @Test(dataProvider = "whileTaskValidArgumentsProvider")
    public void whileTaskValidArgumentsTest(int validA, int validB, int result) {
        assertEquals(new Var1().whileTask(validA, validB), result);
    }

    @DataProvider
    public Object[][] whileTaskValidArgumentsProvider() {
        return new Object[][] {
                {20, 10, 0}, {25, 10, 5}, {2, 1, 0}, {15, 1, 0}, {15, 2, 1}, {16, 2, 0}
        };
    }


    // arrayTask
    // invalid n
    // badNProvider from forTask
    @Test(dataProvider = "badNProvider", expectedExceptions = AssertionError.class)
    public void arrayTaskBadNTest(int badN) {
        new Var1().arrayTask(badN);
    }

    // valid n
    @Test(dataProvider = "arrayTaskValidNWithArrayProvider")
    public void arrayTaskValidNTest(int n, int[] expectedArr) {
        assertEquals(new Var1().arrayTask(n), expectedArr);
    }

    @DataProvider
    public Object[][] arrayTaskValidNWithArrayProvider() { return new Object[][] {{3, new int[] {1, 3, 5}}}; }


    // twoDimensionArrayTask
    // invalid arguments (m <= 0 || n <= 0)
    @Test(dataProvider = "twoDimensionArrayTaskInvalidArgumentsProvider", expectedExceptions = AssertionError.class)
    public void twoDimensionArrayTaskInvalidArgumentsTest(int invalidM, int invalidN) {
        new Var1().whileTask(invalidM, invalidN);
    }

    @DataProvider
    public Object[][] twoDimensionArrayTaskInvalidArgumentsProvider() {
        return new Object[][] {
                {-10, -10}, {-10, 0}, {0, -10}, {0, 0},
                {-10, 10}, {0, -10}, {10, -10}, {10, 0},
        };
    }

    // valid arguments (m > 0 && n > 0)
    @Test
    public void twoDimensionArrayTaskValidArgumentsTest() {
        int[][] expectedMatrix = { {10, 10, 10}, {20, 20, 20} };
        assertEquals(new Var1().twoDimensionArrayTask(2, 3), expectedMatrix);
    }
}
