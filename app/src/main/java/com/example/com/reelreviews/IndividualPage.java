package com.example.com.reelreviews;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.w3c.dom.Text;

public class IndividualPage extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private String videoID = Config.video_String;
    private YouTubePlayer m_youTubePlayer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_page_layout);

        //Create Youtube Support Fragment
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubepLAYER_fragment);
        youTubePlayerSupportFragment.initialize(Config.API_KEY, this);


        //List of cast memebers
        String[] castMemebersList = {
                "Amy Poehler - Joy (voice)",
                "Phyllis Smith - Sadness (voice)",
                "Richard Kind - Bing Bong (voice)",
                "Bill Hader - Fear(voice)"
        };

        //Concatinates String array into a single string variable
        String castMembers = TextUtils.join("\n", castMemebersList);

        //Locates the text view to display the cast members
        final TextView castMember = (TextView) findViewById(R.id.cast);
        castMember.setText(castMembers);

        /**
         * Listener for Share button
         * Shares the info about the movie through intents
         * Opens a dialog for the user to choose the platfrom to share
         */

        Button shareButton = (Button) findViewById(R.id.share_Button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Text view object to get the title anme*/
                TextView title = (TextView) findViewById(R.id.title);

                /**
                 * Creating intents to share and population menu
                 */
                Intent optionInflator = null;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, title.getText().toString());
                intent.setType("text/plain");
                optionInflator = Intent.createChooser(intent, "Share");
                startActivity(optionInflator);
            }
        });

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
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            m_youTubePlayer.setFullscreen(true);
        }
    }
}