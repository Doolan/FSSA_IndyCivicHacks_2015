package civichack.phillips.com.fssaselfservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType MEDIA_TYPE = MediaType.parse("image/jpeg");
    private static final int REQ_CAMERA_IMAGE = 123;

    public String[] possibledocs = {"Driver's License", "Social Security", "Birth Certificate", "Bank Statement", "Other"};//TODO make 'other' work

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Typeface tfArial = Typeface.createFromAsset(getAssets(), "arial.ttf");




        findViewById(R.id.takePicBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClicked();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//
    public void cameraClicked(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                ex.printStackTrace();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

    ImageView mImageView;
    String mCurrentPhotoPath;
    File currentImage;
    String selectedDoc;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ...
//
//            //send file
//
//            final File imagefile = currentImage;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//
//                        Log.e("HERE", imagefile.getAbsolutePath());
//
//                        Request request = new Request.Builder()
//                                .url("http://81f85025.ngrok.io")
//                                .post(RequestBody.create(MEDIA_TYPE, imagefile))
//                                .build();
//
//                        Response response = okHttpClient.newCall(request).execute();
//
//                        //delete file
//                        //imagefile.delete();
//                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//                    }catch (Exception e){
//                        e.printStackTrace();;
//                    }
//                }
//            }).start();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((EditText)findViewById(R.id.fnedit)).setText("");
        ((EditText)findViewById(R.id.lnedit)).setText("");
        ((EditText)findViewById(R.id.ssnedit)).setText("");
        if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK){
            String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
            Log.e("LOG","Got image path: "+ imgPath);
            //displayImage(imgPath);

            showConfirmDialog();

        } else
        if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_CANCELED){
            Log.e("LOG","User didn't take an image");
        }
    }

//    private void displayImage(String path) {
//        ImageView imageView = (ImageView) findViewById(R.id.image_view_captured_image);
//        imageView.setImageBitmap(BitmapHelper.decodeSampledBitmap(path, 300, 250));
//    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        currentImage = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + currentImage.getAbsolutePath();
//        return currentImage;
//    }

    private void submitAnother(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cameraClicked();
            }
        });

        builder.setTitle("Finished?");
        builder.setMessage("Would you like to scan another message?");

        builder.show();
    }

    private void showConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Document Name");
            builder.setSingleChoiceItems(possibledocs, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    selectedDoc = possibledocs[item];
                }
            });

            builder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    submitAnother();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
    }
}
