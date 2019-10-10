package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

//只考虑对图片进行手势缩放
public class ExpandleImageView3 extends ImageView {
    private ScaleGestureDetector scaleGestureDetector;

    private Matrix matrix = new Matrix();
    private float[] matrixValues = new float[9];

    public ExpandleImageView3(Context context) {
        this(context, null);
    }

    public ExpandleImageView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setScaleType(ScaleType.MATRIX);

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                //监听手势放大(>1)、缩小(<1)的比例
                //scaleFactor是手指间距离的变化，和x/y方向无关
                float scaleFactor = detector.getScaleFactor();
                Log.e("scaleFactor", ""+scaleFactor);

                //1.使用setScaleX/setScaleY来缩放
                //这种方式存在的问题： TODO: 2019-09-25 当scaleX值到达一定大（5.2）,就很难缩放了？？？
//                float scaleX = getScaleX() * scaleFactor;
//                float scaleY = getScaleY() * scaleFactor;
//                Log.e("scaleX", ""+scaleX);
//                Log.e("scaleY", ""+scaleY);
//                setScaleX(scaleX);
//                setScaleY(scaleY);


//                //使用setImageMatrix实现缩放时，ImageView的scaleType需要为ScaleType.MATRIX
                setScaleType(ScaleType.MATRIX);
                matrix.getValues(matrixValues);
                float scale = matrixValues[Matrix.MSCALE_X];
                Log.e("scale", ""+scale);
                matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                setImageMatrix(matrix);


                return true;
            }

            /*
            * return false: 忽略剩下的所有手势监听
            * */
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
