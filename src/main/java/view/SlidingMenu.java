package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.administrator.qq_slide_menu.R;

/**
 * ViewGroup
 * onMesure
 * 决定内部子view的宽和高以及自己的宽和高
 * onLayout
 * 决定子View的放置的位置
 * onTouchEvent
 */

/**
 * Created by Administrator on 2015/12/4.
 */
public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    private int mScreenWidth;
    private int mMenuWidth;

    //dp
    private int mMenuRightPadding = 50;
    private boolean once;
    private boolean isopen;

    /**
     * 在代码中new的时候调用
     *
     * @param context
     */
    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));

            }
        }

        ta.recycle();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        Log.d("tag", "mScreenWidth:---" + mScreenWidth);
        Log.d("tag", "mMenuRightPadding:---" + mMenuRightPadding);
    }

    /**
     * 未使用自定义属性时候调用2个参数的
     *
     * @param context
     * @param attrs
     */


    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("tag", "l:  " + l);
        float scale = l * 1.0f / mMenuWidth;
        mMenu.setTranslationX(mMenuWidth * scale*0.5f);

        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight() / 2.0f);

        float leftScale =1.0f-0.7f*scale;
        mMenu.setAlpha(leftScale);
        mMenu.setScaleX(leftScale);
        mMenu.setScaleY(leftScale);

        float rightScale=0.4f*scale+0.6f;
        mContent.setScaleX(rightScale);
        mContent.setScaleY(rightScale);
    }

    /**
     * 设置子view的宽和高
     * 设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 通过设置偏移量，将Menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                Log.d("tag", "mMenuWidth:---" + mMenuWidth);
                int scrollX = getScrollX();
                Log.d("tag", "" + scrollX);

                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isopen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isopen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void closeMenu() {
        if (isopen) {
            isopen = false;
            this.smoothScrollTo(mMenuWidth, 0);
        }

    }

    public void openMenu() {
        if (!isopen) {
            isopen = true;
            this.smoothScrollTo(0, 0);

        }
    }

    public void taggle() {
        if (isopen) {
            closeMenu();
        } else {
            openMenu();
        }
    }


}
