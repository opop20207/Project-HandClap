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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.projectHandClap.youruniv.AddMemoActivity;
import com.projectHandClap.youruniv.ClassData;
import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.Drawer.Schedule.AddScheduleActivity;
import com.projectHandClap.youruniv.GalleryData;
import com.projectHandClap.youruniv.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddGalleryActivity extends AppCompatActivity {
    File tempFile = null;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    String timeStamp = null;
    String timeStamp_db = null;
    DatabaseHelper db;

    String cstr;
    int cid;
    TextView txv_add_gallery_class;
    Button btn_add_gallery_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gallery);

        db = new DatabaseHelper(this);
        init();
        tedPermission();initIndex();
    }
    public void initIndex(){
        txv_add_gallery_class = (TextView)findViewById(R.id.txv_add_gallery_class);
        btn_add_gallery_class = (Button)findViewById(R.id.btn_add_gallery_class);

        cid = getIntent().getIntExtra("classDataId", -1);
        cstr = getIntent().getStringExtra("classString");
        if(cstr==null) cstr = "Default String";
        ClassData cd = db.getClassDataOneById(cid);
        if(cid==-1){
            txv_add_gallery_class.setText("-");
        }else{
            txv_add_gallery_class.setText(cd.class_title);
        }

        final ArrayList<ClassData> clist = db.getClassDataAll();
        final ArrayList<ClassData> selectedClist = new ArrayList<>();
        final ArrayList<String> cstrlist = new ArrayList<>();
        final ArrayList<String> chklist = new ArrayList<>();
        cstrlist.add("All Classes");
        for(ClassData cdata : clist) {
            if(chklist.contains(cdata.class_string)) continue;
            chklist.add(cdata.class_string);
            cstrlist.add(cdata.class_title);
            selectedClist.add(cdata);
        }

        final CharSequence[] classItem = cstrlist.toArray(new String[cstrlist.size()]);
        btn_add_gallery_class.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGalleryActivity.this);
                builder.setItems(classItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            txv_add_gallery_class.setText("-");
                            cid = -1;
                            cstr = "Default String";
                            return;
                        }
                        i--;
                        String selectedClassTitle = selectedClist.get(i).class_title;
                        String selectedClassString = selectedClist.get(i).class_string;
                        txv_add_gallery_class.setText(selectedClassTitle);
                        cid = (int)selectedClist.get(i).class_id;
                        cstr = selectedClassString;
                    }
                });
                builder.create().show();
            }
        });
    }

    public void init(){

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
            case R.id.btn_add_gallery_submit:
                if(timeStamp==null || tempFile==null) return;
                Log.e("!!",timeStamp+tempFile.getAbsolutePath());
                addToDatabase_Gallery();
                Log.e("!!","2");
                Intent intent = new Intent();
                setResult(1);
                finish();
                break;
            case R.id.btn_add_gallery_cancel:
                onBackPressed();
                break;
        }
    }

    public void addToDatabase_Gallery(){
        GalleryData galleryData = new GalleryData();
        galleryData.gallery_class_string = cstr;
        galleryData.gallery_image_path = tempFile.getAbsolutePath();

        EditText editText = (EditText)findViewById(R.id.etxt_add_gallery_memo);
        galleryData.gallery_memo = editText.getText().toString();
        galleryData.gallery_title = "1";
        galleryData.gallery_time = Long.parseLong(timeStamp_db);
        db.insertGallery(galleryData);
        Log.e("addD", "3");
    }

    public void goToAlbum(){
        timeStamp = new SimpleDateFormat("yyyy_MM_dd_kkmmss").format(new Date());
        timeStamp_db = new SimpleDateFormat("yyyyMMddkkmmss").format(new Date());
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
        timeStamp = new SimpleDateFormat("yyyy_MM_dd_kkmmss").format(new Date());
        timeStamp_db = new SimpleDateFormat("yyyyMMddkkmmss").format(new Date());
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
        if(resultCode == 0) return;
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
                Log.e("!!", tempFile.getPath());
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
