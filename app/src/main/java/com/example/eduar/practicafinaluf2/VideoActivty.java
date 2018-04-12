package com.example.eduar.practicafinaluf2;

import android.net.Uri;
import android.widget.MediaController;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;

public class VideoActivty extends YouTubeBaseActivity {
    private static final String TAG = "VideoActivity";
    Button playVideoNormal;
    VideoView mVideoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_activty);


        playVideoNormal = findViewById(R.id.btnPlayVideoNormal);
        mVideoView = findViewById(R.id.videoViewNormal);

        //carraguem el video normal
        mediaController = new MediaController(this);
        Uri uriVideo = Uri.parse("android.resource://com.example.eduar.practicafinaluf2/"+R.raw.video_vinya_presentacio);
        mVideoView.setVideoURI(uriVideo);

        mediaController.setAnchorView(this.mVideoView);
        mVideoView.setMediaController(mediaController);

        playVideoNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });

    }



}
