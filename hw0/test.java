package hw0;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by YASH on 2/2/16.
 */
public class test {
    public static void main(String... a){
        int delay;
        Random r = new Random();
        do {
            double val = r.nextGaussian() * 0.0001 + .5;
            System.out.println(val);
            delay = (int) Math.round(val);
        } while (true);
    }


}

