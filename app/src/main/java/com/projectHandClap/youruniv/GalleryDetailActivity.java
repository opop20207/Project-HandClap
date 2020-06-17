package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.chrisbanes.photoview.PhotoView;

public class GalleryDetailActivity extends AppCompatActivity {
    PhotoView photo_view_gallery_detail;
    EditText etxt_gallery_detail;
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
        gData = db.getGalleryById(gid);

    }
}
