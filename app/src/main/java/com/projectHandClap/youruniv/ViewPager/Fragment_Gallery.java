package com.projectHandClap.youruniv.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.Drawer.Gallery.AddGalleryActivity;
import com.projectHandClap.youruniv.Drawer.Gallery.GalleryActivity;
import com.projectHandClap.youruniv.Drawer.Gallery.ImageDisplay;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.MarginDecoration;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.PicHolder;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.imageFolder;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.itemClickListener;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.pictureFacer;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.pictureFolderAdapter;
import com.projectHandClap.youruniv.GalleryData;
import com.projectHandClap.youruniv.GalleryDetailActivity;
import com.projectHandClap.youruniv.ViewPager.ViewPagerActivity;
import com.projectHandClap.youruniv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class Fragment_Gallery extends Fragment {
    ViewPagerActivity viewPagerActivity;
    ViewGroup viewGroup;
    LinearLayout layoutGallery;
    DatabaseHelper db;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_gallery, container, false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        init();

        return viewGroup;
    }

    public void init(){
        FloatingActionButton fab = viewGroup.findViewById(R.id.btn_add_gallery);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddGalleryActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        layoutGallery = (LinearLayout)viewGroup.findViewById(R.id.layout_gallery);
        setLayout();
    }

    public void setLayout(){
        layoutGallery.removeAllViews();
        ArrayList<GalleryData> galleryDataList = db.getGallery();
        for(GalleryData t : galleryDataList){
            ImageView imageView = new ImageView(mContext);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap origianalBm = BitmapFactory.decodeFile(t.gallery_image_path, options);
            imageView.setImageBitmap(origianalBm);
            final GalleryData ft = t;
            imageView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GalleryDetailActivity.class);
                    intent.putExtra("galleryId", ft.gallery_id);
                    startActivity(intent);
                }
            });
            layoutGallery.addView(imageView);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==1){
            setLayout();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        viewPagerActivity = (ViewPagerActivity) getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        viewPagerActivity = null;
    }

    public Fragment_Gallery(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
