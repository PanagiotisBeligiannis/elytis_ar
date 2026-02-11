package com.example.elytis_ar;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

//==============
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;

import android.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//============

public class ARPoemActivity extends AppCompatActivity {

    PreviewView cameraPreview;
    ImageView overlayImage;
    ScrollView poemScroll;
    TextView poemText;
    Button btnPlay;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    private static final int CAMERA_PERMISSION_REQUEST = 100;

    int[] imageTimes = {0, 20, 38, 62, 85, 132, 136, 141, 163, 176, 198};
    int[] imageRes = {
            R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p1, R.drawable.p6,
            R.drawable.p7, R.drawable.p3, R.drawable.p2
    };

    int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        setContentView(R.layout.activity_ar_poem);
        if (checkSelfPermission(android.Manifest.permission.CAMERA)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST
            );
            return;
        }

        cameraPreview = findViewById(R.id.cameraPreview);
        overlayImage = findViewById(R.id.overlayImage);
        poemScroll = findViewById(R.id.poemScroll);
        poemText = findViewById(R.id.poemText);
        btnPlay = findViewById(R.id.btnPlay);

        startCamera();

        poemText.setText(loadPoemText());

        mediaPlayer = MediaPlayer.create(this, R.raw.eleni);

        btnPlay.setOnClickListener(v -> {
            mediaPlayer.start();
            handler.post(updateRunnable);
            btnPlay.setVisibility(View.GONE);
        });
    }

    Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mediaPlayer.isPlaying()) return;

            int currentSec = mediaPlayer.getCurrentPosition() / 1000;

            // ΕΙΚΟΝΕΣ
            if (currentImageIndex < imageTimes.length - 1 &&
                    currentSec >= imageTimes[currentImageIndex + 1]) {
                currentImageIndex++;
                overlayImage.animate()
                        .alpha(0f)
                        .setDuration(600)
                        .withEndAction(() -> {
                            overlayImage.setImageResource(imageRes[currentImageIndex]);
                            overlayImage.animate()
                                    .alpha(0.65f)
                                    .setDuration(600)
                                    .start();
                        })
                        .start();

            }

            // SCROLL ΚΕΙΜΕΝΟΥ (0:20 → 3:20)
            if (currentSec >= 20 && currentSec <= 200) {
                float progress = (currentSec - 20) / 180f;
                int maxScroll = poemText.getHeight() - poemScroll.getHeight();
                poemScroll.smoothScrollTo(0, (int) (maxScroll * progress));
            }

            handler.postDelayed(this, 200);
        }
    };

    private String loadPoemText() {
        InputStream is = getResources().openRawResource(R.raw.poem);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST &&
                grantResults.length > 0 &&
                grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            startCamera(); // Η μέθοδος που ήδη έχεις
        }
    }

    // =======================
    // ΚΑΜΕΡΑ (CameraX)
    // =======================
    void startCamera() {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider =
                        cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();

                preview.setSurfaceProvider(
                        cameraPreview.getSurfaceProvider());

                CameraSelector cameraSelector =
                        CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
