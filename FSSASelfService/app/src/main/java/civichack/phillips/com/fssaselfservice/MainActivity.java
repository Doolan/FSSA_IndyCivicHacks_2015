package civichack.phillips.com.fssaselfservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {


    private static final int REQ_CAMERA_IMAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Typeface tfArial = Typeface.createFromAsset(getAssets(), "arial.ttf");

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

        ValidateResults results = validate();
        if(results != ValidateResults.Good){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getValidateMessage(results)).setTitle("Error");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return;
        }

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }

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
        ((EditText)findViewById(R.id.month)).setText("");
        ((EditText)findViewById(R.id.day)).setText("");
        ((EditText)findViewById(R.id.year)).setText("");
        ((EditText)findViewById(R.id.lnedit)).setText("");
        ((EditText)findViewById(R.id.ssnedit)).setText("");
    }

    enum ValidateResults{
        Good,MissingSSN,MissingDOB,MissingName,NoMatch
    }

    private ValidateResults validate(){
        String ssn = ((EditText)findViewById(R.id.ssnedit)).getText().toString();
        String name = ((EditText)findViewById(R.id.lnedit)).getText().toString();
        String month = ((EditText)findViewById(R.id.month)).getText().toString();
        String day = ((EditText)findViewById(R.id.day)).getText().toString();
        String year = ((EditText)findViewById(R.id.year)).getText().toString();

        if(ssn.isEmpty())return ValidateResults.MissingSSN;
        if(name.isEmpty())return ValidateResults.MissingSSN;
        if(month.isEmpty() || day.isEmpty() || year.isEmpty())return ValidateResults.MissingDOB;



        return ValidateResults.Good;
    }

    private String getValidateMessage(ValidateResults results){
        String pls = "Please enter your ";
        switch (results){
            case MissingSSN:
                return pls + "Social Security Number.";
            case MissingDOB:
                return pls + "Date of Birth.";
            case MissingName:
                return pls + "Last Name.";
            default: return "";
        }
    }
}
