/****************************************************************************
 *  Class Solver
 *    for an assignment of week 4 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class Solver {
    private class Node implements Comparable<Node> {
        public Node(Board current, Node previous, int moves, int priority)
        {
            this.current = current;
            this.previous = previous;
            this.moves = moves;
            this.priority = priority;
        }
        
        @Override
        public int compareTo(Node that)
        {
            if (that.priority > priority)
                return -1;
            if (that.priority < priority)
                return 1;
                
            return 0;
        }
        
        public Board getCurrentBoard()
        {
            return current;
        }
        
        public Node getPreviousNode()
        {
            return previous;
        }
        
        public int getMoves()
        {
            return moves;
        }        
        
        private Board current;
        private Node previous;
        private int moves;
        private int priority;
    }
        
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        initBoard = initial;               
        
        MinPQ<Node> queue1 = new MinPQ<Node>();
        MinPQ<Node> queue2 = new MinPQ<Node>();
        
        queue1.insert(new Node(initBoard, null, 0, initBoard.manhattan() + 0));
        queue2.insert(new Node(initBoard.twin(), null, 0, initBoard.manhattan() + 0));
        
        Node current1 = queue1.delMin();
        Node current2 = queue2.delMin();
        //run
        while (!current1.getCurrentBoard().isGoal() && !current2.getCurrentBoard().isGoal())
        {                       
            Queue<Board> neighbors1 = (Queue<Board>)current1.getCurrentBoard().neighbors();
            Queue<Board> neighbors2 = (Queue<Board>)current2.getCurrentBoard().neighbors();
            
            for (Board b: neighbors1)
            {
                if (current1.getPreviousNode() != null)
                {
                    if (!b.equals(current1.getPreviousNode().getCurrentBoard()))
                            queue1.insert(new Node(b, current1, current1.getMoves()+1, b.manhattan() + current1.getMoves()+1));
                } else
                {
                    queue1.insert(new Node(b, current1, current1.getMoves()+1, b.manhattan() + current1.getMoves()+1));
                }
            }
            
            for (Board b: neighbors2)
            {
                if (current1.getPreviousNode() != null)
                {
                    if (!b.equals(current2.getPreviousNode().getCurrentBoard()))
                            queue2.insert(new Node(b, current2, current2.getMoves()+1, b.manhattan() + b.hamming() + current2.getMoves()+1));
                }
                else
                {
                    queue2.insert(new Node(b, current2, current2.getMoves()+1, b.manhattan() + b.hamming() + current2.getMoves()+1));
                }
            }
            
            current1 = queue1.delMin();
            current2 = queue2.delMin();
        }
        
        if (current1.getCurrentBoard().isGoal())
        {
            solving  = current1;
            solvable = true;
        }
        else if (current2.getCurrentBoard().isGoal())
        {
            solving  = null;
            solvable = false;
        }
    }
    
    public boolean isSolvable()             // is the initial board solvable?
    {
        return solvable;
    }
    
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (solving == null)
            return -1;
        return solving.getMoves();
    }
    
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if (!isSolvable())
            return null;
        
        Stack<Board> solution = new Stack<Board>();
        solution.push(solving.getCurrentBoard());
        Node prev = solving.getPreviousNode();
        while (prev != null)
        {
            solution.push(prev.getCurrentBoard());
            prev = prev.getPreviousNode();
        }
        
        return solution;
    }
    
    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                 blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);        

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
        
    private Board initBoard;
    private boolean solvable;
    private Node solving;
}