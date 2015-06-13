package civichack.phillips.com.fssaselfservice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Sean on 6/11/2015.
 */
public class ReviewActivity extends Activity{

    Bitmap adjustedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ImageView imageView = (ImageView)findViewById(R.id.review_pic);
        String absPath = getIntent().getStringExtra("filepath");

        setResult(Activity.RESULT_CANCELED);

        //matrix for rotation
        Matrix matrix = new Matrix();
        matrix.preRotate(90);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(absPath,bmOptions);

        adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        imageView.setImageBitmap(adjustedBitmap);

        try {
            ExifInterface exifInterface = new ExifInterface(absPath);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adjustedBitmap.recycle();
    }

    public void onAccept(View v){
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void onReject(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }


}
