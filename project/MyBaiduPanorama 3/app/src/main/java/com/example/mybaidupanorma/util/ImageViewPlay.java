package com.example.mybaidupanorma.util;

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

import androidx.appcompat.widget.AppCompatImageView;

import com.example.mybaidupanorma.R;

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

    public ImageViewPlay(Context context) {
        super(context);
    }

    public ImageViewPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageViewPlay);
        playBtnRes = ta.getResourceId(R.styleable.ImageViewPlay_ivp_play_btn_res, playBtnRes);
        playBtnBitmap = BitmapFactory.decodeResource(getResources(), playBtnRes);
    }

    public ImageViewPlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     *description: 设置图片类型，如果是TYPE_IMAGE，显示图片，如果是TYPE_VIDEO，显示图片，并且在图片正中心绘制一个播放按钮
     **/
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == TYPE_VIDEO){ //如果是TYPE_VIDEO，显示图片，并且在图片正中心绘制一个播放按钮
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int viewW = drawable.getIntrinsicWidth(); //获取图片的宽
                int viewH = drawable.getIntrinsicHeight(); //获取图片的高
                int btnW = playBtnBitmap.getWidth(); //获取播放按钮的宽
                int btnH = playBtnBitmap.getHeight(); //获取播放按钮的高
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
    }

}
