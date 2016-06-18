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


public class All extends Sync implements View.OnClickListener{

    private OnAllListener mListener;
    private View v;
    private Button all;
    private LinearLayout l;
	private Context con;
    private Resources res;




    //____________________________________________________________________lifecycle
    public All() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_all, container, false);
        l = (LinearLayout) v.findViewById(R.id.f_all_l);
        all = (Button)v.findViewById(R.id.f_all);
        all.setOnClickListener(this);
        return v;
    }
    
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetStartButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    //_____________________________________________________________________on Flagment
    public void resetStartButton(){
        if(started){
            removeButton(); 
        }else {
        	addButton();
        }
    }

    public void removeButton(){
        if(l.getChildAt(0) == all){
            l.removeView(all);
        }
    }

    public void addButton(){
        if(l.getChildAt(0) == null) {
        	if(all == null){
        		all = (Button)v.findViewById(R.id.f_all);
        	}
            l.addView(all);
            all.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        started = true;
        toActivity();
        onButtonPressed();
    }


    //______________________________________________________________________to Activity
    public interface OnAllListener {
        void onAll();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onAll();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAllListener) {
            mListener = (OnAllListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAllListener");
        }
        con = context.getApplicationContext();
        res = con.getResources();
    }


}
