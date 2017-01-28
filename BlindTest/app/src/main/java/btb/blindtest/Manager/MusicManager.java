package btb.blindtest.Manager;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Random;

import btb.blindtest.Data.Music;

/**
 * Created by Tlucien on 25/12/2016.
 */

public class MusicManager {
    ArrayList<Music> listMusic;
    public Music currentMusic;
    MediaPlayer mMediaPlayer;
    Context context;
    int musicCount;
    int randMaxSong = 50000;
    Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("timeractivated");
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
    };

    public MusicManager(Context c) {
        context = c;
        mMediaPlayer = new MediaPlayer();
        handler = new Handler();

        initPhoneMusicList();

    }

    public Music getCurrentMusic() {
        return currentMusic;
    }
    public int getMusicCount(){ return musicCount; }

    private void initPhoneMusicList() {
        listMusic = new ArrayList<>();
        context.getContentResolver();
        Cursor musiccursor;
        int musicTitle;
        int musicArtist;
        int musicFilename;
        String id;
        String[] proj = { MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA};
        musiccursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        musicCount = musiccursor.getCount();
        for(int i=0; i < musicCount; i++){
            musiccursor.moveToPosition(i);
            musicTitle = musiccursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            musicArtist = musiccursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            musicFilename = musiccursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            listMusic.add(new Music(musiccursor.getString(musicTitle), musiccursor.getString(musicArtist),
                    musiccursor.getString(musicFilename)));
        }
    }

    public void generateRandomSong(){
        if(musicCount > 0) {
            Random r = new Random();
            int randomMusicIndex = r.nextInt(musicCount);
            currentMusic = listMusic.get(randomMusicIndex);
        }else{
            currentMusic = null;
        }
    }

    public void playMusicAtRandomStart(Music m){
        Random r = new Random();
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.reset();
            }
            mMediaPlayer.setDataSource(m.getFilename());
            mMediaPlayer.prepare();
            mMediaPlayer.seekTo(r.nextInt(randMaxSong));
            mMediaPlayer.start();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 10000);
        } catch (Exception e) {

        }
    }

}
