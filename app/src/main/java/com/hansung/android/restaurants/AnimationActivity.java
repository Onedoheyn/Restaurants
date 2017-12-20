package com.hansung.android.restaurants;

import android.animation.Animator;
 import android.animation.AnimatorInflater;
 import android.animation.AnimatorSet;
 import android.animation.ObjectAnimator;
 import android.animation.ValueAnimator;
 import android.content.Intent;
 import android.graphics.drawable.AnimationDrawable;
 import android.graphics.drawable.Drawable;
 import android.media.Image;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.util.DisplayMetrics;
 import android.util.Log;
 import android.view.animation.AccelerateDecelerateInterpolator;
 import android.view.animation.Animation;
 import android.view.animation.AnimationUtils;
 import android.widget.FrameLayout;
 import android.widget.ImageView;
//--------------------------------앱 시작시 나오는 애니메이션----------------------------------
// 안드로이드 강의 12주차 참조
 public class AnimationActivity extends AppCompatActivity {

 final String TAG = "AnimationTest";
 FrameLayout mFrame;
 ImageView mRocket;
 ImageView mFirework;
 ImageView mCountDown;
 int mScreenHeight;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.animation);

 mFrame = (FrameLayout)findViewById(R.id.animation);
 mCountDown = (ImageView) findViewById(R.id.countdown);
 mFirework = (ImageView) findViewById(R.id.fire);



 }

 @Override
 protected void onResume() {
 super.onResume();

 DisplayMetrics displaymetrics = new DisplayMetrics();
 getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
 mScreenHeight = displaymetrics.heightPixels;


 startCountDownFrameAnimation();
 startFireTweenAnimation();
                }

private void startCountDownFrameAnimation() {
        mCountDown.setBackgroundResource(R.drawable.frame_anim);
        AnimationDrawable countdownAnim = (AnimationDrawable) mCountDown.getBackground();
        countdownAnim.start();
        }

private void startFireTweenAnimation() {
        Animation fire_anim = AnimationUtils.loadAnimation(this, R.anim.fire);
        mFirework.startAnimation(fire_anim);
        fire_anim.setAnimationListener(animationListener);
        }


        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
@Override
public void onAnimationStart(Animation animation) {
        Log.i(TAG, "onAnimationStart");
        }

@Override
public void onAnimationEnd(Animation animation) {
        Log.i(TAG, "onAnimationEnd");
        finish();
        startActivity(new Intent(getApplicationContext(), MapActivity.class));
        }

@Override
public void onAnimationRepeat(Animation animation) {
        Log.i(TAG, "onAnimationRepeat");
        }
        };

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
@Override
public void onAnimationStart(Animator animator) {
        Log.i(TAG, "onAnimationStart");
        }

@Override
public void onAnimationEnd(Animator animator) {
        Log.i(TAG, "onAnimationEnd");
        }

@Override
public void onAnimationCancel(Animator animator) {
        Log.i(TAG, "onAnimationCancel");
        }

@Override
public void onAnimationRepeat(Animator animator) {
        Log.i(TAG, "onAnimationRepeat");
        }
        };
        }