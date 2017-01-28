package btb.blindtest.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import btb.blindtest.Data.Music;
import btb.blindtest.Manager.MusicManager;
import btb.blindtest.R;
import btb.blindtest.Utils.ScoreUtils;

public class TestActivity extends Activity {
    MusicManager musicManager;
    TextView tvArtist, tvTitle, tvScore;
    EditText field;
    Button start;
    int distLev = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvArtist = (TextView) findViewById(R.id.textViewArtist);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        start = (Button) findViewById(R.id.button);
        field = (EditText) findViewById(R.id.editText);
        tvScore = (TextView) findViewById(R.id.score);
        field.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                distLev = ScoreUtils.getLevenshteinDistance((field.getText()).toString(),(tvTitle.getText()).toString());
                tvScore.setText("distance : "+distLev);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        musicManager = new MusicManager(getApplicationContext());
        start.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(musicManager.currentMusic == null){
                        new Toast(getApplicationContext()).makeText(getApplicationContext(),
                                "No song in sdcard", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    musicManager.generateRandomSong();
                    Music m = musicManager.getCurrentMusic();
                    tvArtist.setText(m.getArtist());
                    tvTitle.setText(m.getTitle());
                    musicManager.playMusicAtRandomStart(m);
                    return true;
                }
                return false;
            }
        });
        MainTask mt = new MainTask();
        mt.execute();
        try {
            //attendre la fin de l'asynctask
            mt.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private class MainTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //CommunicationManager c = new CommunicationManager("boolshit", 1234, getApplicationContext());
            //c.read();
            //c.write("caca");
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    tvScore.setText("boolshit");
                }
            });

            return null;
        }
    }

}
