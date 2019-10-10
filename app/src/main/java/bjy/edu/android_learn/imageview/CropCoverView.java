package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import bjy.edu.android_learn.App;
import bjy.edu.android_learn.R;

//裁剪遮罩层
public class CropCoverView extends View {
    private Paint paint;
    private Rect rect;
    private Context context;
    private float cropWidth;

    public CropCoverView(Context context) {
        this(context, null);
    }

    public CropCoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        cropWidth = dp2px(200);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas xFerCanvas = new Canvas(bitmap);
        xFerCanvas.drawColor(context.getResources().getColor(R.color.transparent_bg_1));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        xFerCanvas.drawCircle(getWidth()/2, getHeight()/2, cropWidth/2, paint);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
