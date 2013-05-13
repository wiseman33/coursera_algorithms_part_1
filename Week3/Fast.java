/****************************************************************************
 *  Class Fast
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
import java.util.Arrays;
import java.lang.System;
import java.util.List;
import java.util.ArrayList;

public class Fast
{
    public Fast()
    {
    }
    
    public static void main(String[] args)
    {
        Point[] points;
        int num;
        try {
            BufferedReader file =new BufferedReader(new InputStreamReader(
                                                                          new FileInputStream(args[0])));
            String s;
            s = file.readLine();
            num = Integer.parseInt(s);
            points = new Point[num];
            int k = 0;
            while ((s = file.readLine()) != null && k < num) 
            {
                if (!s.trim().equals(""))
                {
                String[] point = s.trim().split("\\s+");
                points[k++] = new Point(Integer.parseInt(point[0].trim()), 
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
        List<List<Point>> lines = new ArrayList<List<Point>>();
        for (int i = 0; i < num; i++)
        {
            int tmpSize = num-i;
            Point[] tmp = new Point[tmpSize];
            for (int j = 0; j < tmpSize; j++)
                tmp[j] = points[j+i];
            Arrays.sort(tmp, tmp[0].SLOPE_ORDER);
            List<Integer> line = new ArrayList<Integer>();
            for (int j = 1; j < tmpSize - 1; j++)
            {
                double slope = tmp[0].slopeTo(tmp[j]);
                if (slope == tmp[0].slopeTo(tmp[j+1]))
                {
                    line.add(j);
                    for (int k = j + 1; k < tmpSize; k++)
                    {
                        if (slope == tmp[0].slopeTo(tmp[k]))
                        {
                            line.add(k);                            
                        } else
                        {
                            break;
                        }
                    }
                    if (line.size() > 2)
                    {                        
                        //break;                        
                        int len = line.size() + 1;                                                
                        j = line.get(len-2);
                        Point[] out = new Point[len];                
                        for (int l = 0; l < len - 1; l++)
                            out[l] = tmp[line.get(l)];
                        out[len-1] = tmp[0];
                        Arrays.sort(out);
                        
                        boolean was = false;
                        for (List<Point> segment : lines)
                        {
                            if ((segment.get(1).compareTo(out[len-1]) == 0 ||
                                 segment.get(0).compareTo(out[0]) == 0)&&
                                segment.get(0).slopeTo(segment.get(1)) ==                               
                                out[0].slopeTo(out[len-1]))
                            {
                                was = true;
                                break;
                            }
                                
                        }
                        if (!was)
                        {
                            StdOut.print(out[0]);
                            for (int t = 1; t < len; t++)
                            {                
                                StdOut.print(" -> ");
                                StdOut.print(out[t]);
                            }
                            out[0].draw();
                            out[0].drawTo(out[len-1]);
                            StdOut.println("");
                            
                            List<Point> one_segment = new ArrayList<Point>();
                            one_segment.add(out[0]);
                            one_segment.add(out[len-1]);
                            lines.add(one_segment);
                        }
                        line.clear();                        
                    } else
                    {
                        line.clear();
                    }
                }
            }            
        }       
    }
}