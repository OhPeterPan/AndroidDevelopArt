package com.art.demo.one.ui;

import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.art.demo.one.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button translate;
    private ImageView ivScale, ivRotate, ivAlpha, ivAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initView();
        // startActivity();
    }

    private void initView() {
        translate = findViewById(R.id.translate);
        translate.setOnClickListener(this);
        ivScale = findViewById(R.id.ivScale);
        ivScale.setOnClickListener(this);
        ivRotate = findViewById(R.id.ivRotate);
        ivRotate.setOnClickListener(this);
        ivAlpha = findViewById(R.id.ivAlpha);
        ivAlpha.setOnClickListener(this);
        ivAnimation = findViewById(R.id.ivAnimation);
        ivAnimation.setOnClickListener(this);

 /*       LinearLayout root = findViewById(R.id.root);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layout_translate);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        root.setLayoutAnimation(controller);*/

     /*   Animator animator = AnimatorInflater.loadAnimator(this, AnimatorInflater.loadAnimator(this, R.animator.test_one).test_one);
        animator.setTarget();
        animator.start();*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
                translate.startAnimation(animation);
                break;
            case R.id.ivScale:
                Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
                ivScale.startAnimation(scale);
                break;
            case R.id.ivRotate:
                Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
                ivRotate.startAnimation(rotate);
                break;
            case R.id.ivAlpha:
                Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
                ivAlpha.startAnimation(alpha);
                break;
            case R.id.ivAnimation://帧动画
                AnimationDrawable drawable = (AnimationDrawable) ivAnimation.getBackground();
                drawable.start();
                break;
        }
    }
}
