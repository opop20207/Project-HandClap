package com.projectHandClap.youruniv.Drawer.Gallery;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.projectHandClap.youruniv.Drawer.Gallery.utils.MarginDecoration;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.PicHolder;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.imageFolder;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.itemClickListener;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.pictureFacer;
import com.projectHandClap.youruniv.Drawer.Gallery.utils.pictureFolderAdapter;
import com.projectHandClap.youruniv.R;
import com.projectHandClap.youruniv.ViewPager.Fragment_Gallery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryActivity extends AppCompatActivity implements itemClickListener {

    RecyclerView folderRecycler;
    TextView empty;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if(ContextCompat.checkSelfPermission(GalleryActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(GalleryActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        //____________________________________________________________________________________

        empty =findViewById(R.id.empty);

        folderRecycler = findViewById(R.id.folderRecycler);
 //       folderRecycler.addItemDecoration(new MarginDecoration(Fragment_Gallery.this));
        folderRecycler.hasFixedSize();
        ArrayList<imageFolder> folds = getPicturePaths();

        if(folds.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else{
 //           RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds,getAct,this);
 //           folderRecycler.setAdapter(folderAdapter);
        }
    }
    private ArrayList<imageFolder> getPicturePaths(){
        ArrayList<imageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%YourUniv%"}, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                imageFolder folds = new imageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }

        return picFolders;
    }


    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {
        Intent move = new Intent(GalleryActivity.this,ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);

        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move);
    }


    public void o1nClickCamera(View v){
        if(v.getId()==R.id.open_camera){

            long now = System.currentTimeMillis();

            Date mDate = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            String getTime = simpleDate.format(mDate);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_1024(1).JPG");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/YourUniv/"+getTime);
            Uri image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            //Camera intent
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
            startActivityForResult(takePictureIntent, 1);
        }
    }
}
