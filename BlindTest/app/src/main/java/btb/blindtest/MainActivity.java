package btb.blindtest;

import android.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {
    MusicManager musicManager;
    TextView tvArtist;
    TextView tvTitle;
    Button start;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvArtist = (TextView) findViewById(R.id.textViewArtist);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        start = (Button) findViewById(R.id.button);
        musicManager = new MusicManager(getApplicationContext());
        start.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
    }
}
