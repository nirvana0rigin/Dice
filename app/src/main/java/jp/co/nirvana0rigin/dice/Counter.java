package jp.co.nirvana0rigin.dice;


import java.util.ArrayList;
import java.util.Collections;

public class Counter {

    public Counter(){}

    public int[] getRandom(){
        int[] r = new int[6];
        ArrayList list = new ArrayList();
        for ( int i = 1; i < 7; i++ ) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for( int i = 0; i < 6; i++ ) {
            r[i] =((Integer)list.get(i)).intValue();
        }
        return r;
    }

    public int[][] getRandamSet(int d){
        int[][] rr = new int[d][6];
        for ( int i = 0; i < d; i++ ) {
            rr[i] = getRandom();
        }
        return rr;
    }


}
