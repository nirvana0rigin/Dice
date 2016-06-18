
package jp.co.nirvana0rigin.dice;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;



public class Sync extends Fragment {

    public static final String QTY = "quantity";
    public static final String STARTED = "started";
    public static final String DICES = "dices";
    public static final String ONE = "oneMode";
    public static int quantity;
    public static boolean started;
    public static int[] dices = new int[5];
    public static int oneMode;
    private OnSyncListener mListener;
    private static Bundle args;
    private Context con;
    private Resources res;


    //____________________________________________________________________lifecycle
    public Sync() {
        // Required empty public constructor
    }

    public static Sync newInstance(int q,boolean s,int[] d, int o) {
        quantity = q;
        started = s;
        dices = d;
        oneMode = o; 
        Sync fragment = new Sync();
        args = new Bundle();
        args.putInt(QTY,quantity);
        args.putBoolean(STARTED,started);
        args.putIntArray(DICES,dices);
        args.putInt(ONE,oneMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quantity = getArguments().getInt(QTY);
            started = getArguments().getBoolean(STARTED);
            dices = getArguments().getIntArray(DICES);
            oneMode = getArguments().getInt(ONE);
        }
        //activity再生成時に破棄させないフラグを立てる
        setRetainInstance(true);
    }

	/*______________________viewなし
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change, container, false);
    }
    */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //NOTHING
    }

    @Override
    public void onStart() {
        super.onStart();
        quantity = args.getInt(QTY);
        started = args.getBoolean(STARTED);
        dices = args.getIntArray(DICES);
        oneMode = args.getInt(ONE);
        toActivity();
    }

    @Override
    public void onStop() {
        saveParams();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle args) {
        saveParams();
        super.onSaveInstanceState(args);
    }

    @Override
    public void onDetach() {
        saveParams();
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }





    //_____________________________________________________________________on Flagment
    private void saveParams() {
        args.putInt(QTY, quantity);
        args.putBoolean(STARTED,started);
        args.putIntArray(DICES, dices);
        args.putInt(ONE, oneMode);
        toActivity();
    }






    //______________________________________________________________________to Activity
    public interface OnSyncListener {
        void onSync();
    }

    public void toActivity() {
        if (mListener != null) {
            mListener.onSync();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSyncListener) {
            mListener = (OnSyncListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSyncListener");
        }
        con = context.getApplicationContext();
        res = con.getResources();
    }


}