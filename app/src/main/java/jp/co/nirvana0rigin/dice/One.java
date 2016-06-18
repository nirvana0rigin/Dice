package jp.co.nirvana0rigin.dice;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class One extends Sync implements View.OnClickListener{

    private OnOneListener mListener;
    private View v;
    private Button one;
    private LinearLayout l;
	private Context con;
    private Resources res;
    
    
    
    
    
    //____________________________________________________________________lifecycle
    public One() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_one, container, false);
        l = (LinearLayout) v.findViewById(R.id.f_one_l);
        one = (Button)v.findViewById(R.id.f_one);
        one.setOnClickListener(this);
        return v;
    }
    
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        resetOneButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //_____________________________________________________________________on Flagment
    public void resetOneButton(){
        if(quantity == 1){
            removeButton();
        }else {
            if (started) {
                removeButton();
            } else {
                addButton();
            }
        }
    }

    public void removeButton(){
        if(l.getChildAt(0) == one){
            l.removeView(one);
        }
    }

    public void addButton(){
        if(l.getChildAt(0) == null) {
        	if(one == null){
        		one = (Button)v.findViewById(R.id.f_one);
        	}
            l.addView(one);
            one.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(started){
            started = false;
        }else {
            started = true;
        }
        toActivity();
        onButtonPressed();
    }






    //______________________________________________________________________to Activity
    public interface OnOneListener {
        void onOne();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onOne();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOneListener) {
            mListener = (OnOneListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOneListener");
        }
        con = context.getApplicationContext();
        res = con.getResources();
    }



}
