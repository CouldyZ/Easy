package com.thh.easy.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thh.easy.R;

/**
 * Created by cloud on 2015/11/5.
 */
public class ChoosePhotoTypeDialog extends Dialog  implements View.OnClickListener{

    Context mContext;
    TextView tvTakePhoto;
    TextView tvFromAtlas;

    public ChoosePhotoTypeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll_dialog = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_upload_content,null);
        tvTakePhoto = (TextView)ll_dialog.findViewById(R.id.tv_take_photo);
        tvTakePhoto.setOnClickListener(this);
        tvFromAtlas = (TextView)ll_dialog.findViewById(R.id.tv_from_atlas);
        tvFromAtlas.setOnClickListener(this);
        this.setContentView(ll_dialog);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener){
        this.onClickItemListener = onClickItemListener;
    }
    OnClickItemListener onClickItemListener;

    public interface OnClickItemListener{
        void onClickPhoto();
        void onClickAtlas();
    }

    public void onClick(View view){
        if(view.getId() == R.id.tv_take_photo){
            onClickItemListener.onClickPhoto();
        } else {
            onClickItemListener.onClickAtlas();
        }
    }
}
