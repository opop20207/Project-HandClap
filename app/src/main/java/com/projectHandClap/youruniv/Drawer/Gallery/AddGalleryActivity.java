package com.projectHandClap.youruniv.Drawer.Gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.projectHandClap.youruniv.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddGalleryActivity extends AppCompatActivity {
    File tempFile;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gallery);

        tedPermission();
    }

    public void tedPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .check();
    }

    public void onClickAddGallery(View v){
        switch (v.getId()){
            case R.id.btn_add_gallery_pick:
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                final List<String> ListItems = new ArrayList<>();
                ListItems.add("카메라로 사진 찍기");
                ListItems.add("앨범에서 사진 선택");
                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                dlg.setTitle("사진 선택");
                dlg.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            takePhoto();
                        }else{
                            goToAlbum();
                        }
                    }
                });
                dlg.show();
                break;
//            case R.id.btn_add_gallery_submit:
//                break;
//            case R.id.btn_add_gallery_cancel:
//                break;
        }
    }

    public void goToAlbum(){
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(albumIntent, PICK_FROM_ALBUM);
    }

    public void takePhoto(){
        Intent tpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            tempFile = createImageFile();
        }catch(IOException e){
            Toast.makeText(this, "이미지 처리 오류, 다시 시도해 주세요", Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }

        if(tempFile!=null){
            Uri photoUri = FileProvider.getUriForFile(this, "com.projectHandClap.fileprovider", tempFile);
            tpIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(tpIntent, PICK_FROM_CAMERA);
        }
    }

    public File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_kkmmss").format(new Date());
        String imageFileName = "YourUNIV_"+timeStamp+"_";

        File storageDir = new File(Environment.getExternalStorageDirectory()+"/YourUNIV/");
        if(!storageDir.exists()) storageDir.mkdir();

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    public void setImage(){
        ImageView imageView = findViewById(R.id.iv_temp_image);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap origianalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(origianalBm);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            Toast.makeText(this,"취소 되었습니다", Toast.LENGTH_LONG).show();
            if(tempFile!=null){
                if(tempFile.exists()){
                    if(tempFile.delete()){
                        Log.e("!!", tempFile.getAbsolutePath()+"삭제");
                        tempFile=null;
                    }
                }
            }
        }

        if(requestCode == PICK_FROM_ALBUM){
            if(data == null){
                Toast.makeText(this,"취소 되었습니다", Toast.LENGTH_LONG).show();
                if(tempFile!=null){
                    if(tempFile.exists()){
                        if(tempFile.delete()){
                            Log.e("!!", tempFile.getAbsolutePath()+"삭제");
                            tempFile=null;
                        }
                    }
                }
                return;
            }
            Uri photoUri = data.getData();

            Cursor cursor = null;
            try{
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
            }finally {
                if(cursor!=null){
                    cursor.close();
                }
            }
            setImage();
        }else if(requestCode == PICK_FROM_CAMERA){
            setImage();
        }
    }
}
