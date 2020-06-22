package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TransparentActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxt_gallery_detail;
    Button btn_gallery_detail_submit;
    DatabaseHelper db;
    int gid;
    GalleryData gData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        db = new DatabaseHelper(TransparentActivity.this);
        init();
    }

    public void init(){
        gid = getIntent().getIntExtra("galleryId", 1);
        gData = db.getGalleryById(gid);

        etxt_gallery_detail = (EditText) findViewById(R.id.etxt_gallery_detail);
        etxt_gallery_detail.setText(gData.gallery_memo);

        btn_gallery_detail_submit = (Button) findViewById(R.id.btn_gallery_detail_submit);
        btn_gallery_detail_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    public void submit(){
        gData.gallery_memo = etxt_gallery_detail.getText().toString();
        db.updateGallery(gData);
    }

    @Override
    public void onClick(View view) {
        finish();
        overridePendingTransition(0, 0);
    }
}
