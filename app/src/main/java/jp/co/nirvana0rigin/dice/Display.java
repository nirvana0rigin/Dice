package jp.co.nirvana0rigin.dice;


import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Display extends Sync {

	private OnDisplayListener mListener;
    private static Bundle args;
    private View v;
    private final static String DICES = "dices";
    //↑ディスプレイ表示数は持っておく
    private ImageView c0;
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;
    private ImageView c4;
    private ImageView[] c = new ImageView[5];
    private TextView result;
    private static int sendResult;
    private Anim[] anim = new Anim[5];
    private Counter counter;
    private static AtEndTimer atEndTimer; 
	private Context con;
    private Resources res;


    //____________________________________________________________________lifecycle
    public Display() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dices = savedInstanceState.getIntArray(DICES);
        }else{
            args = new Bundle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_display, container, false);

        c0 = (ImageView) v.findViewById(R.id.c0);
        c1 = (ImageView) v.findViewById(R.id.c1);
        c2 = (ImageView) v.findViewById(R.id.c2);
        c3 = (ImageView) v.findViewById(R.id.c3);
        c4 = (ImageView) v.findViewById(R.id.c4);
        ImageView[] cD = {c0,c1,c2,c3,c4};
        c = cD;
        result = (TextView) v.findViewById(R.id.result);


        return v;
    }
    
    public void onStart() {
        super.onStart();
        counter = new Counter();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisplay();
    }

    @Override
    public void onStop() {
        atEndTimer = null; 
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }






    //_____________________________________________________________________on Flagment
    @SuppressWarnings("deprecation")
    public void resetDisplay(){
    	int q ;
        sendResult = 0;
    	if(oneMode==0){
    		q = quantity;
    	}else{
    		q = 1;
    	}
        for ( int i = 0; i < 5; i++ ) {
            if(i+1 <= q ) {
                sendResult += dices[i];
                String d;
                if(oneMode != 0){
                    d = "d" + oneMode;
                }else {
                    d = "d" + dices[i];
                }
                int r = res.getIdentifier(d, "drawable", con.getPackageName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    c[i].setBackground(con.getDrawable(r));
                } else {
                    c[i].setBackground(res.getDrawable(r));
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    c[i].setBackground(con.getDrawable(R.drawable.d0));
                } else {
                    c[i].setBackground(res.getDrawable(R.drawable.d0));
                }
            }
        }
        if(oneMode !=0 ){
            sendResult = oneMode;
        }
        if(started){
            //結果表示は”？”のまま
        }else {
            result.setText(String.valueOf(sendResult));
        }
        atEndTimer = null; 
    }
    
    public void changeDisplay(){
        /*
        for ( int i = 0; i < anim.length;i++ ){
            anim[i].removeAnim();
        }
        */
        resetDisplay();
    }

    public void startAnim(){
    	oneMode = 0;
    	result.setText("?");
        int[][] random = counter.getRandamSet(quantity);
        for ( int i = 0; i < quantity; i++ ) {
            dices[i] = random[i][0];
            anim[i] = new Anim( res, con, c[i]);
            anim[i].startAnim(random[i]);
        }
        toActivity();
        atEndTimer = new AtEndTimer(3900,2000);
        atEndTimer.start();
    }

    public void startAnimOne(){
    	oneMode = 1 ;
        result.setText("?");
		resetDisplay();
        int[] random = counter.getRandom();
        oneMode = random[0];
        anim[0] = new Anim( res, con, c[0]);
        anim[0].startAnim(random);
        toActivity();
        atEndTimer = new AtEndTimer(3900,2000);
        atEndTimer.start();
    }
	
	public void atEnd(){
		started = false;
		toActivity();
		resetDisplay();
        atEndToActivity(sendResult);
	}

    class AtEndTimer extends CountDownTimer {

        public AtEndTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            atEnd();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // NOTHING
        }
    }






    //______________________________________________________________________to Activity
    public interface OnDisplayListener {
        void onDisplay(int sendResult);
    }

    public void atEndToActivity(int sendResult) {
        if (mListener != null) {
            mListener.onDisplay(sendResult);
        }
    }
    
	@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayListener) {
            mListener = (OnDisplayListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDisplayListener");
        }
        con = context.getApplicationContext();
        res = con.getResources();
    }






}
