package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private VideoView videoPlayer;
    private Spinner typeSpinner;
    private Spinner sourceSpinner;
    private Spinner fileSpinner;
    private TextView fileTextView;
    private TextView uriTextView;
    private EditText uriInput;
    private AssetManager assetManager;
    private Button btnPlay;
    private Button btnPause;
    private Button btnResume;
    private Button btnStop;
    private Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileSpinner = findViewById(R.id.spinner_file);
        fileSpinner.setEnabled(false);

        uriInput = findViewById(R.id.uri_input);

        fileTextView = findViewById(R.id.textView3);
        uriTextView = findViewById(R.id.textView4);

        btnPlay = findViewById(R.id.button_play);
        btnPause = findViewById(R.id.button_pause);
        btnResume = findViewById(R.id.button_resume);
        btnStop = findViewById(R.id.button_stop);
        btnSelect = findViewById(R.id.button_select);

        final String[] types = getResources().getStringArray(R.array.types);
        final String[] sources = getResources().getStringArray(R.array.sources);

        typeSpinner = findViewById(R.id.spinner_type);
        sourceSpinner = findViewById(R.id.spinner_src);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> sourceAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, sources);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(sourceAdapter);

        videoPlayer = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }

    public void select(View view) {
        String selectedType = typeSpinner.getSelectedItem().toString();
        String selectedSrc = sourceSpinner.getSelectedItem().toString();
        uriInput.setText("");
        if (selectedSrc.equals("File")) {
            uriTextView.setEnabled(false);
            uriInput.setEnabled(false);

            if (selectedType.equals("Audio")) {
                videoPlayer.setVisibility(View.INVISIBLE);
                assetManager = getAssets();
                try {
                    final String[] audios = assetManager.list("audio");

                    ArrayAdapter<String> audiosAdapter = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_item, audios);
                    audiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fileSpinner.setAdapter(audiosAdapter);

                    fileTextView.setEnabled(true);
                    fileSpinner.setEnabled(true);
                    btnPlay.setEnabled(true);
                    btnPause.setEnabled(false);
                    btnResume.setEnabled(false);
                    btnStop.setEnabled(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (selectedType.equals("Video")) {
                assetManager = getAssets();
                try {
                    final String[] videos = assetManager.list("video");

                    ArrayAdapter<String> videosAdapter = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_item, videos);
                    videosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fileSpinner.setAdapter(videosAdapter);

                    fileTextView.setEnabled(true);
                    fileSpinner.setEnabled(true);
                    btnPlay.setEnabled(true);
                    btnPause.setEnabled(false);
                    btnResume.setEnabled(false);
                    btnStop.setEnabled(false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (selectedSrc.equals("URI")) {
            if (selectedType.equals("Audio")) {
                uriInput.setText("http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3");
                videoPlayer.setVisibility(View.INVISIBLE);
            } else if (selectedType.equals("Video")) {
                uriInput.setText("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
            }
            fileTextView.setEnabled(false);
            fileSpinner.setEnabled(false);
            uriTextView.setEnabled(true);
            uriInput.setEnabled(true);
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            btnResume.setEnabled(false);
            btnStop.setEnabled(false);
        }
    }

    public void play(View view) {
        String selectedType = typeSpinner.getSelectedItem().toString();
        String selectedSrc = sourceSpinner.getSelectedItem().toString();
        if (selectedSrc.equals("File")) {
            if (selectedType.equals("Audio")) {
                String filename = fileSpinner.getSelectedItem().toString();
                try {
                    AssetFileDescriptor afd = assetManager.openFd("audio/" + filename);
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (selectedType.equals("Video")) {
                String filename = fileSpinner.getSelectedItem().toString();

                String uri = "android.resource://" + getPackageName() + "/" +
                        this.getResources().getIdentifier(filename.substring(0, filename.length() - 4), "raw", this.getPackageName());

                videoPlayer.setVideoURI(Uri.parse(uri));
                videoPlayer.setVisibility(View.VISIBLE);
                videoPlayer.start();
            }
        } else if (selectedSrc.equals("URI")) {
            if (selectedType.equals("Audio")) {
                String content_audio = uriInput.getText().toString();
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(content_audio);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return;
                }
            } else if (selectedType.equals("Video")) {
                String content_video = uriInput.getText().toString();
                    try {
                        videoPlayer.setVideoURI(Uri.parse(content_video));
                        videoPlayer.setVisibility(View.VISIBLE);
                        videoPlayer.requestFocus();
                        videoPlayer.start();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        return;
                    }
            }
        }
        btnPlay.setEnabled(false);
        btnPause.setEnabled(true);
        btnResume.setEnabled(false);
        btnStop.setEnabled(true);
        btnSelect.setEnabled(false);
    }

    public void pause(View view) {
        String selectedType = typeSpinner.getSelectedItem().toString();
        if (selectedType.equals("Video")) {
            videoPlayer.pause();
        } else {
            mediaPlayer.pause();
        }

        btnPlay.setEnabled(false);
        btnPause.setEnabled(false);
        btnResume.setEnabled(true);
        btnStop.setEnabled(true);
    }

    public void resume(View view) {
        String selectedType = typeSpinner.getSelectedItem().toString();
        if (selectedType.equals("Video")) {
            videoPlayer.start();
        } else {
            mediaPlayer.start();
        }

        btnPlay.setEnabled(false);
        btnPause.setEnabled(true);
        btnResume.setEnabled(false);
        btnStop.setEnabled(true);
    }

    public void stop(View view) {
        String selectedType = typeSpinner.getSelectedItem().toString();
        if (selectedType.equals("Video")) {
            videoPlayer.stopPlayback();
            videoPlayer.resume();
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        btnPlay.setEnabled(true);
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnStop.setEnabled(false);
        btnSelect.setEnabled(true);
    }
}
