package jp.co.nirvana0rigin.dice;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements Change.OnChangeListener,One.OnOneListener,All.OnAllListener,Display.OnDisplayListener,Sync.OnSyncListener {

    private Context con;
    private Resources res;
    private Bundle args;
    private FragmentManager fm;

    public static final String QTY = "quantity";
    public static final String STARTED = "started";
    public static final String DICES = "dices";
    public static final String ONE = "oneMode";
    public static int quantity;
    public static boolean started;
    public static int[] dices = new int[5];
    public static int oneMode;

    private Sync sync;
    private Change change;
    private One one;
    private All all;
    private Display display;
    private Speak speak;
    private Fragment[] f = new Fragment[4];
    private int[] fID = new int[4];
    private String[] fTAG = new String[4];


    //____________________________________________________________________lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        con = getApplicationContext();
        args = savedInstanceState;
        fm = getSupportFragmentManager();

        if (args != null) {
            getFromBundle();
        } else {
            args = new Bundle();
            if (sync != null) {
                getFromSync();
            } else {
                quantity = 1;
                started = false;
                int[] dices2 = {1, 2, 3, 4, 5};
                dices = dices2;
                oneMode = 0;
            }
            putOnBundle();
        }

        int[] fID2 = {R.id.change, R.id.one, R.id.all, R.id.display};
        String[] fTAG2 = {"change", "one", "all", "display"};
        fID = fID2;
        fTAG = fTAG2;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFromBundle();
        createFragments();
        addFragments();
    }


    @Override
    protected void onResume() {
        getFromBundle();
        super.onResume();
    }

    @Override
    protected void onPause() {
        putOnBundle();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle args, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(args, outPersistentState);
        putOnBundle();
    }

    @Override
    protected void onDestroy() {
        putOnBundle();
        super.onDestroy();
    }







    //_____________________________________________________________________on Activity
    private void getFromBundle() {
        quantity = args.getInt(QTY);
        started = args.getBoolean(STARTED);
        dices = args.getIntArray(DICES);
        oneMode = args.getInt(ONE);
    }

    private void putOnBundle() {
        args.putInt(QTY, quantity);
        args.putBoolean(STARTED, started);
        args.putIntArray(DICES, dices);
        args.putInt(ONE, oneMode);
    }

    private void getFromSync() {
        quantity = sync.quantity;
        started = sync.started;
        dices = sync.dices;
        oneMode = sync.oneMode;
    }

    private void createFragments(){
        if(sync == null) {
            sync = Sync.newInstance(quantity, started, dices, oneMode);
        }
        if(speak == null) {
            speak = new Speak();
        }
        if(change == null) {
            change = new Change();
        }
        if(one == null) {
            one = new One();
        }
        if(all == null) {
            all = new All();
        }
        if(display == null) {
            display = new Display();
        }
        Fragment[] f2 = {change,one,all,display};
        f =f2;
    }

    private void addFragments(){
        FragmentTransaction ft = fm.beginTransaction();
        if (!isAlive("sync")){
            ft.add(sync,"sync");
        }
        if (!isAlive("speak")){
            ft.add(speak,"speak");
        }
        for (int i = 0; i < 4; i++) {
            if (!isAlive(fTAG[i])) {
                ft.add(fID[i], f[i], fTAG[i]);
            }
        }
        ft.commit();
    }

    private boolean isAlive(String tag){
        if(fm.findFragmentByTag(tag) == null){
            return false;
        }else{
            return true;
        }
    }

    private void resetAllButton(){
        change.resetChangeButton();
        all.resetStartButton();
        one.resetOneButton();
    }






    //______________________________________________________________________from fragments
    @Override
    public void onAll() {
        display.startAnim();
        resetAllButton();
    }

    @Override
    public void onChange() {
        display.changeDisplay();
        all.resetStartButton();
        if(quantity ==1){
            one.removeButton();
        }else{
            one.addButton();
        }
    }

    @Override
    public void onOne() {
        display.startAnimOne();
        resetAllButton();
    }

	@Override
    public void onDisplay(int sendResult) {
        resetAllButton();
    	String resultPlus = speak.createTalk(sendResult);
    	speak.speakResult(resultPlus);
    }

    @Override
    public void onSync() {
        getFromSync();
        putOnBundle();
    }
    
    
    
}
