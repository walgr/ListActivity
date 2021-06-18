package com.base.listactivity.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.base.listactivity.util.AnimationDrawableUtil;
import com.base.listactivity.util.DensityUtil;

/**
 * Created by zhuli on 2020/5/15.
 */
public class CustomRefreshHeadView extends AppCompatImageView implements SwipeTrigger, SwipeRefreshTrigger {

    private static int changeDistace = 0;
    public static int frameSize = 91;
    public static int frameDuration = 30;

    public AnimationDrawable mAnimationDrawable;

    boolean isRelease = false;

    int curIndex = 0;

    public CustomRefreshHeadView(Context context) {
        super(context);
        init();
    }

    public CustomRefreshHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRefreshHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        changeDistace = DensityUtil.INSTANCE.dip2px(getContext(), 3f);
        mAnimationDrawable = new AnimationDrawable();
        mAnimationDrawable.setOneShot(false);

        for (int i = 0; i < frameSize; i++) {
            int identifier = getResources().getIdentifier("ic_loadding_red_000" + i, "drawable", getContext().getPackageName());
            Drawable drawable = getResources().getDrawable(identifier);
            mAnimationDrawable.addFrame(drawable, frameDuration);
        }
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        setImageDrawable(mAnimationDrawable);
        startAnim();
    }

    public void startAnim() {
        mAnimationDrawable.start();
    }

    public  void stopAnim() {
        mAnimationDrawable.stop();
    }


    @Override
    public void onRefresh() {
        AnimationDrawableUtil.INSTANCE.startAtIndex(mAnimationDrawable, curIndex);
    }

    @Override
    public void onPrepare() {
        isRelease = false;
        // 暂停动画
        mAnimationDrawable.stop();
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isRelease) {
            mAnimationDrawable.stop();

            int length = y / changeDistace % frameSize;
            mAnimationDrawable.selectDrawable(length);

            curIndex = length;
        }
    }

    @Override
    public void onRelease() {
        isRelease = true;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        // 暂停动画
        mAnimationDrawable.stop();
    }
}
