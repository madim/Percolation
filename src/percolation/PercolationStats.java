package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int T;
    private int threshold;
    private double[] anal;

    /*
    * How do I generate a site uniformly at random among all blocked sites for use in PercolationStats?
    * Pick a site at random (by using StdRandom to generate two integers between 1 and n)
    * and use this site if it is blocked; if not, repeat.
    * */

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "PercolationStats must be more than 0");
        }
        this.n = n;
        T = trials;

        threshold = 0;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < trials; i++) {

            do {
                percolation.open(StdRandom.uniform(n), StdRandom.uniform(n));
                threshold++;
            }
            while (!percolation.percolates());

            anal[i] = estimateThreshold();
            threshold = 0;
        }
    }

    private double estimateThreshold() {
        return threshold / n * n;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(anal);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1) return Double.NaN;
        return StdStats.stddev(anal);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }

    /*
    * I don't get reliable timing information in PercolationStats when n = 200. What should I do?
    * Increase the size of n (say to 400, 800, and 1600), until the mean running
    * time exceeds its standard deviation.
    * */

    public static void main(String[] args) {

    }
}