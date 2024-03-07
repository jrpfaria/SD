package assignment1;

import assignment1.entities.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");
        ThreadGroup testsGroup = new ThreadGroup("Tests");
        final int nTests = 10;
        Test[] tests = new Test[nTests];
        for (int i = 0; i < nTests; i++) tests[i] = new Test(testsGroup, i);
        for (int i = 0; i < nTests; i++) tests[i].start();
        for (int i = 0; i < nTests; i++) {
            try {
                tests[i].join();
            }
            catch (InterruptedException e) {}
        }
        System.out.println("goodbye world");
    }
}
