package jp.co.nirvana0rigin.dice;


import java.util.ArrayList;
import java.util.Collections;

public class Counter {

    public Counter(){}

    public int[] getRandom(){
        int[] r = new int[4];
        ArrayList list = new ArrayList();
        for ( int i = 1; i < 7; i++ ) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for( int i = 0; i < 4; i++ ) {
            r[i] =((Integer)list.get(i)).intValue();
        }
        return r;
    }

    public int[][] getRandamSet(int d){
        int[][] rr = new int[d][4];
        for ( int i = 0; i < d; i++ ) {
            rr[i] = getRandom();
        }
        return rr;
    }


}
