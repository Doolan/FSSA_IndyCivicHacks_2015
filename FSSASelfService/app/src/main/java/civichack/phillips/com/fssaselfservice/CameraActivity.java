package civichack.phillips.com.fssaselfservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 6/10/2015.
 */
public class CameraActivity extends Activity implements Camera.PictureCallback, Camera.AutoFocusCallback {

    protected static final int REVIEW_CODE = 234;

    int imageCounter = 0;

    private Camera camera;
    private CameraPreview cameraPreview;

    ArrayList<String> picPathList = new ArrayList<>();
    ArrayList<String> docTypeList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setResult(RESULT_CANCELED);

        findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnotherDialog("Are you sure that you want to stop scanning?");
            }
        });

        showInstructionDialog();

        // Camera may be in use by another activity or the system or not available at all
        camera = getCameraInstance();
        if(camera != null){
            initCameraPreview();
        } else {
            finish();
        }
    }

    // Show the camera view on the activity
    private void initCameraPreview() {
        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        cameraPreview.init(camera);
    }

    //@FromXML
    public void onCaptureClick(View button){
        // Take a picture with a callback when the photo has been created
        // Here you can add callbacks if you want to give feedback when the picture is being taken
        camera.takePicture(null, null, this);
    }

    public void onTap(View v){
        camera.autoFocus(this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.e("LOG","Picture taken");
        String path = savePictureToFileSystem(data);

        Intent intentToReview = new Intent(this, ReviewActivity.class);
        intentToReview.putExtra("filepath",path);
        startActivityForResult(intentToReview,REVIEW_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) return;

        picPathList.add(data.getStringExtra("picpath"));
        docTypeList.add(data.getStringExtra("docType"));

        if(data.getBooleanExtra("done",true)){

        }

        setResult(Activity.RESULT_OK);

        //TODO transfer data
        //finish();

    }

    private String savePictureToFileSystem(byte[] data) {
        File file = new File(getFilesDir(), "ScannedDoc"+imageCounter);
        saveToFile(data, file);
        imageCounter++;
        return file.getAbsolutePath();
    }

    public void saveToFile(byte[] bytes, File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
    }

    // ALWAYS remember to release the camera when you are finished
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(camera == null){
            camera = getCameraInstance();
            initCameraPreview();
        }
    }

    private void releaseCamera() {
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
            c.setDisplayOrientation(90);
        } catch (Exception e) {
            // Camera is not available or doesn't exist
            Log.e("getCamera failed", e.toString());
            e.printStackTrace();
        }
        return c;
    }

    public void showInstructionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instructions");
        builder.setMessage("1. Align document with the corners.\n\n" +
                "2. Tap the screen to focus the camera.\n\n" +
                "3. Press the 'Take Picture' button when ready.\n\n" +
                "4. Review the picture.");
        builder.setNeutralButton("OK", new DismissListener());
        builder.show();
    }



    public void instructionsClicked(View v){
        showInstructionDialog();
    }

    private void submitAnotherDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButton("Keep Scanning", new DismissListener());

        builder.setTitle("Finished?");
        builder.setMessage(message);

        builder.show();
    }

    class DismissListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if(success)Log.e("AUTO FOCUS", "GREAT SUCCESS");
        else Log.e("AUTO FOCUS", "FAILURE");
    }

    @Override
    public void onBackPressed() {
        submitAnotherDialog("Are you sure that you want to stop scanning?");
    }
}