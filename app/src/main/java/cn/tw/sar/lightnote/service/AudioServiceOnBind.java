package cn.tw.sar.lightnote.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import cn.tw.sar.lightnote.bean.Candidates;
import cn.tw.sar.lightnote.bean.LrcEntity;
import cn.tw.sar.lightnote.bean.LyricBean;
import cn.tw.sar.lightnote.bean.LyricDetailBean;
import cn.tw.sar.lightnote.bean.MusicBean;
import cn.tw.sar.lightnote.bean.SongList;
import cn.tw.sar.lightnote.dao.MusicApi;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AudioServiceOnBind extends Service implements MediaPlayer.OnCompletionListener{

    private final IBinder binder = new AudioBinder();
    private MediaPlayer mediaPlayer;
    private String  musicUrl = null;
    private int musicId = 0;
    private ArrayList<SongList> songLists = new ArrayList<>();

    private static final String TAG = "AudioServiceOnBind";

    private List<LrcEntity> lrcList = new ArrayList<>();

    public AudioServiceOnBind() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(AudioServiceOnBind.this.getClass().getName(),"执行onStartCommand()");
        this.musicId = intent.getIntExtra("musicId",0);
        String songsStr = intent.getStringExtra("songs");
        if (songsStr != null){
            Gson gson = new Gson();
            SongList[] songs = gson.fromJson(songsStr, SongList[].class);
            songLists.addAll(Arrays.asList(songs));
        }
        try{
            if (mediaPlayer==null){
                play();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        Log.d(AudioServiceOnBind.this.getClass().getName(),"执行onStartCommand()");


        return Service.START_STICKY_COMPATIBILITY;
    }
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(AudioServiceOnBind.this.getClass().getName(),"执行onCreate()");

    }



    @Override
    public void onDestroy(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        stopForeground(true);

        Log.d(AudioServiceOnBind.this.getClass().getName(),"执行onDestroy()");
    }

    public int getProgress(){
        if (mediaPlayer != null){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }
    public boolean isPlayEnd(){
        if (mediaPlayer != null){
            return mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration();
        }
        return false;
    }
    public void setMusicId(int musicId){
        this.musicId = musicId;
        musicUrl = null;
        play();
    }

    public int getDuration(){
        if (mediaPlayer != null){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public boolean isPlaying(){
        if (mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getMusicId(){
        return musicId;
    }

    public void prev(){
        musicId--;
        if (musicId < 0){
            musicId = songLists.size() - 1;
        }
        musicUrl = null;
        play();
    }
    public void next(){
        musicId++;
        if (musicId >= songLists.size()){
            musicId = 0;
        }
        musicUrl = null;
        play();
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public void setList(ArrayList<SongList> songLists){
        this.songLists = songLists;
        Log.d(AudioServiceOnBind.this.getClass().getName(),"执行setList() ${songLists}");
    }

    public String getLyric(
        long currentTime
    ){
        // 获取当前时间的歌词
        for (int i = lrcList.size()-1; i >= 0; i--) {

            if (currentTime >= lrcList.get(i).getTimeLong() ) {
                return lrcList.get(i).getText();
            }
        }
        return "";

    }
    public void play() {
        if (musicUrl == null ) {
            if (songLists.size() == 0){
                return;
            }
            Retrofit retrofit =  new Retrofit.Builder()
                    //设置网络请求BaseUrl地址
                    .baseUrl("https://music.wxd2zrx.top")
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MusicApi api = retrofit.create(MusicApi.class);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        MusicBean resp = api.getSongUrl(songLists.get(musicId).getHash()).execute().body();
                        if (resp != null) {
                            musicUrl = resp.getUrl().get(0);
                            mediaPlayer = MediaPlayer.create(AudioServiceOnBind.this, Uri.parse(musicUrl));
                            mediaPlayer.start();
                            Response<LyricBean> lyricBean2 = api.getLyrics(songLists.get(musicId).getHash()).execute();
                            LyricBean lyricBean = lyricBean2.body();
                            if (lyricBean != null){
                                Candidates candidates = lyricBean.getCandidates().get(0);
                                Log.d(AudioServiceOnBind.this.getClass().getName(),"执行play() ${candidates.getId()}"+candidates.toString());
                                LyricDetailBean lyricDetailBean = api.getLyric(
                                        candidates.getId(),
                                        candidates.getAccesskey(),
                                        true,
                                        "lrc"
                                ).execute().body();
                                if (lyricDetailBean != null){
                                    lrcList = LrcEntity.parseLrc(lyricDetailBean.getDecodeContent());

                                }

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }else{
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //播放完成
        next();
    }



    //为了和Activity交互，我们需要定义一个Binder对象
    public class AudioBinder extends Binder {

        //返回Service对象
        public AudioServiceOnBind getService(){
            return AudioServiceOnBind.this;
        }
    }
}