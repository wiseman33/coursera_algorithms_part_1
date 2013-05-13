/****************************************************************************
 *  Class Deque
 *    for an assignment of week 1 for course Algorithms Part 1 at the coursra.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

import java.util.*;

public class Deque<Item> implements Iterable<Item> {
   public Deque()                     // construct an empty deque
   {
       size_ = 0;
       first_ = last_ = null;
   }
   
   public boolean isEmpty()           // is the deque empty?
   {
       return size_ == 0;
   }
   
   public int size()                  // return the number of items on the deque
   {
       return size_;
   }
   
   public void addFirst(Item item)    // insert the item at the front
   {       
       if (item == null)
           throw new NullPointerException();
    
       size_++;
       
       Node new_node = new Node(item);
       new_node.next_ = first_;
       if (first_ != null)
           first_.prev_ = new_node;
       first_ = new_node;
       if (last_ == null)
           last_ = first_;
   }
   
   public void addLast(Item item)     // insert the item at the end
   {       
       if (item == null)
           throw new NullPointerException();
    
       size_++;
       
       Node new_node = new Node(item);
       new_node.prev_ = last_;
       if (last_ != null)
           last_.next_ = new_node;
       last_ = new_node;       
       if (first_ == null)
           first_ = last_;
   }
   
   public Item removeFirst()          // delete and return the item at the front
   {
       if (isEmpty())
           throw new NoSuchElementException();
       
       size_--;
       
       Item front = first_.data_;       
       first_.data_ = null;
       if (first_.next_ != null)
           first_.next_.prev_ = null;
       first_ = first_.next_;
       
       return front;
   }
   
   public Item removeLast()           // delete and return the item at the end
   {
       if (isEmpty())
           throw new NoSuchElementException();
       
       size_--;
       
       Item end = last_.data_;     
       last_.data_ = null;
       if (last_.prev_ != null)
           last_.prev_.next_ = null;
       last_ = last_.prev_;
       if (isEmpty())
           first_ = last_ = null;
       
       return end;
   }
   
   public Iterator<Item> iterator()   // return an iterator over items in order from front to end
   {
       return new ListIterator();
   }
       
   private class ListIterator implements Iterator<Item>
   {
       private Node current = first_;
       
       public boolean hasNext()
       {
           return current != null;
       }
       
       public void remove()
       {
           throw new UnsupportedOperationException();
       }
       
       public Item next()
       {
           if (current == null)
               throw new NoSuchElementException();
           Item item = current.data_;
           current = current.next_;
           
           return item;
       }
   }
   
   private class Node
   {       
       public Node(Item data)
       {
           next_ = null;
           prev_ = null;
           data_ = data;
       }
       
       private Node next_;
       private Node prev_;
       private Item data_;
   }
   
   private int size_;
   private Node first_;
   private Node last_;
   
   public static void main(String[] args)
   {
       Deque<Integer> test = new Deque<Integer>();
       
       for (int i = 0; i < 50; i+=2)
       {
           test.addFirst(i);       
           test.addLast(i+1);
           for (Integer s : test) StdOut.print(s + " "); StdOut.println("");
           StdOut.println("d: " + test.removeFirst());
           StdOut.println("d: " + test.removeLast());
           for (Integer s : test) StdOut.print(s + " "); StdOut.println("");
       }
       for (Integer s : test) StdOut.print(s + " "); StdOut.println("");                     
   }
}