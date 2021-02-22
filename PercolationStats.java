
public class PercolationStats {

    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        double [] th = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates())
            {
                int row = edu.princeton.cs.algs4.StdRandom.uniform(1, n + 1);
                int col = edu.princeton.cs.algs4.StdRandom.uniform(1, n + 1);

                p.open(row, col);
            }
            th[i] = (double) p.numberOfOpenSites() / (double) (n * n);
        }

        this.mean = edu.princeton.cs.algs4.StdStats.mean(th);
        this.stdDev = edu.princeton.cs.algs4.StdStats.stddev(th);

        double zIndex = 1.97;
        this.confidenceLo = (mean - (zIndex * stdDev)/ Math.sqrt(trials));
        this.confidenceHi = (mean + (zIndex * stdDev)/ Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
         return confidenceLo;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args)
    {
         int n = Integer.parseInt(args[0]);
         int tests = Integer.parseInt(args[1]);

        edu.princeton.cs.algs4.Stopwatch s = new edu.princeton.cs.algs4.Stopwatch();
        PercolationStats stats = new PercolationStats(n, tests);
        System.out.println("Mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = ["+stats.confidenceLo()+", "+ stats.confidenceHi()+"]");
        System.out.println(s.elapsedTime());

    }

}
