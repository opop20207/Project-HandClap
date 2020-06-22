package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Log.e("!!", gData.gallery_image_path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap origianalBm = BitmapFactory.decodeFile(gData.gallery_image_path, options);
        photo_view_gallery_detail.setImageBitmap(origianalBm);
        photo_view_gallery_detail.setOnClickListener(new PhotoView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryDetailActivity.this, TransparentActivity.class);
                intent.putExtra("galleryId", gid);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
