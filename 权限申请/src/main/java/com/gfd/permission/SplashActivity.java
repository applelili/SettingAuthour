package com.gfd.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * 1.先检查权限有没有授权过，如果授权过不申请，如果没有就去申请
 * 2.如果去申请用户拒绝了，发起二次请求，此时会有一个“不在提示的”选择
 * 3.在二次请求中，这里需要给用户解释一下我们为什么需要这个权限，否则用户可能会永久不在激活这个申请，方便用户理解我们为什么需要这个权限
 * 4.如果用户二次请求被拒绝或者选择了不在提示，我们引导用户到应用权限页面，让用户自己手动打
 */
public class SplashActivity extends AppCompatActivity {

    private RelativeLayout mRelayBg;
    private PermissionHelper.PermissionModel[] permissionModels = {
            new PermissionHelper.PermissionModel(1, Manifest.permission.READ_PHONE_STATE, "我们需要读取手机状态，来识别你的身份"),
            new PermissionHelper.PermissionModel(0, Manifest.permission.CAMERA, "我们需要获取一些图片资源")

    };
    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mRelayBg = (RelativeLayout) findViewById(R.id.relay_splash);
        permissionHelper = new PermissionHelper(this);
        permissionHelper.setOnAlterApplyPermission(new PermissionHelper.OnAlterApplyPermission() {
            @Override
            public void OnAlterApplyPermission() {
                runApp();
            }
        });
        permissionHelper.setRequestPermission(permissionModels);
        if (Build.VERSION.SDK_INT < 23) {//6.0以下，不需要动态申请
            runApp();
        } else {//6.0+ 需要动态申请
            //判断是否全部授权过
            if (permissionHelper.isAllApplyPermission()) {//申请的权限全部授予过，直接运行
                runApp();
            } else {//没有全部授权过，申请
                permissionHelper.applyPermission();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);

    }


    /**
     * 程序的正常执行流程
     */
    private void runApp() {

        AlphaAnimation anim = new AlphaAnimation(1f, 0.5f);
        anim.setDuration(3000);
        anim.setFillAfter(true);
        mRelayBg.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
