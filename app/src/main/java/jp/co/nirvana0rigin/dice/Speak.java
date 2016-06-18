package jp.co.nirvana0rigin.dice;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;
import java.util.Locale;


public class Speak extends Sync implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private static final int CHECK_TTS = 8;
    private static int lang = 1;
    /*
    0 : Japanese
    1 : English
    2 : both NG
    3 : Init NG
    */
    private Context con;
    private Resources res;




    //___________________________________________for life cycles

    public Speak() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        checkTTS();
        tts = new TextToSpeech(con, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tts.shutdown();
    }







    //___________________________________________for connection on Activity
	@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        con = context.getApplicationContext();
        res = con.getResources();
    }









    //____________________________________________for work on Fragment

    private void checkTTS() {
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, CHECK_TTS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CHECK_TTS) {
            if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
                String info = con.getString(R.string.info);
                Toast.makeText(con,info, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onInit(int status) {
        String info;
        if (TextToSpeech.SUCCESS == status) {
            Locale locale = Locale.getDefault();
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
                lang = 0;
            } else {
                locale = Locale.ENGLISH;
                if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                    tts.setLanguage(locale);
                    lang = 1;
                    info = con.getString(R.string.info2);
                    Toast.makeText(con, info, Toast.LENGTH_SHORT).show();
                }else{
                    lang = 2;
                    info = con.getString(R.string.info3);
                    Toast.makeText(con, info, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            lang =3;
            info = con.getString(R.string.info4);
            Toast.makeText(con, info, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("deprecation")
    public void speakResult(String result) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            tts.speak(result, TextToSpeech.QUEUE_FLUSH, null, "1");
        } else {
            tts.speak(result, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
	
	public String createTalk(int result){
		String result2 ;
        if (lang == 0) {
            result2 = result + randomText(lang); 
        }else if(lang == 1) {
            result2 = randomText(lang) + result;
        }else{
        	result2 = ("result," + result);
        }
        return result2; 
	}
	
	private String randomText(int lang){
        Counter counter = new Counter();
        int[] ra = counter.getRandom();
        int r = ra[0];
		String randomID = ( "r" + lang )+ (r);
		int randomStringID = res.getIdentifier(randomID, "string", con.getPackageName());
        String randomString = con.getString(randomStringID);
        return randomString;
	}



}
