package net.sgoliver.android.navigationdrawer;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class PlayerYouTubeFrag extends YouTubePlayerSupportFragment {

    private YouTubePlayer activePlayer;

    public static PlayerYouTubeFrag newInstance(String url) {

        PlayerYouTubeFrag playerYouTubeFrag = new PlayerYouTubeFrag();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        playerYouTubeFrag.setArguments(bundle);
        playerYouTubeFrag.init();
        return playerYouTubeFrag;
    }

    private void init() {

        initialize("AIzaSyCDmMSxc18zjPh3-ZxKTDGo4ughyF8wCxM", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) { }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {
                    activePlayer.loadVideo(getArguments().getString("url"), 0);

                }
                onYouTubeVideoPaused();
            }
        });
    }

    //@Override
    public void onYouTubeVideoPaused() {
        activePlayer.pause();
    }
}
