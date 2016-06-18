package jp.co.nirvana0rigin.dice;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Change extends Sync implements View.OnClickListener{

    private OnChangeListener mListener;
    private View v;
    private Button change;
    private LinearLayout l;
	private Context con;
    private Resources res;
    
    
    
    
    //____________________________________________________________________lifecycle
    public Change() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTHING
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_change, container, false);
        l = (LinearLayout) v.findViewById(R.id.f_change_l);
        change = (Button)v.findViewById(R.id.f_change);
        change.setOnClickListener(this);
        return v;
    }
    
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetChangeButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }









    //_____________________________________________________________________on Flagment
    public void resetChangeButton(){
        if(started) {
            removeButton(); 
        }else {
            addButton();
        }
    }

    public void removeButton(){
        if(l.getChildAt(0) == change){
            l.removeView(change);
        }
    }

    public void addButton(){
        if(l.getChildAt(0) == null) {
        	if(change == null){
        		change = (Button)v.findViewById(R.id.f_change);
        	}
            l.addView(change);
            change.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
    	if(oneMode != 0){
    		oneMode = 0;
    	}else{
        	quantity += 1;
        }
        if(quantity >= 6){
            quantity = 1;
        }
        toActivity();
        onButtonPressed();
    }









    //______________________________________________________________________to Activity
    public interface OnChangeListener {
        // TODO: Update argument type and name
        void onChange();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onChange();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeListener) {
            mListener = (OnChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChangeListener");
        }
        con = context.getApplicationContext();
        res = con.getResources();
    }


}

