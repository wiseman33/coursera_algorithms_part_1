/****************************************************************************
 *  Class PercolationStats
 *      for an assignment of week 1 for course Algorithms Part 1 at the coursra.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class PercolationStats 
{
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        
        tresholds = new double[T];
        nTests = T;
        for (int i = 0; i < T; i++)
        {
            Percolation perTest = new Percolation(N);
            int nOpenSites = 0;
            while (!perTest.percolates())
            {
                int k,j;
                do{
                k = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                }while(perTest.isOpen(k,j));
                
                perTest.open(k, j);
                if (perTest.isOpen(k, j))
                    nOpenSites++;
            }
            
            tresholds[i] = (double) nOpenSites / (N*N);            
        }
    }
    
    // sample mean of percolation threshold
    public double mean()                     
    {
        double sum = 0;
        for (int i = 0; i < nTests; i++)
            sum += tresholds[i];
        return (double) sum / nTests;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev()                
    {
        if (nTests == 1)
            return Double.NaN;
        mean = mean();
        double sum = 0;
        for (int i = 0; i < nTests; i++)
            sum += (tresholds[i] - mean) * (tresholds[i] - mean);
        return Math.sqrt((double) sum / (nTests - 1));
    }
    
    // test client, described below
    public static void main(String[] args)   
    {
        if (args.length != 2) 
        {
            StdOut.println("Please input N and T!");
            return;
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
                       
        PercolationStats test = new PercolationStats(N, T);
        test.mean();
        double sigma = test.stddev();
        StdOut.println("mean                    = " + test.mean);
        StdOut.println("stddev                  = " + sigma);
        StdOut.println("95% confidence interval = " + (test.mean - 1.96*sigma / Math.sqrt(test.nTests)) 
                           + ", " + (test.mean + 1.96*sigma / Math.sqrt(test.nTests)));
    }
    
    private double[] tresholds;
    private int nTests;
    private double mean;
}