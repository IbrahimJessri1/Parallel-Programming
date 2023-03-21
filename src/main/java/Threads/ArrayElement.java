package Threads;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ArrayElement extends RecursiveAction {
    public long count;
    private static int arr[];
    int lo, hi;
    private int target;
    public static void setArray(int [] arr){
        ArrayElement.arr = arr;
    }
    public ArrayElement(int target, int lo, int hi) {
        this.target = target;
        this.count = 0;
        this.hi = hi;
        this.lo = lo;
    }

    public void computeSeq() {
        for (int i = lo; i <= hi; ++i) {
            if(this.arr[i] == target)
                this.count++;
        }
        return;
    }

    @Override
    protected void compute() {
        if (hi - lo > 1_000_000) {
            int mid = (lo + hi) / 2;
            ArrayElement left = new ArrayElement(target, lo , mid);
            ArrayElement right = new ArrayElement(target, mid + 1, hi);
            left.fork();
            right.compute();
            left.join();
            count = left.count + right.count;
        } else {
            computeSeq();
        }
    }
    public void computeStreamSeq() {
        count = Arrays.stream(arr).asLongStream().filter(x -> x == target).count();
    }
    public void computeStreamPP() {
        count = Arrays.stream(arr).asLongStream().parallel().filter(x -> x == target).count();
    }
}
