/****************************************************************************
 *  Class RandomizedQueue
 *    for an assignment of week 1 for course Algorithms Part 1 at the coursra.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()           // construct an empty randomized queue
   {
       size_ = 0;
       realSize_ = bound;//for the begining
       first_ = last_ = 0;
       data_ = (Item[]) new Object[realSize_];
       for (int i = 0; i < realSize_; i++)
           data_[i] = null;
   }
   
   public boolean isEmpty()           // is the queue empty?
   {
       return size_ == 0;
   }
   
   public int size()                  // return the number of items on the queue
   {
       return size_;
   }
   
   public void enqueue(Item item)     // add the item
   {
       if (item == null)
           throw new NullPointerException();
       
       size_++;
       if (first_ != -1)
       {
           //add to the begining of array;
           if (last_ == 0) last_++;
           data_[first_--] = item;
       }else
       {
           if (last_ < realSize_) //we have enough memory
           {
               data_[last_++] = item;
           }
           else//need to encrease our array
           {               
               Item[] new_array = (Item[]) new Object[realSize_*2];
               for (int i = 0; i < realSize_; i++)
               {
                   new_array[i] = data_[i];
                   data_[i] = null;
               }
               int new_real_size = realSize_*2;
               for (int i = realSize_; i < new_real_size; i++)
                   new_array[i] = null;
               realSize_ = new_real_size;
               data_ = new_array;
               
               data_[last_++] = item;
           }
       }
   }
   
   public Item dequeue()              // delete and return a random item
   {
       if (isEmpty())
           throw new NoSuchElementException();
       
       size_--;
       int num = StdRandom.uniform(first_+1, last_);
       Item ret = data_[num];
       if (num - first_ < last_ - num)
       {
           for (int i = num-1; i > first_; i--)
               data_[i+1] = data_[i];
           if (first_ != -1)
               data_[first_] = null;
           data_[++first_] = null;
       }else
       {
           for (int i = num+1; i < last_; i++)
               data_[i-1] = data_[i];               
           if (last_ < realSize_)
               data_[last_] = null;
           data_[--last_] = null;
       }
       
       //chack do we need to decrease array size
       if (size_ < realSize_/3 && realSize_/3 > bound)
       {
           Item[] new_array = (Item[]) new Object[realSize_/3];
           int j = 0;
           for (int i = first_+1; i < last_; i++)
           {
               new_array[j++] = data_[i];
               data_[i] = null;
           }
           int new_real_size = realSize_/3;
           for (int i = j; i < new_real_size; i++)
                   new_array[i] = null;
           realSize_ = new_real_size;
           data_ = new_array;
           first_ = -1;
           last_ = j;
       }
       
       return ret;
   }
   
   public Item sample()               // return (but do not delete) a random item
   {
       if (isEmpty())
           throw new NoSuchElementException();
       
       int num = StdRandom.uniform(first_+1, last_);
       return data_[num];
   }
   
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {
       return new ArrayIterator();
   }
       
   private class ArrayIterator implements Iterator<Item>
   {
       private int current = 0;//first_ + 1;
       private int[] indexes;
       
       public ArrayIterator()
       {
           indexes = new int[size_];
           int i = 0;
           boolean found;
           do{
               found = false;
               int ind = StdRandom.uniform(first_+1, last_);
               for (int j = 0; j < i; j++)
                   if (indexes[j] == ind)
                   {
                       found = true;
                       break;
                   }
               if (!found)
                   indexes[i++] = ind;
           }while(i < size_);
       }
       
       public boolean hasNext()
       {
           return current < size_;//last_;
       }
       
       public void remove()
       {
           throw new UnsupportedOperationException();
       }
       
       public Item next()
       {
           if (current >= size_)
               throw new NoSuchElementException();
                      
           Item item = data_[indexes[current++]];           
           return item;
       }
   }
   
   private int size_;
   private int realSize_;
   private int first_;//first empty element
   private int last_;//last empty element
   private Item[] data_;
   private static int bound = 10;
   
   public static void main(String[] args)
   {
       RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();      
       Stopwatch time = new Stopwatch();
       double start = time.elapsedTime();
       for (int i = 0; i < 256000; i++)
            test.enqueue(i);
       for (int i = 0; i < 256000; i++)
            test.dequeue();
       StdOut.println(time.elapsedTime() - start);          
   }
}