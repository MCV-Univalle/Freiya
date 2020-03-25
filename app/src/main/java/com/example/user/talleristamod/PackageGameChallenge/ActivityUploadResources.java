package com.example.user.talleristamod.PackageGameChallenge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.talleristamod.GlobalVariables.GlobalVariables;
import com.example.user.talleristamod.PackageProfiles.ProfileEstudiante.ActivityProfileEstudiante;
import com.example.user.talleristamod.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class ActivityUploadResources extends AppCompatActivity {

    private Button BtnUploadImage, BtnUploadAudio, BtnUploadCameraImage;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 0;
    private final int PICK_VIDEO_REQUEST = 71;
    private final int PICK_AUDIO_REQUEST = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    //Crear referencia a firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReferenceFromUrl("gs://freiya.appspot.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resources);

        //Initializar Variables
        BtnUploadCameraImage = (Button) findViewById(R.id.buttonUploadCameraImage);
        BtnUploadImage = (Button) findViewById(R.id.buttonUploadImage);
        BtnUploadAudio = (Button) findViewById(R.id.buttonUploadAudio);

        DatabaseReference tipeSolutionRef = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.ID_ACTIVITY+"/type");
        tipeSolutionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String typeSolution = (String) dataSnapshot.getValue();
                if(typeSolution.equalsIgnoreCase("AUDIO"))
                {
                    BtnUploadCameraImage.setVisibility(View.INVISIBLE);
                    BtnUploadCameraImage.setEnabled(false);
                    BtnUploadImage.setVisibility(View.INVISIBLE);
                    BtnUploadImage.setEnabled(false);

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
                }
                else {
                    BtnUploadAudio.setVisibility(View.INVISIBLE);
                    BtnUploadAudio.setEnabled(false);

                    BtnUploadCameraImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                            }
                        }
                    });

                    BtnUploadImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Selecciona una Imagen"), PICK_IMAGE_REQUEST);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    //----------------------------------Metodos-------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                filePath = getImageUri(getApplicationContext(), photo);
                upload();
            } else if (requestCode == PICK_IMAGE_REQUEST) {
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
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void upload()
    {
        if (filePath != null)
        {

            final DatabaseReference databaseReferenceRace = FirebaseDatabase.getInstance().getReference("Activity/ActivityChallenge/"+GlobalVariables.ID_ACTIVITY+"/Resources");
            final String idResource = databaseReferenceRace.push().getKey();
            final StorageReference ref = reference.child(idResource);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String studentName = user.getDisplayName();
            final String studentId = user.getUid();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();





            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Subido exitosamente", Toast.LENGTH_SHORT).show();
                            ObjectResourceChallenge objectResourceChallenge = new ObjectResourceChallenge(studentName, studentId, idResource);
                            databaseReferenceRace.child(idResource).setValue(objectResourceChallenge);
                            Intent intentR = new Intent(getApplicationContext(), ActivityProfileEstudiante.class);
                            startActivity(intentR);
                            finish();

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