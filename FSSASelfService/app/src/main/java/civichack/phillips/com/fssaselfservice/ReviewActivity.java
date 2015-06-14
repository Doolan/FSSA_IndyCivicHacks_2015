package civichack.phillips.com.fssaselfservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sean on 6/11/2015.
 */
public class ReviewActivity extends Activity{

    Bitmap adjustedBitmap;
    String selectedDoc = "Driver's License";
    //TODO make 'other' work
    LayoutInflater inflater;
    String absPath;
    EditText otherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ImageView imageView = (ImageView)findViewById(R.id.review_pic);
        absPath = getIntent().getStringExtra("filepath");

        int sampleSize = getInSampleSizeScale(absPath);

        setResult(Activity.RESULT_CANCELED);

        //matrix for rotation
        Matrix matrix = new Matrix();
        matrix.preRotate(90);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(absPath,bmOptions);

        adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        bitmap.recycle();

        imageView.setImageBitmap(adjustedBitmap);

        showInstructionDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adjustedBitmap.recycle();
    }

    public void onAccept(View v){
        showConfirmDialog();

        //finish();
    }

    public void onReject(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        onReject(null);
    }

    public void showInstructionDialog(){
        showInstructionDialog(null);
    }

    public void showInstructionDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instructions");
        builder.setMessage("1. Look over your photo for clarity.\n\n" +
                "2. PINCH to zoom the photo.\n\n" +
                "3. Make sure that all parts of the document are legible before submitting.");
        builder.setNeutralButton("OK", new DismissListener());
        builder.show();
    }

    private void showConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Document Name");
        builder.setView(View.inflate(this, R.layout.type_view_layout, null));
//        builder.setSingleChoiceItems(possibledocs, -1, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                selectedDoc = possibledocs[item];
//            }
//        });

        builder.setNeutralButton("Submit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selectedDoc.equals("Other...   ")) selectedDoc = otherText.getText().toString();
                dialog.dismiss();
                submitAnotherDialog("Would you like to scan another document?");
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedDoc = null;
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void submitAnotherDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                addPicToEntry(true);

                finish();
            }
        });
        builder.setPositiveButton("Keep Scanning", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                addPicToEntry(false);

                finish();
            }
        });

        builder.setTitle("Finished?");
        builder.setMessage(message);

        builder.show();
    }

    private int getInSampleSizeScale(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        return calculateInSampleSize(options,360,640);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    class DismissListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public void onRadioClicked(View view){
        RadioButton rb = (RadioButton)view;
        int id = rb.getId();
        boolean checked = rb.isChecked();
        selectedDoc = rb.getText().toString();

        RelativeLayout parent = (RelativeLayout)view.getParent();

        if(otherText != null) otherText = (EditText)parent.findViewById(R.id.otherText);

        if(!checked) return;

        EditText otherText = (EditText)parent.findViewById(R.id.otherText);
        ArrayList<RadioButton> list = new ArrayList<>();

        list.add(((RadioButton) parent.findViewById(R.id.ss)));
        list.add(((RadioButton) parent.findViewById(R.id.license)));
        list.add(((RadioButton) parent.findViewById(R.id.bank)));
        list.add(((RadioButton) parent.findViewById(R.id.birthcert)));
        list.add(((RadioButton) parent.findViewById(R.id.other)));

        for(RadioButton r: list){
            if(id != r.getId()) r.setChecked(false);
        }

        switch (id){
            case R.id.ss:
            case R.id.license:
            case R.id.birthcert:
            case R.id.bank:
                otherText.setText("");
                otherText.setEnabled(false);
                break;
            case R.id.other:
                otherText.setEnabled(true);
                break;

        }
    }

    private void addPicToEntry(boolean finished){
        Intent intent = new Intent();
        intent.putExtra("done",finished);
        intent.putExtra("picpath",absPath);
        intent.putExtra("docType",selectedDoc);

        setResult(Activity.RESULT_OK, intent);

        Log.e("PASSBACKINTENT", absPath);
        Log.e("PASSBACKINTENT", selectedDoc);
    }

}
