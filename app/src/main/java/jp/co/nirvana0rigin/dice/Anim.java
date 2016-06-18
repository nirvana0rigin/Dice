package jp.co.nirvana0rigin.dice;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;


public class Anim {

    Drawable frame1 ;
    Drawable frame2 ;
    Drawable frame3 ;
    Drawable frame4 ;
    Drawable frame5 ;
    Drawable frame6 ;
    Drawable frame7 ;
    Drawable frame8 ;
    Drawable frame9 ;
    Drawable frame0 ;
    Drawable[] frame = new Drawable[10];
    AnimationDrawable anim;
    View v;
    private Resources res;
    private Context con;


    public Anim(Resources res,Context con,View v){
        this.res = res;
        this.con = con;
        this.v = v;
    }

    //@SuppressWarnings("deprecation")
    public void startAnim(int[] randam) {
        anim = new AnimationDrawable();

        int f0 = res.getIdentifier(("d"+randam[0]), "drawable", con.getPackageName());
        int f7 = res.getIdentifier(("d"+randam[1]), "drawable", con.getPackageName());
        int f8 = res.getIdentifier(("d"+randam[2]), "drawable", con.getPackageName());
        int f9 = res.getIdentifier(("d"+randam[3]), "drawable", con.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            frame1 = con.getDrawable(R.drawable.d1);
            frame2 = con.getDrawable(R.drawable.d2);
            frame3 = con.getDrawable(R.drawable.d3);
            frame4 = con.getDrawable(R.drawable.d4);
            frame5 = con.getDrawable(R.drawable.d5);
            frame6 = con.getDrawable(R.drawable.d6);
            frame7 = con.getDrawable(f7);
            frame8 = con.getDrawable(f8);
            frame9 = con.getDrawable(f9);
            frame0 = con.getDrawable(f0);

        } else {
            frame1 = res.getDrawable(R.drawable.d1);
            frame2 = res.getDrawable(R.drawable.d2);
            frame3 = res.getDrawable(R.drawable.d3);
            frame4 = res.getDrawable(R.drawable.d4);
            frame5 = res.getDrawable(R.drawable.d5);
            frame6 = res.getDrawable(R.drawable.d6);
            frame7 = res.getDrawable(f7);
            frame8 = res.getDrawable(f8);
            frame9 = res.getDrawable(f9);
            frame0 = res.getDrawable(f0);
        }
        Drawable[] frameD = {frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9};
        frame = frameD;

        anim.addFrame(frame1, 50);
        anim.addFrame(frame2, 50);
        anim.addFrame(frame3, 50);
        anim.addFrame(frame4, 50);  //200
        anim.addFrame(frame5, 100);
        anim.addFrame(frame6, 100);
        anim.addFrame(frame1, 100);  //500
        anim.addFrame(frame2, 150);
        anim.addFrame(frame3, 150);  //800
        anim.addFrame(frame4, 200);
        anim.addFrame(frame5, 200);  //1200
        anim.addFrame(frame6, 250);
        anim.addFrame(frame7, 250);  //1700
        anim.addFrame(frame8, 300);
        anim.addFrame(frame9, 350);  //2350
        anim.addFrame(frame0, 350);
        anim.setOneShot(true);
        v.setBackground(anim);
        anim.start();
    }

    public void removeAnim(){
        for(int i = 0; i<10; i++){
            frame[i] = null;
        }
        anim = null;
    }








}
