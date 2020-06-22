package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity {
    NavController navController;
    boolean isRecording = false;

    String recordPermission = Manifest.permission.RECORD_AUDIO;
    int PERMISSION_CODE = 21;

    MediaRecorder mediaRecorder;
    String recordFile;
    String file_path, timeStamp;

    Button btn_record;
    TextView txv_record_title;
    EditText etxt_add_record_title;
    Chronometer timer;

    DatabaseHelper db;

    String cstr;
    int cid;
    TextView txv_add_record_class;
    Button btn_add_record_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        db = new DatabaseHelper(this);
        init();
        tedPermission();initIndex();
    }

    public void initIndex(){
        txv_add_record_class = (TextView)findViewById(R.id.txv_add_record_class);
        btn_add_record_class = (Button)findViewById(R.id.btn_add_record_class);

        cid = getIntent().getIntExtra("classDataId", -1);
        cstr = getIntent().getStringExtra("classString");
        if(cstr==null) cstr = "Default String";
        ClassData cd = db.getClassDataOneById(cid);
        if(cid==-1){
            txv_add_record_class.setText("-");
        }else{
            txv_add_record_class.setText(cd.class_title);
        }

        final ArrayList<ClassData> clist = db.getClassDataAll();
        final ArrayList<String> cstrlist = new ArrayList<>();
        final ArrayList<String> cstrTempList = new ArrayList<>();
        cstrlist.add("All Classes");
        for(ClassData cdata : clist) {
            if(cstrTempList.contains(cdata.class_string)) continue;
            cstrTempList.add(cdata.class_string);
            cstrlist.add(cdata.class_title);
        }

        final CharSequence[] classItem = cstrlist.toArray(new String[cstrlist.size()]);
        btn_add_record_class.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(AddRecordActivity.this);
                builder.setItems(classItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            txv_add_record_class.setText("-");
                            cid = -1;
                            cstr = "Default String";
                            return;
                        }
                        i--;
                        String selectedClassTitle = clist.get(i).class_title;
                        String selectedClassString = clist.get(i).class_string;
                        txv_add_record_class.setText(selectedClassTitle);
                        cid = (int)clist.get(i).class_id;
                        cstr = selectedClassString;
                    }
                });
                builder.create().show();
            }
        });
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
                .setRationaleMessage("녹음 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .check();
    }

    public void init(){
        timer = (Chronometer)findViewById(R.id.record_timer);
        btn_record = (Button)findViewById(R.id.btn_record);
        etxt_add_record_title = (EditText)findViewById(R.id.etxt_add_record_title);
        txv_record_title = (TextView)findViewById(R.id.txv_record_title);
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            //Permission not granted, ask for permission
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    public void stopRecording(){
        timer.stop();

        txv_record_title.setText("Recording Stopped.");

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
    }

    public void startRecording(){
        Log.e("!!", "btn_record in@#$");
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        Log.e("!!", "btn_record in@#$");
        String recordPath = Environment.getExternalStorageDirectory()+"/YourUNIVrecord/";
        File storageDir = new File(recordPath);
        if(!storageDir.exists()) storageDir.mkdir();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddkkmmss");
        Date now = new Date();
        timeStamp = formatter.format(now);

        Log.e("!!", "btn_record in@#");
        recordFile = "Record_"+timeStamp+".mp3";
        txv_record_title.setText("Recording...,");
        file_path = recordPath+"/"+recordFile;

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(file_path);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public int addToDatabase_Record(){
        if(etxt_add_record_title.getText().toString().isEmpty()){
            Toast.makeText(this,"제목이 없습니다.", Toast.LENGTH_LONG).show();
            return 1;
        }
        if(isRecording){
            Toast.makeText(this,"녹음중입니다.\n녹음을 끄고 저장 버튼을 눌러주세요.", Toast.LENGTH_LONG).show();
            return 1;
        }
        RecordData recordData = new RecordData();
        recordData.record_class_string = cstr;
        recordData.record_title = etxt_add_record_title.getText().toString();
        recordData.record_file_path = file_path;
        recordData.record_time = Long.parseLong(timeStamp);
        db.insertRecord(recordData);
        Log.e("!!", "3");
        return 0;
    }

    public void onClickAddRecord(View v){
        switch(v.getId()){
            case R.id.btn_add_record_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_record_submit:
                int flag = addToDatabase_Record();
                if(flag == 1) return;
                setResult(1);
                finish();
                break;
            case R.id.btn_record:
                Log.e("!!", "btn_record in");
                if(isRecording){
                    stopRecording();

                    btn_record.setText("녹음 시작");
                    isRecording = false;
                }else{
                    Log.e("!!", "btn_record in2");
                    if(checkPermissions()){
                        Log.e("!!", "btn_record in3");
                        startRecording();
                        Log.e("!!", "btn_record in3");
                        btn_record.setText("녹음 중지");
                        Log.e("!!", "btn_record in4");
                        isRecording = true;
                    }
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isRecording){
            stopRecording();
        }
    }
}
