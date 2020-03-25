package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;


import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ActivityShowResourses extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener{

    ImageView imageView, imageViewAudio, imageViewStop;
    ProgressDialog progressDialog;
    StorageReference reference, ref;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resourses);

        imageView = findViewById(R.id.imageViewResourse);
        imageView.setOnClickListener(this);
        imageViewAudio = findViewById(R.id.imageViewAudio);
        imageViewAudio.setOnClickListener(this);
        imageViewStop = findViewById(R.id.imageViewStop);
        imageViewStop.setVisibility(View.INVISIBLE);
        imageViewAudio.setVisibility(View.INVISIBLE);
        imageViewStop.setOnClickListener(this);
        //progressDialog= new ProgressDialog(this);
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Toast.makeText(getApplicationContext(), "estoy en la actividad...", Toast.LENGTH_LONG).show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        reference = storage.getReferenceFromUrl("gs://freiya.appspot.com");
        ref = reference.child(GlobalVariables.IDRESOURCE);


        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata)
            {
               if(storageMetadata.getContentType().substring(0,5).equals("image"))
               {
                   imageView.setVisibility(View.VISIBLE);
                   loadImageFromStorage();

               }
               else if (storageMetadata.getContentType().substring(0,5).equals("audio"))
               {
                   Toast.makeText(getApplicationContext(), "reproduciendo...", Toast.LENGTH_LONG).show();
                   imageView.setVisibility(View.INVISIBLE);
                   imageViewStop.setVisibility(View.VISIBLE);
                   imageViewAudio.setVisibility(View.INVISIBLE);
                   playAudioFromFirebase(storageMetadata.getPath());
               }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

    }

    public void loadImageFromStorage()
    {

        progressDialog = new ProgressDialog(this);
        //agregas un mensaje en el ProgressDialog
        progressDialog.setMessage("cargando");
        //muestras el ProgressDialog
        progressDialog.show();

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                progressDialog.dismiss();
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .into(imageView);

            }
        }).addOnFailureListener(new OnFailureListener()
        {

            @Override
            public void onFailure(@NonNull Exception exception)
            {
                progressDialog.dismiss();
            }
        });

    }

    private void playAudioFromFirebase(final String path) {

        // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(path);
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    try {
                        // Download url of file
                        final String url = uri.toString();
                        player.setDataSource(url);
                        // wait for media player to get prepare
                        player.setOnPreparedListener(ActivityShowResourses.this);
                        player.prepareAsync();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("TAG", e.getMessage());
                        }
                    });

        }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        player.stop();
        imageView.setVisibility(View.INVISIBLE);
        imageViewStop.setVisibility(View.INVISIBLE);
        imageViewAudio.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageViewAudio:
                imageView.setVisibility(View.INVISIBLE);
                imageViewAudio.setVisibility(View.INVISIBLE);
                imageViewStop.setVisibility(View.VISIBLE);
                player.start();

                break;

            case R.id.imageViewStop:
                imageView.setVisibility(View.INVISIBLE);
                imageViewStop.setVisibility(View.INVISIBLE);
                imageViewAudio.setVisibility(View.VISIBLE);
                player.pause();
                break;

        }
    }
}










