package com.example.sofarsounds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ReliveActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    static public final String DEVELOPER_KEY = "AIzaSyAXMIPdZE8YBOUHtmP5JXdVhiM1wixCWAE";
    static public final String DEFAULT_VIDEO = "nfO4Xs1HdSU";

    private String videoId = DEFAULT_VIDEO;

    public ReliveActivity(String videoId) {
        if (videoId != null && videoId.length() > 0) {
            this.videoId = videoId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relive);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DEVELOPER_KEY, this);

        final Button twitterButton = (Button) findViewById(R.id.share_twitter);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postTweet();
            }
        });

        final Button facebookButton = (Button) findViewById(R.id.share_facebook);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postFacebook();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.relive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
       youTubePlayer.loadVideo(videoId);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private String getMessage() {
        return "This was an awesome show! http://youtu.be/" + videoId;
    }

    private void postTweet() {
        String originalMessageEscaped = null;
        try {
            originalMessageEscaped = String.format(
                    "https://twitter.com/intent/tweet?source=webclient&text=%s",
                    URLEncoder.encode(getMessage(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (originalMessageEscaped != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(originalMessageEscaped));
            startActivity(i);
        }
    }

    private void postFacebook() {
        Intent normalIntent = new Intent(Intent.ACTION_SEND);
        normalIntent.setType("text/plain");
        normalIntent.putExtra(Intent.EXTRA_TEXT, getMessage());
        startActivity(normalIntent);
    }
}
