package bjy.edu.android_learn.imageview;

import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import bjy.edu.android_learn.R;

public class ImageViewActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);

        //scaleType;
//        test_1();

        //setSrc
        test_2();

        //
    }

    public void test_1(){
        imageView.setImageDrawable(getDrawable(R.drawable.tu));
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        matrix.setTranslate(100, 100);
        matrix.preRotate(30);
        imageView.setImageMatrix(matrix);
    }

    public void test_2(){
        //color
//        imageView.setImageResource(R.color.colorPrimary);

        //color -> colorDrawable
        imageView.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.statusBarColor)));
    }
}
