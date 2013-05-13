/****************************************************************************
 *  Class Percolation
 *  	for an assignment of week 1 for course Algorithms Part 1 at the coursra.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class Percolation 
{
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {        
        numSites = N*N;
        dimension = N;
        indexOpenedBottom = new int[N];
        data = new WeightedQuickUnionUF(numSites+1);
        sites = new boolean[numSites];
        for (int i = 0; i < numSites; i++)
            sites[i] = false;
    }
   
    // open site (row i, column j) if it is not already
    public void open(int i, int j) 
    {
        if (i > dimension || j > dimension || i <= 0 || j <= 0)
            throw new IndexOutOfBoundsException();
        
        if (isOpen(i, j))
            return;
        
        sites[(i - 1)*dimension + (j - 1)] = true;        
        
        //top site
        if ((i - 1) > 0 && isOpen(i-1, j) && !data.connected((i - 1)*dimension + (j - 1), (i - 2)*dimension + (j - 1)))
            data.union((i - 1)*dimension + (j - 1), (i - 2)*dimension + (j - 1));
        //right site
        if ((j + 1) <= dimension && isOpen(i, j+1) && !data.connected((i - 1)*dimension + (j - 1), (i - 1)*dimension + j))
            data.union((i - 1)*dimension + (j - 1), (i - 1)*dimension + j);
        //bottom site
        if ((i + 1) <= dimension && isOpen(i+1, j) && !data.connected((i - 1)*dimension + (j - 1), i*dimension + (j - 1)))
            data.union((i - 1)*dimension + (j - 1), i*dimension + (j - 1));
        //left site
        if ((j - 1) > 0 && isOpen(i, j-1) && !data.connected((i - 1)*dimension + (j - 1), (i - 1)*dimension + (j - 2)))
            data.union((i - 1)*dimension + (j - 1), (i - 1)*dimension + (j - 2));
        
        if (i == 1 && !data.connected(j - 1, numSites))
            data.union((j - 1), numSites);
        if (i == dimension)
        {
            indexOpenedBottom[nOpenedAtBottom++] = (i - 1)*dimension + (j - 1);
        }
    }
        
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) 
    {
        if (i > dimension || j > dimension || i <= 0 || j <= 0)
            throw new IndexOutOfBoundsException();
        return sites[(i - 1)*dimension + (j - 1)];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)    
    {
        if (i > dimension || j > dimension || i <= 0 || j <= 0)
            throw new IndexOutOfBoundsException();       
                
        return isOpen(i, j) && data.connected(numSites, (i - 1)*dimension + (j - 1));       
    }
    
    // does the system percolate?
    public boolean percolates()      
    {
        int topHead = data.find(numSites);
        for (int i = 0; i < nOpenedAtBottom; i+=2)
        {
            if (topHead == data.find(indexOpenedBottom[i]))
                return true;
        }
        for (int i = 1; i < nOpenedAtBottom; i+=2)
        {
            if (topHead == data.find(indexOpenedBottom[i]))
                return true;
        }

        return false;
    }      
    
    private int dimension;
    private int numSites;
    private int nOpenedAtBottom = 0;
    private int[] indexOpenedBottom;
    private boolean[] sites;
    private WeightedQuickUnionUF data;
}

