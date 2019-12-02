package com.xdynamics.share;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xdynamics.R;

import java.util.Objects;

public class ShareUtils {

    public static void showShare(final AppCompatActivity activity, final ShareContent content, final ShareCallback callback) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(activity, R.layout.share_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();

        assert window != null;
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.btn_youto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                YouToShare share = new YouToShare(activity, content, callback);

                share.show();

            }
        });

        dialog.findViewById(R.id.btn_instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                FacebookShare share = new FacebookShare(activity, content, callback);

                share.show();
            }
        });

        dialog.findViewById(R.id.btn_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                TwitterShare share = new TwitterShare(activity, content, callback);

                share.show();

            }
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public static void showShare(final Fragment fragment, final ShareContent content, final ShareCallback callback) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(Objects.requireNonNull(fragment.getContext()), R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(fragment.getContext(), R.layout.share_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();

        assert window != null;
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.btn_youto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                YouToShare share = new YouToShare(fragment, content, callback);

                share.show();
            }
        });

        dialog.findViewById(R.id.btn_instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                FacebookShare share = new FacebookShare(fragment, content, callback);

                share.show();

            }
        });

        dialog.findViewById(R.id.btn_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                TwitterShare share = new TwitterShare(fragment, content, callback);

                share.show();
            }
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


}


