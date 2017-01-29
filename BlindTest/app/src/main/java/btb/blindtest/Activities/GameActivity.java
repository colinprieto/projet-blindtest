package btb.blindtest.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import btb.blindtest.Data.Music;
import btb.blindtest.Manager.CommunicationManager;
import btb.blindtest.Manager.MusicManager;
import btb.blindtest.R;
import btb.blindtest.Utils.ScoreUtils;

public class GameActivity extends Activity {
    MusicManager musicManager;
    String artist;
    String title;
    int score;
    boolean player = false;
    TextView tv;
    MainTask mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tv = (TextView) findViewById(R.id.textViewScores);
        final EditText et = (EditText) findViewById(R.id.editTextGame);
        Button bValidate = (Button) findViewById(R.id.buttonValidate);
        bValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!artist.equalsIgnoreCase("") && !title.equalsIgnoreCase("")){
                    score = ScoreUtils.getLevenshteinDistance(artist + " " + title, et.getText().toString());
                    if(score < 5){
                        et.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        et.setBackgroundColor(Color.RED);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et.setText("");
                            et.setBackgroundColor(Color.WHITE);
                        }
                    }, 1000);
                }
            }
        });
        tv.setText("");
        musicManager = new MusicManager(getApplicationContext());
        artist = "";
        title = "";
        mt = new MainTask();
        mt.execute();
    }

    private class MainTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            SharedPreferences sharedPref = GameActivity.this.getPreferences(Context.MODE_PRIVATE);
            CommunicationManager c = CommunicationManager.getInstance(getApplicationContext());
            //check bon lancement ...
            String idParty = sharedPref.getString("partyID", "");
            String pseudo = sharedPref.getString("pseudo", "");
            if(idParty.equalsIgnoreCase("") || pseudo.equalsIgnoreCase("")){
                new Toast(getApplicationContext()).makeText(getApplicationContext(),
                        "Error no party selected or pseudo", Toast.LENGTH_LONG).show();
                return false;
            }
            //envoi addresse mac (inscription)
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String macAddress = info.getMacAddress();
            c.write(idParty + "/" + pseudo + "/" + macAddress);
            //check reponse
            String[] responses;
            do {
                responses = c.read()[0].split("/");
            }while (!responses[0].equalsIgnoreCase(idParty));
            if(!responses[1].equalsIgnoreCase("ok")){
                new Toast(getApplicationContext()).makeText(getApplicationContext(),
                        "Server Error", Toast.LENGTH_LONG).show();
                //return false;
            }
            //init de la liste des musiques et envoie du booleen
            int musicCount = musicManager.getMusicCount();
            if(musicCount > 0) {
                c.write(idParty + "/" + pseudo + "/1");
            }else{
                new Toast(getApplicationContext()).makeText(getApplicationContext(),
                        "No song in sdcard", Toast.LENGTH_LONG).show();
                c.write(idParty + "/" + pseudo + "/0");
            }
            //check reponse
            do {
                responses = c.read()[0].split("/");
            }while (!responses[0].equalsIgnoreCase(idParty));
            if(!responses[1].equalsIgnoreCase("ok")){
                new Toast(getApplicationContext()).makeText(getApplicationContext(),
                        "Server Error", Toast.LENGTH_LONG).show();
                return false;
            }
            //TODO ADD A WHILE READ TO WAIT FOR THE BEGINNING OF THE PARTY ?
            while(true) {
                score = 0;
                player = false;
                do {
                    responses = c.read()[0].split("/");
                }while (!responses[0].equalsIgnoreCase(idParty));
                if (responses[1].equalsIgnoreCase(pseudo)) {
                    player = true;
                    musicManager.generateRandomSong();
                    Music m = musicManager.getCurrentMusic();
                    c.write(idParty + "/" + m.getArtist() + "/" + m.getTitle());
                    musicManager.playMusicAtRandomStart(m);
                    //TODO GREY UI AND DISPLAY ANIMATION
                }
                //end of the game
                else if(responses[1].equalsIgnoreCase("endofthegame")) {
                    return true;
                }
                else{
                    player = false;
                    do {
                        responses = c.read()[0].split("/");
                    }while (!responses[0].equalsIgnoreCase(idParty));
                    artist = responses[1];
                    title = responses[2];
                }
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!player) {
                    c.write(idParty + "/" + pseudo + "/" + score);
                }
                final String[] scores = c.read();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        tv.setText(scoreListToString(scores));
                    }
                });
            }
            //return true;
        }

    }

    private String scoreListToString(String[] scores){
        String result = "";
        for(int i = 0 ; i < scores.length ; i++){
            result += scores[i] + "\n";
        }
        return result;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mt.getStatus() == AsyncTask.Status.RUNNING){
            mt.cancel(true);
            CommunicationManager.getInstance(getApplicationContext()).close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO RESTART FROM WHERE WE STOPPED
        /*if(mt.getStatus() == AsyncTask.Status.FINISHED){
            mt.execute();
        }*/
    }
}
