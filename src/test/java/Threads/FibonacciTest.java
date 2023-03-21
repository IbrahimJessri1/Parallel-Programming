package Threads;

import junit.framework.TestCase;


public class FibonacciTest extends TestCase {

    int n=1000;

    public void testFiboPP(){
        long start = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci(n);
        Long res = fib.compute();
        long end = System.currentTimeMillis()-start;
        System.out.printf("Fibonacci for %d is %d, and parallel execution took %d ms\n",n,res,end);
//        Fibonacci for 40 is 102334155, and sequential execution took 1271 ms before enhancement 1
    }

    public void testFiboSeq(){
        long start = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci(n);
        Long res = fib.computeSeq();
        long end = System.currentTimeMillis()-start;
        System.out.printf("Fibonacci for %d is %d, and sequential execution took %d ms\n",n,res,end);
//        Fibonacci for 40 is 102334155, and parallel execution took 761 ms before enhancement 1
    }
}
