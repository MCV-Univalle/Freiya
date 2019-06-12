package com.example.user.talleristamod.PackageGameChallenge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ActivityUploadResources extends AppCompatActivity {

    private Button BtnUploadImage, BtnUploadVideo, BtnUploadAudio;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 0;
    private final int PICK_VIDEO_REQUEST = 71;
    private final int PICK_AUDIO_REQUEST = 2;

    //Crear referencia a firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReferenceFromUrl("gs://freiyaproject-65b0b.appspot.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resources);

        //Initializar Variables
        BtnUploadImage = (Button) findViewById(R.id.buttonUploadImage);
        BtnUploadVideo = (Button) findViewById(R.id.buttonUploadVideo);
        BtnUploadAudio = (Button) findViewById(R.id.buttonUploadAudio);

        BtnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //Toast.makeText(getApplicationContext(), " = " + GlobalVariables.SELECTED_CHALLENGE, Toast.LENGTH_SHORT).show();
                startActivityForResult(Intent.createChooser(intent, "Selecciona una Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        BtnUploadAudio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un Audio"), PICK_AUDIO_REQUEST);
            }
        });

        BtnUploadVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un video"), PICK_VIDEO_REQUEST);
            }
        });

    }

    //----------------------------------Metodos-------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                filePath = data.getData();
                upload();
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                filePath = data.getData();
                upload();

            } else if (requestCode == PICK_AUDIO_REQUEST) {
                filePath = data.getData();
                upload();
            }
        }
    }

    private void upload() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();


            final DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.SELECTED_CHALLENGE+"/Resources");
            final String idResource = databaseReferenceRace.push().getKey();
            final StorageReference ref = reference.child(idResource);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String studentName = user.getDisplayName();
            final String studentId = user.getUid();


            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Subido exitosamente", Toast.LENGTH_SHORT).show();
                            ObjectResourceChallenge objectResourceChallenge = new ObjectResourceChallenge(studentName, studentId, idResource);
                            databaseReferenceRace.child(idResource).setValue(objectResourceChallenge);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Seleccion vacia", Toast.LENGTH_SHORT).show();
        }
    }

}
