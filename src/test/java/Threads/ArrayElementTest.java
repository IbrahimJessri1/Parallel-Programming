package Threads;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ArrayElementTest extends TestCase {

    private int size;
    private int target;
    private ArrayElement array;
    private static int[] arr = null;
    private static int range;


    @Before
    public void setUp() {
        size = 1000_000_000;
        range = 100_000;
        target = 10;
        if(arr == null)
            arr = randomArray(size, range);
        array = new ArrayElement(target, 0, size - 1);
        array.setArray(arr);
    }


    private static int[] randomArray(int size, int range) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(range) + 1;
        }
        return arr;
    }

    @Test
    public void testArrayCountSeq() {
        long start = System.currentTimeMillis();
        array.computeSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Time execution for Random Array of size %d is %d ms, count of %d is %d\n", size, endTimer, target, array.count);
    }

    @Test
    public void testArrayCountPP() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(array);
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Time execution for Random Array of size %d is %d ms, count of %d is %d\n", size, endTimer, target, array.count);
    }

    @Test
    public void testArraySumStreamSeq() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        array.computeStreamSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Stream Time execution for Random Array of size %d is %d ms, count of %d is %d\n", size, endTimer, target, array.count);
    }


    @Test
    public void testArraySumStreamPP() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        array.computeStreamPP();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Stream Time execution for Random Array of size %d is %d ms, count of %d is %d\n", size, endTimer, target, array.count);
    }

    public void resource() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool().getParallelism());
    }
}