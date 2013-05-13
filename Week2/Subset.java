/****************************************************************************
 *  Class Subset
 *    for an assignment of week 1 for course Algorithms Part 1 at the coursra.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

public class Subset {
   public static void main(String[] args)
   {
       if (args.length != 1) 
       {
           StdOut.println("Please input K");
           return;
       }
       int K = Integer.parseInt(args[0]);       
        
       if (K < 0)
       {
           StdOut.println("The K cannot be less or equal 0");
           return;
       }

       RandomizedQueue<String> strings = new RandomizedQueue<String>();
       String[] all = StdIn.readStrings();
       for (int i = 0; i < all.length; i++)
       {
           if (!" ".equals(all[i]))
               strings.enqueue(all[i]/*.replace("\"","").replace(".","").replace(",","")*/);
       }
        
       for (int i = 0; i < K; i++)
           StdOut.println(strings.dequeue());
   }
}