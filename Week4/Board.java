/****************************************************************************
 *  Class Board
 *    for an assignment of week 4 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class Board {
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        N = blocks.length;
        cells = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                cells[i][j] = blocks[i][j];
                if (blocks[i][j] == 0)
                {
                    rowZero = i;
                    colZero = j;
                }
            }
        }
    }
    
    public int dimension()                 // board dimension N
    {
        return N;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        int hamm = 0;
        int all = N*N;
        for (int i = 0; i < all - 1; i++)
        {
            int row,col;
            row = i / N;
            col = i % N;
            if (cells[row][col] != i+1)
                hamm++;
        }
        return hamm;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manh = 0;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (cells[i][j] != 0)
                {
                    int value; 
                    if (cells[i][j] != i * N + j + 1)
                    {
                        value = cells[i][j];
                        int row,col;
                        row = (value-1) / N;
                        col = (value-1) % N;
                        manh += Math.abs(i - row) + Math.abs(j - col);               
                    }                
                }
            }
        }
        return manh;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        int h = hamming();
        return h == 0;
    }
    
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int priority = 9999;
        Board goal = null;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N-1; j++)
            {
                if (cells[i][j] != 0 && cells[i][j+1] != 0)
                {
                    //can make a twin                    
                    int[][] tmp = new int[N][N];
                    for(int k = 0 ; k < N; k++)
                    {
                        System.arraycopy(cells[k], 0, tmp[k], 0, N);
                    }
                    int copy = tmp[i][j];
                    tmp[i][j] = tmp[i][j+1];
                    tmp[i][j+1] = copy;
                    
                    Board t = new Board(tmp);
                    int new_p = t.hamming() + t.manhattan();
                    if (new_p < priority)
                    {
                        priority = new_p;
                        goal = t;
                    }
                }
            }
        }
        //already made all possible twins
        return goal;
    }
    
    @Override
    public boolean equals(Object y)        // does this board equal y?
    {        
        //Board that = (Board)y;
        if (this == y) return true;
        if (!(y instanceof Board)) return false;
        Board that = (Board)y;
        
        if (this.N != that.N)  return false;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (this.cells[i][j] != that.cells[i][j])
                    return false;
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> all_neighbors = new Queue<Board>();
        
        if (rowZero != 0) //top
        {
             int[][] tmp = new int[N][N];
             for (int k = 0 ; k < N; k++)
             {
                 System.arraycopy(cells[k], 0, tmp[k], 0, N);
             }
             tmp[rowZero][colZero] = tmp[rowZero-1][colZero];
             tmp[rowZero-1][colZero] = 0;
             all_neighbors.enqueue(new Board(tmp));
        }
        
        if (colZero != N-1 ) //right
        {
             int[][] tmp = new int[N][N];
             for (int k = 0 ; k < N; k++)
             {
                 System.arraycopy(cells[k], 0, tmp[k], 0, N);
             }
             tmp[rowZero][colZero] = tmp[rowZero][colZero+1];
             tmp[rowZero][colZero+1] = 0;
             all_neighbors.enqueue(new Board(tmp));
        }
        
        if (rowZero != N-1 ) //bottom
        {
             int[][] tmp = new int[N][N];
             for (int k = 0 ; k < N; k++)
             {
                 System.arraycopy(cells[k], 0, tmp[k], 0, N);
             }
             tmp[rowZero][colZero] = tmp[rowZero+1][colZero];
             tmp[rowZero+1][colZero] = 0;
             all_neighbors.enqueue(new Board(tmp));
        }
        
        if (colZero != 0 ) //left
        {
             int[][] tmp = new int[N][N];
             for (int k = 0 ; k < N; k++)
             {
                 System.arraycopy(cells[k], 0, tmp[k], 0, N);
             }
             tmp[rowZero][colZero] = tmp[rowZero][colZero-1];
             tmp[rowZero][colZero-1] = 0;
             all_neighbors.enqueue(new Board(tmp));
        }
        
        return all_neighbors;
    }
    
    @Override
    public String toString()               // string representation of the board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                s.append(String.format("%2d ", cells[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private int[][] cells;
    private int N;
    private int rowZero;
    private int colZero;
}