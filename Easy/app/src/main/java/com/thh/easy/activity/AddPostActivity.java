package com.thh.easy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.view.ChoosePhotoTypeDialog;
import com.thh.easy.view.SendCommentButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发帖界面
 */
public class AddPostActivity extends AppCompatActivity implements  SendCommentButton.OnSendClickListener{

    @Bind(R.id.take_photo_toolbar)
    Toolbar postToolbar;

    @Bind(R.id.btn_add_picture)
    Button btnAddPicture;                // 添加图片的按钮

    @Bind(R.id.post_picture)
    ImageView ivPostPicture;              // 添加图片的缩略图

    @Bind(R.id.et_add_post)
    EditText etAddPostContent;            // 添加帖子内容的编辑框

    @Bind(R.id.btn_send_post)
    SendCommentButton mybtnSendPost;      // 发布帖子按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        HttpTools.init(this);
        httpTools = new HttpTools(this);
        setUpToolbar();
        mybtnSendPost.setOnSendClickListener(this);  // 绑定发送事件
    }

    /**
     * toolbar返回
     */
    @OnClick(R.id.iv_back)
    void onToolbarBackPress() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void setUpToolbar() {
        ButterKnife.bind(this);
        if (postToolbar != null) {
            postToolbar.setTitle("");
            setSupportActionBar(postToolbar);
        }
    }

    HttpTools httpTools;                        // 网络操作工具

    @Override
    public void onSendClickListener(View v) {
        // 如果帖子内容不为空，则添加一条新帖子
        if(validateComment()) {
            // TODO 发布新帖子
            String postContent = etAddPostContent.getText().toString();
            Bitmap postImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_22);
            int useId = 0;

            Map<String, Object> params = new HashMap<>();
            params.put("post.image", postImage);
            params.put("user.id", useId);
            params.put("post.contents", postContent);

            httpTools.upload("upload_url", params, new HttpCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onResult(String s) {
                    readJson(s);
                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onCancelled() {

                }

                @Override
                public void onLoading(long l, long l1) {

                }
            });

        }
    }


    public void readJson(String s){
        try {
            JSONObject object = new JSONObject(s);
            String state = object.getString("state");

            // TODO 增加snakebar 显示提示信息。
            if("1".equals(state)){
                Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "添加失败", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     *  点击添加图片
     */
    @OnClick(R.id.btn_add_picture)
    void addPicture() {
        // TODO 从图库或者拍照获取照片
        final ChoosePhotoTypeDialog dialog = new ChoosePhotoTypeDialog(this);
        dialog.setTitle("上传图片");
        dialog.setOnClickItemListener(new ChoosePhotoTypeDialog.OnClickItemListener() {
            @Override
            public void onClickPhoto() {
                dialog.dismiss();
                // 跳转到拍照界面
                startActivity(new Intent(AddPostActivity.this, TakePhotoActivity.class));
            }

            @Override
            public void onClickAtlas() {
                dialog.dismiss();
                // TODO 到图库中选择图片
            }
        });
        dialog.show();
    }


    /**
     * 如果填写的帖子为空，评论按钮会闪动
     * @return
     */
    private boolean validateComment() {
        if (TextUtils.isEmpty(etAddPostContent.getText())) {
            mybtnSendPost.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }
}
