package com.projectHandClap.youruniv.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.AddRecordActivity;
import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.R;
import com.projectHandClap.youruniv.RecordData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Recorder extends Fragment {
    ViewPagerActivity viewPagerActivity;
    ViewGroup viewGroup;
    LinearLayout layoutRecord;
    DatabaseHelper db;
    Context mContext;

    ConstraintLayout playerSheet;
    BottomSheetBehavior bottomSheetBehavior;

    MediaPlayer mediaPlayer = null;
    boolean isPlaying = false;

    File fileToPlay = null;
    int fileToPlayId;
    String fileToPlayString;

    ImageButton playBtn;
    TextView playerHeader;
    TextView playerFilename;

    SeekBar playerSeekbar;
    Handler seekbarHandler;
    Runnable updateSeekbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_recorder, container,false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        init();

        return viewGroup;
    }

    public void init(){
        Button fab = viewGroup.findViewById(R.id.btn_add_record);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddRecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        playerSheet = viewGroup.findViewById(R.id.player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);

        playBtn = viewGroup.findViewById(R.id.ibtn_player_play_button);
        playerHeader = viewGroup.findViewById(R.id.player_header_title);
        playerFilename = viewGroup.findViewById(R.id.player_filename);

        playerSeekbar = viewGroup.findViewById(R.id.player_seekbar);
        layoutRecord = viewGroup.findViewById(R.id.layout_record);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        playBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    pauseAudio();
                }else{
                    if(fileToPlay!=null){
                        resumeAudio();
                    }
                }
            }
        });

        playerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
                resumeAudio();
            }
        });

        setLayout();
    }

    public void setLayout(){
        layoutRecord.removeAllViews();
        ArrayList<RecordData> recordData = db.getRecord();
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2 =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
        Typeface typeface = getResources().getFont(R.font.font);

        long nowtime = 0;
        for(RecordData r : recordData){
            long t = r.record_time%1000000;
            long d = (r.record_time/1000000);

            long year = d/10000;
            long month = (d%10000)/100;
            long day = (d%10000)%100;

            String sd = String.valueOf(year)+"년 "+String.valueOf(month)+"월 "+String.valueOf(day)+"일";

            final RecordData fr = r;
            TextView txvDate = null;

            if(nowtime != d){
                nowtime = d;
                txvDate = new TextView(mContext);

                txvDate.setText(String.format(sd));
                txvDate.setLayoutParams(layoutParams);
                txvDate.setTextSize(15);
                txvDate.setTypeface(typeface);
                txvDate.setPadding(30,50,15, 0);
                layoutRecord.addView(txvDate);
            }
            LinearLayout ll = new LinearLayout(mContext);
            ll.setLayoutParams(layoutParams);

            TextView txvTitle = new TextView(mContext);
            txvTitle.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    2f));
            txvTitle.setText(r.record_title);
            txvTitle.setTypeface(typeface);
            txvTitle.setPadding(100,15,15,0);

            Button btnSelect = new Button(mContext);
            btnSelect.setTypeface(typeface);
            btnSelect.setText("재생");
            btnSelect.setBackgroundColor(Color.TRANSPARENT);
            btnSelect.setPadding(0, 15, 10, 0);
            btnSelect.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    3f));
            btnSelect.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fileToPlayString = fr.record_file_path;
                    File tempFile = new File(fr.record_file_path);
                    playFunc(tempFile);
                }
            });

            Button btnDelete = new Button(mContext);
            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    3f));
            btnDelete.setText("삭제");
            btnDelete.setBackgroundColor(Color.TRANSPARENT);
            btnDelete.setPadding(0,15,10,0);
            btnDelete.setTypeface(typeface);
            btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fileToPlayString.equals(fr.record_file_path)){
                        initPlayer();
                    }
                    db.deleteRecord(fr.record_id);
                    setLayout();
                }
            });

            ll.addView(txvTitle);
            ll.addView(btnSelect);
            ll.addView(btnDelete);
            layoutRecord.addView(ll);
        }
    }

    public void initPlayer(){
        mediaPlayer.stop();
        fileToPlay = null;
        fileToPlayString = null;
        playerHeader.setText("Stopped");
        playerFilename.setText("-");
        playBtn.setImageDrawable(getResources().getDrawable(R.drawable.player_play_btn, null));
        isPlaying = false;
        seekbarHandler.removeCallbacks(updateSeekbar);
    }

    public void playFunc(File file){
        fileToPlay = file;
        if(isPlaying){
            stopAudio();
            playAudio(fileToPlay);
        } else {
            playAudio(fileToPlay);
        }
    }

    private void pauseAudio() {
        mediaPlayer.pause();
        playBtn.setImageDrawable(getResources().getDrawable(R.drawable.player_play_btn, null));
        isPlaying = false;
        seekbarHandler.removeCallbacks(updateSeekbar);
    }

    private void resumeAudio() {
        mediaPlayer.start();
        playBtn.setImageDrawable(getResources().getDrawable(R.drawable.player_pause_btn, null));
        isPlaying = true;

        updateRunnable();
        seekbarHandler.postDelayed(updateSeekbar, 0);
    }

    private void stopAudio() {
        //Stop The Audio
        playBtn.setImageDrawable(getResources().getDrawable(R.drawable.player_play_btn, null));
        playerHeader.setText("Stopped");
        isPlaying = false;
        mediaPlayer.stop();
        seekbarHandler.removeCallbacks(updateSeekbar);
    }

    private void playAudio(File fileToPlay) {
        mediaPlayer = new MediaPlayer();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playBtn.setImageDrawable(getResources().getDrawable(R.drawable.player_pause_btn, null));
        playerFilename.setText(fileToPlay.getName());
        playerHeader.setText("Playing");
        //Play the audio
        isPlaying = true;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
                playerHeader.setText("Finished");
            }
        });

        playerSeekbar.setMax(mediaPlayer.getDuration());

        seekbarHandler = new Handler();
        updateRunnable();
        seekbarHandler.postDelayed(updateSeekbar, 0);

    }

    private void updateRunnable() {
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                playerSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                seekbarHandler.postDelayed(this, 500);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isPlaying) {
            stopAudio();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==1){
            setLayout();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        viewPagerActivity = (ViewPagerActivity) getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        viewPagerActivity = null;
    }

    public Fragment_Recorder(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
