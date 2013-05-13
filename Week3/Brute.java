/****************************************************************************
 *  Class Brute
 *    for an assignment of week 3 for course Algorithms Part 1 at the coursera.org
 *  
 *  Copyright (c) 2013 Danil Knysh
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Brute
{   
    public Brute()
    {
    }
    
    private static boolean less(Point p1, Point p2)
    {
        int res = p1.compareTo(p2);
        if (res < 0)
            return true;
        if (res > 0)
            return false;
        return false;
    }
    
    private static void compute(Point[] mas)
    {
        int num = mas.length;
        Point[] tmp = new Point[num];
        for (int i = 0; i < num; i++)
            tmp[i] = mas[i];
        //we have 4 points in tmp
                    double first_slope = tmp[0].slopeTo(tmp[1]);
                    boolean ok = true;
                    for (int k = 2; k < 4; k++)
                    {
                        if (first_slope != tmp[0].slopeTo(tmp[k]))
                        {
                            ok = false;
                            break;
                        }
                    }
                    
                    if (ok)
                    {            
                        for (int k = 0; k < 4; k++)
                        {
                            for (int t = k; t > 0; t--)
                            {
                                if (less(tmp[t], tmp[t-1]))
                                {
                                    Point tmp_point = tmp[t];
                                    tmp[t] = tmp[t-1];
                                    tmp[t-1] = tmp_point;
                                }
                            }
                        }
                        
                        for (int k = 0; k < 4; k++)
                        {
                            if (k > 0)
                                StdOut.print(" -> ");
                            StdOut.print(tmp[k]);
                        }                     
                        StdOut.println("");
                        tmp[0].draw();
                        tmp[0].drawTo(tmp[3]);
                    }
    }
    
    private void exch(Point[] mas, int sTmp, int tmpSize, Point[] all, int sAll, int allSize)
    {
        if (sAll >= allSize || sTmp >= tmpSize)return;
        for (int i = sTmp; i < tmpSize; i++)
        {
            int num = mas.length;
            Point[] tmp = new Point[num];
            for (int j = 0; j < num; j++)
            tmp[j] = mas[j];
            int end = sAll;
            for (int j = end; j < allSize; j++)
            {
                tmp[i] = all[j];
                end++;
                compute(tmp);
                exch(tmp, i+1, tmpSize, all, j+1, allSize);
            }
        }
    }
    
    public static void main(String[] args)
    {
        Point[] points_original;
        int num;
        try {
            BufferedReader file =new BufferedReader(new InputStreamReader(
                                                                          new FileInputStream(args[0])));
            String s;
            s = file.readLine();
            num = Integer.parseInt(s);
            points_original = new Point[num];
            int k = 0;
            while ((s = file.readLine()) != null) 
            {
                if (!s.trim().equals(""))
                {
                String[] point = s.trim().split("\\s+");
                points_original[k++] = new Point(Integer.parseInt(point[0].trim()), 
                                                 Integer.parseInt(point[1].trim()));
                }
            }
            file.close();   
        } catch (FileNotFoundException ex) {
            return;
        } catch (IOException ex) {
            return;
        }
        StdDraw.setXscale(0, 32768);StdDraw.setYscale(0, 32768);
        Point[] tmp = new Point[4];
        for (int i = 0; i < num && i+4 <= num; i++)
        {
            int tmp_size = 0;
            for (int j = i; j < i+4; j++)
            {
                tmp[tmp_size++] = points_original[j];
            }                         
            compute(tmp);
            new Brute().exch(tmp, 1, 4, points_original, i+4, num);            
        }                
    }
}