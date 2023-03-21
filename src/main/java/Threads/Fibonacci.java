package Threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;


/*
However, besides being a dumb way to compute Fibonacci functions (there is a simple fast linear algorithm that you'd use in practice),
 this is likely to perform poorly because the smallest subtasks are too small to be worthwhile splitting up.
 Instead, as is the case for nearly all fork/join applications,
 you'd pick some minimum granularity size (for example 10 here) for which you always sequentially solve rather than subdividing.
 */

public class Fibonacci extends RecursiveTask<Long> {
    final int n;
    private static Map<Integer, Long> _dp = null;

    public Fibonacci(int n) {
        this.n = n;
        if(_dp == null)
            _dp = new HashMap<Integer, Long>();
    }

    public Long compute() {
        if(_dp.containsKey(n))
            return _dp.get(n);
        Long res = 0L;
        if(n > 20) {
            if (n <= 1)
                return n + 0L;
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            res = f2.compute() + f1.join();
        }else{
            res = computeSeq();
        }
        _dp.put(n, res);
        return res;
    }

    public Long computeSeq() {
        if (n <= 1)
            return n + 0L;
        if(_dp.containsKey(n))
            return _dp.get(n);
        Fibonacci f1 = new Fibonacci(n - 1);
        Fibonacci f2 = new Fibonacci(n - 2);
        Long res = f2.computeSeq() + f1.computeSeq();
        _dp.put(n, res);
        return res;
    }

}
