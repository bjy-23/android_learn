package bjy.edu.android_learn.drawable;

import android.graphics.drawable.VectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bjy.edu.android_learn.R;

public class DrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        ImageView imageView = findViewById(R.id.img);
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
