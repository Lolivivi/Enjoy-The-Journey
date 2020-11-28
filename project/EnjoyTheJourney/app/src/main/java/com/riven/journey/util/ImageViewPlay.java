package com.riven.journey.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.riven.journey.R;

/**
 * @author 张硕
 */
public class ImageViewPlay extends AppCompatImageView {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    private int type = TYPE_IMAGE;
    private int playBtnRes = R.drawable.play_btn;
    private Bitmap playBtnBitmap;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect src = new Rect();
    RectF dest = new RectF();

   public ImageViewPlay(@NonNull Context context) {
        super(context);
    }

    public ImageViewPlay(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ImageViewPlay);
        playBtnRes = ta.getResourceId(R.styleable.ImageViewPlay_ivp_play_btn_res,playBtnRes);
        playBtnBitmap = BitmapFactory.decodeResource(getResources(),playBtnRes);
    }

    public ImageViewPlay(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setType(int type){
       this.type = type;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == TYPE_VIDEO){
            Drawable drawable = getDrawable();
            if(drawable != null){
                int viewW = drawable.getIntrinsicWidth();
                int viewH = drawable.getIntrinsicHeight();
                int btnW = playBtnBitmap.getWidth();
                int btnH = playBtnBitmap.getHeight();
                float[] result = measureViewSize(viewW, viewH);
                if(result[0] > 0 && result[1] > 0){ //先根据比例缩放图标，确保绘制的时候再次回归缩放，保持播放的图片大小不变
                    btnW *= (viewW / result[0]);
                    btnH *= (viewH / result[1]);
                }
                float left = (viewW - btnW) / 2.0f;
                float top = (viewH - btnH) / 2.0f;
                src.set(0, 0, btnW, btnH);
                dest.set(left, top, left+btnW, top+btnH);
                canvas.save();
                canvas.concat(getImageMatrix());
                canvas.drawBitmap(playBtnBitmap, src, dest, mPaint);
                canvas.restore();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable drawable = getDrawable();
        if (drawable != null) { //重新计算view
            int viewW = drawable.getIntrinsicWidth();
            int viewH = drawable.getIntrinsicHeight();
            if(viewW > 0 && viewH > 0) {
                float[] result = measureViewSize(viewW, viewH);
                setMeasuredDimension((int)result[0], (int) result[1]);
            }
        }
    }

    //根据图片的宽高，计算出imageview的宽高，长宽等比缩放
    private float[] measureViewSize(int w, int h) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        float maxW = lp.width;
        float maxH = lp.height;
        float showWidth = w;
        float showHeight = h;
        float s = 1.0f * w / h;
        float scale = (1.0f * maxW) / maxH;
        if (w < maxW && h < maxH) { //不进行缩放
            showWidth = w;
            showHeight = h;
        } else if (s > scale) { //宽取最大，高进行缩小
            showWidth = maxW;
            showHeight = (int) (h * (showWidth * 1.0 / w));
        } else if (s <= scale) {//高取最大，宽进行缩小
            showHeight = maxH;
            showWidth = (int) (w * (showHeight * 1.0 / h));
        }
        float[] result = new float[2];
        result[0] = showWidth;
        result[1] = showHeight;
        return result;
    }

}
