package com.example.user.talleristamod.PackageGameChallenge.TalleristaProfile;


import android.app.ProgressDialog;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.net.URL;

public class ActivityShowResourses extends AppCompatActivity {

    ImageView imageView;
    ProgressDialog progressDialog;
    StorageReference reference, ref;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resourses);
        imageView = findViewById(R.id.imageViewResourse);
        progressDialog= new ProgressDialog(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        reference = storage.getReferenceFromUrl("gs://freiyaproject-65b0b.appspot.com");

        reference.child(GlobalVariables.IDRESOURCE).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata)
            {
               Toast.makeText(getApplicationContext(), "" + storageMetadata.getContentType(), Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });


       // loadImageFromStorage();

    }

    public void loadImageFromStorage()
    {
        //agregas un mensaje en el ProgressDialog
        progressDialog.setMessage("cargando");
        //muestras el ProgressDialog
        progressDialog.show();

        ref = reference.child(GlobalVariables.IDRESOURCE);

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







    }




