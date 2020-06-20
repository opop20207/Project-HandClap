package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class GalleryDetailActivity extends AppCompatActivity {
    PhotoView photo_view_gallery_detail;
    EditText etxt_gallery_detail;
    Button btn_gallery_detail_submit;
    DatabaseHelper db;
    int gid;
    GalleryData gData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        db = new DatabaseHelper(GalleryDetailActivity.this);
        init();
    }

    public void init(){
        gid = getIntent().getIntExtra("galleryId", 1);
        Log.e("!!", "get:"+gid);
        gData = db.getGalleryById(gid);

        photo_view_gallery_detail = (PhotoView) findViewById(R.id.photo_view_gallery_detail);

        final BottomSheetDialog dialog = new BottomSheetDialog(GalleryDetailActivity.this);
        dialog.setContentView(R.layout.bottomsheetgallerydetail);

        etxt_gallery_detail = (EditText) dialog.findViewById(R.id.etxt_gallery_detail);
        etxt_gallery_detail.setText(gData.gallery_memo);

        btn_gallery_detail_submit = (Button) dialog.findViewById(R.id.btn_gallery_detail_submit);
        btn_gallery_detail_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        Log.e("!!", gData.gallery_image_path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap origianalBm = BitmapFactory.decodeFile(gData.gallery_image_path, options);
        photo_view_gallery_detail.setImageBitmap(origianalBm);
        photo_view_gallery_detail.setOnLongClickListener(new PhotoView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialog.show();
                return false;
            }
        });
    }

    public void submit(){
        gData.gallery_memo = etxt_gallery_detail.getText().toString();
        db.updateGallery(gData);
        setResult(1);
        finish();
    }
}
