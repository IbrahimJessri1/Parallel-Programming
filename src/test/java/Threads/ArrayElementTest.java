package Threads;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ArrayElementTest extends TestCase {

    private int _size;
    private int _target;
    private ArrayElement _array;
    private static int[] _arr = null;
    private static int _range;


    @Before
    public void setUp() {
        _size = 1000_000_000;
        _range = 100_000;
        _target = 10;
        if(_arr == null)
            _arr = randomArray(_size, _range);
        _array = new ArrayElement(_target, 0, _size - 1);
        _array.setArray(_arr);
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
        _array.computeSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Time execution for Random Array of size %d is %d ms, count of %d is %d\n", _size, endTimer, _target, _array.count);
    }

    @Test
    public void testArrayCountPP() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(_array);
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Time execution for Random Array of size %d is %d ms, count of %d is %d\n", _size, endTimer, _target, _array.count);
    }

    @Test
    public void testArraySumStreamSeq() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        _array.computeStreamSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Stream Time execution for Random Array of size %d is %d ms, count of %d is %d\n", _size, endTimer, _target, _array.count);
    }


    @Test
    public void testArraySumStreamPP() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        long start = System.currentTimeMillis();
        _array.computeStreamPP();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Stream Time execution for Random Array of size %d is %d ms, count of %d is %d\n", _size, endTimer, _target, _array.count);
    }

    public void resource() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool().getParallelism());
    }
}