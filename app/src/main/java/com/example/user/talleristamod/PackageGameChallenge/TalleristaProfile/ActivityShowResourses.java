package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;


import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.net.URL;

public class ActivityShowResourses extends AppCompatActivity implements MediaPlayer.OnPreparedListener{

    ImageView imageView;
    ProgressDialog progressDialog;
    StorageReference reference, ref;
    private MediaPlayer mMediaplayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resourses);
        imageView = findViewById(R.id.imageViewResourse);
        progressDialog= new ProgressDialog(this);
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        reference = storage.getReferenceFromUrl("gs://freiyaproject-65b0b.appspot.com");
        ref = reference.child(GlobalVariables.IDRESOURCE);

        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata)
            {
               if(storageMetadata.getContentType().equals("image/jpeg"))
               {
                   loadImageFromStorage();

               }
               else if (storageMetadata.getContentType().equals("audio/ogg"))
               {
                   loadAudioFromFirebase(storageMetadata.getPath());
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

    private void loadAudioFromFirebase(String path)
    {
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(path);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    final String url = uri.toString();
                    mMediaplayer.setDataSource(url);
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(ActivityShowResourses.this);
                    mMediaplayer.prepareAsync();
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







}










