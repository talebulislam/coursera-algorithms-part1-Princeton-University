import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int t_count;
    private double[] t_Result;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be <= 0");
        }
        int gridSize = n;
        t_count = trials;
        t_Result = new double[t_count];

        for (int trial = 0; trial < t_count; trial++) {
            Percolation percolation = new Percolation(gridSize);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                percolation.open(row, col);
            }
            int openSites = percolation.numberOfOpenSites();
            double result = (double) openSites / (gridSize * gridSize);
            t_Result[trial] = result;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(t_Result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(t_Result);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(t_count));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(t_count));
    }

    // test client (described below)
    public static void main(String[] args) {
        int gridSize = 10;
        int t_count = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            t_count = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, t_count);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}