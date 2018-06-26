package com.example.eh.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eh.bakingapp.models.StepItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by Eh on 6/30/2017.
 */

public class SelectedRecipeFragment extends Fragment {
    LinearLayout linearLayout;
    public static final String TAG = SelectedRecipeFragment.class.getSimpleName();
    TextView textView;
    private ArrayList <StepItem> stepItems = new ArrayList<>();
    StepItem stepItem;
    int position;
    Button previous,next;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private ExoPlayer.EventListener exoPlayerEventListener;
    private View rootView;
    static final String Selected_RECIPE = "Selected_step";
    static final String RECIPES_LIST = "Recipes_list";
    static final String RECIPE_POSITION = "Recipes_position";
    private Uri mp4VideoUri;
    public long exoPlayerCurrentPosition = 0;
    private boolean WhenReady;
    public SelectedRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        WhenReady =true;
        Bundle arguments = getArguments();
        if (arguments != null) {
            stepItems= new ArrayList<>();
             rootView = inflater.inflate(R.layout.fragment_selected_recipe, container, false);
            linearLayout = (LinearLayout) rootView.findViewById(R.id.select_layout) ;
            linearLayout.setVisibility(View.VISIBLE);

            stepItems=arguments.getParcelableArrayList(SelectedRecipeFragment.RECIPES_LIST);
            if(savedInstanceState != null){
                //long videoPosition = savedInstanceState.getLong("playerPosition");
                exoPlayerCurrentPosition = savedInstanceState.getLong("exoPlayerCurrentPosition");
                position=savedInstanceState.getInt("ItemPosition");
                stepItem=stepItems.get(position);
                WhenReady = savedInstanceState.getBoolean("WhenReady");
                //mExoPlayer.seekTo(videoPosition);
                Log.e("mExoPlayer. will be",exoPlayerCurrentPosition+");");
            }
            else {
                position = Integer.parseInt(arguments.getString(SelectedRecipeFragment.RECIPE_POSITION));
                stepItem=arguments.getParcelable(SelectedRecipeFragment.Selected_RECIPE);

            }


            textView= (TextView) rootView.findViewById(R.id.textView);
            textView.setText(stepItem.getDescription());
         //   Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
            next= (Button) rootView.findViewById(R.id.button2);
           if(next!=null) {
               next.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       exoPlayerCurrentPosition=0;
                       player.seekTo(exoPlayerCurrentPosition);
           //            Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                       if (position < stepItems.size() - 1) {
                           position++;

                           //    Toast.makeText(getActivity(), stepItems.size()+"", Toast.LENGTH_SHORT).show();

                           textView.setText(stepItems.get(position).getDescription());
                            mp4VideoUri = Uri.parse(stepItems.get(position).getVideoURL());
                           playstream(mp4VideoUri);
                       } else
                           Toast.makeText(getActivity(), "Nothing Next", Toast.LENGTH_SHORT).show();
                   }
               });
           }
            previous= (Button) rootView.findViewById(R.id.button3);
            if(previous!=null) {
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exoPlayerCurrentPosition=0;
                        player.seekTo(exoPlayerCurrentPosition);
                        if (position > 0) {
                            position--;

                            //    Toast.makeText(getActivity(), stepItems.size()+"", Toast.LENGTH_SHORT).show();

                            textView.setText(stepItems.get(position).getDescription());
                            Uri mp4VideoUri = Uri.parse(stepItems.get(position).getVideoURL());
                            playstream(mp4VideoUri);
                        } else
                            Toast.makeText(getActivity(), "Nothing Previous", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            init();


      }




        return rootView;
    }

    void init()
    {
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);

// 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(this.getActivity());
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);

//Set media controller
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        if(exoPlayerCurrentPosition != 0)
            player.seekTo(exoPlayerCurrentPosition);

// Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(WhenReady);

        Uri mp4VideoUri = Uri.parse(stepItem.getVideoURL());
        Log.v("VIDOE URI0",stepItem.getVideoURL());
        playstream(mp4VideoUri);
        // Measures bandwidth during playback. Can be null if not required.
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Savi: ",String.valueOf(exoPlayerCurrentPosition));
        outState.putLong("exoPlayerCurrentPosition", exoPlayerCurrentPosition);
        outState.putInt("ItemPosition", position);
        outState.putBoolean("WhenReady", WhenReady);
    }

    void playstream(Uri mp4VideoUri)
    {
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
//Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeterA);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(), Util.getUserAgent(this.getActivity(), "exoplayer2example"), bandwidthMeterA);

//Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //FOR LIVESTREAM LINK:
        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);


// Prepare the player with the source.
        player.prepare(loopingSource);



        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG,"Listener-onLoadingChanged...");

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG,"Listener-onPlayerStateChanged...");

            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG,"Listener-onTimelineChanged...");

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG,"Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG,"Listener-onPositionDiscontinuity...");

            }
        });
        player.setPlayWhenReady(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG,"onStop()...");
        if(player!=null) {
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG,"onStart()...");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"onResume()...");
        init();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player!=null) {
            releasePlayer();
        }
    }



    private void releasePlayer() {
        try {
            exoPlayerCurrentPosition = player.getCurrentPosition();
            WhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        } catch (Exception e) {

        }
    }


}
