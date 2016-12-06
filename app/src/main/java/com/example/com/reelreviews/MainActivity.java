package com.example.com.reelreviews;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class MainActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private String videoID = Config.video_String;
    private YouTubePlayer m_youTubePlayer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the action bar of the app as the custom designed toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //Create Youtube Support Fragment
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubepLAYER_fragment);
        youTubePlayerSupportFragment.initialize(Config.API_KEY, this);


        //List of cast memebers
        String[] castMemebersList  = {
                "Amy Poehler - Joy (voice)",
                "Phyllis Smith - Sadness (voice)",
                "Richard Kind - Bing Bong (voice)",
                "Bill Hader - Fear(voice)"
        };

        //Concatinates String array into a single string variable
        String castMembers = TextUtils.join("\n",castMemebersList);

        //Locates the text view to display the cast members
        TextView castMember = (TextView) findViewById(R.id.cast);
        castMember.setText(castMembers);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        m_youTubePlayer = youTubePlayer;
        if (!b) {
            youTubePlayer.cueVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Error loading", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            m_youTubePlayer.setFullscreen(true);
        }
    }
}