package com.projectHandClap.youruniv.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.AutoGridLayout;
import com.projectHandClap.youruniv.ClassData;
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
import com.projectHandClap.youruniv.SquareImageView;
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

    TextView txv_gallery_index_date, txv_gallery_index_class;
    Button btn_gallery_index_date, btn_gallery_index_class;
    ArrayList<Long> dateList;
    long y=0, m=0, d=0;
    int cid;
    String cstr;
    String selDateString;
    long selDateLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_gallery, container, false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        cid = viewPagerActivity.cid;
        cstr = viewPagerActivity.cstr;
        dateList = new ArrayList<>();
        selDateString=null;
        selDateLong = -1;
        init();

        return viewGroup;
    }

    public void initIndex(){
        txv_gallery_index_class = (TextView)viewGroup.findViewById(R.id.txv_gallery_index_class);
        txv_gallery_index_date = (TextView)viewGroup.findViewById(R.id.txv_gallery_index_date);
        btn_gallery_index_class = (Button)viewGroup.findViewById(R.id.btn_gallery_index_class);
        btn_gallery_index_date = (Button)viewGroup.findViewById(R.id.btn_gallery_index_date);

        if(cid!=-1){
            ClassData cd = db.getClassDataOneById(cid);
            txv_gallery_index_class.setText(cd.class_title);
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

        btn_gallery_index_class.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setItems(classItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("!!@", "!"+i);
                        if(i==0){
                            txv_gallery_index_class.setText("-");
                            cid = -1;
                            cstr = "Default String";
                            viewPagerActivity.cid = cid;
                            viewPagerActivity.cstr = cstr;
                            setLayout();
                            return;
                        }
                        i--;
                        String selectedClassTitle = selectedClist.get(i).class_title;
                        String selectedClassString = selectedClist.get(i).class_string;
                        Log.e("!!@", selectedClassTitle);
                        txv_gallery_index_class.setText("ㅁ");
                        cid = (int)selectedClist.get(i).class_id;
                        cstr = selectedClassString;
                        viewPagerActivity.cid = cid;
                        viewPagerActivity.cstr = cstr;
                        setLayout();
                    }
                });
                builder.create().show();
            }
        });

        final ArrayList<String> dateStrList = new ArrayList<>();
        dateStrList.add("All date");
        for(long t : dateList){
            dateStrList.add(String.valueOf(t));
        }

        final CharSequence[] dateItem = dateStrList.toArray(new String[dateStrList.size()]);
        btn_gallery_index_date.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setItems(dateItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            txv_gallery_index_date.setText("-");
                            selDateString = null;
                            selDateLong = -1;
                            setLayout();
                            return;
                        }
                        String selectedDateString = dateStrList.get(i);
                        long selectedDateLong = Long.parseLong(selectedDateString);
                        txv_gallery_index_date.setText(selectedDateString);
                        selDateString = selectedDateString;
                        selDateLong = selectedDateLong;
                        setLayout();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void init(){
        Button fab = viewGroup.findViewById(R.id.btn_add_gallery);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddGalleryActivity.class);
                intent.putExtra("classDataId", cid);
                intent.putExtra("classString", cstr);
                startActivityForResult(intent, 1);
            }
        });

        layoutGallery = viewGroup.findViewById(R.id.layout_gallery);
        setLayout();
    }

    public int convertPixelsToDp(float px, Context context){
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }

    public void setLayout(){
        layoutGallery.removeAllViews();
        dateList = new ArrayList<>();
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2 =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);

        ArrayList<GalleryData> galleryDataList;
        if(cid==-1){
            galleryDataList = db.getGallery();
        }else{
            galleryDataList = db.getGalleryByClassString(cstr);
        }

        Point size = new Point();
        viewPagerActivity.getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int r = 0;
        int c = 0;

        Typeface typeface = getResources().getFont(R.font.font);
        Boolean flag = false;
        long nowtime = 0;
        GridLayout gl = null;
        for(GalleryData g : galleryDataList){
            long t = g.gallery_time%1000000;
            long d = (g.gallery_time/1000000);

            long year=d/10000;
            long month=(d%10000)/100;
            long day=(d%10000)%100;

            String sd=String.valueOf(year)+"년 "+String.valueOf(month)+"월 "+String.valueOf(day)+"일";

            TextView txvDate = null;

            if(nowtime != d){
                flag=true;
                dateList.add(d);
                if(selDateLong!=-1 && selDateLong!=d){
                    continue;
                }
                if(gl!=null){
                    layoutGallery.addView(gl);
                    gl = null;
                }
                nowtime = d;
                gl = new AutoGridLayout(mContext);
                gl.setColumnCount(3);
                gl.setOrientation(GridLayout.HORIZONTAL);

                txvDate = new TextView(mContext);
                txvDate.setText(String.format(sd));
                txvDate.setLayoutParams(layoutParams);
                txvDate.setTextSize(15);
                txvDate.setTypeface(typeface);
                txvDate.setPadding(30,50,15, 0);
                layoutGallery.addView(txvDate);
            }else if(selDateLong!=-1 && selDateLong!=d){
                continue;
            }

            CardView cardView= new CardView(mContext);
            cardView.setLayoutParams(new CardView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_HORIZONTAL));

            SquareImageView imageView = new SquareImageView(mContext);
            imageView.setImageBitmap(decodeSampledBitmapFromFile(g.gallery_image_path, 100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            final GalleryData fg = g;
            imageView.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GalleryDetailActivity.class);
                    Log.e("!!", "send"+fg.gallery_id);
                    intent.putExtra("galleryId", (int)fg.gallery_id);
                    intent.putExtra("classDataId", cid);
                    intent.putExtra("classString", cstr);
                    startActivityForResult(intent, 1);
                }
            });
            imageView.setOnLongClickListener(new ImageView.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("삭제 하시겠습니까?");
                    builder.getContext().getResources().getFont(R.font.font);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteGallery(fg);
                            Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            setLayout();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.create().show();
                    return false;
                }
            });


            GridLayout.Spec rowSpan = GridLayout.spec(r);
            GridLayout.Spec colSpan = GridLayout.spec(c);
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
            gridParam.width = gridParam.height = screenWidth/3;
            c++;
            if(c==3){
                r++;
                c=0;
            }

            gridParam.setGravity(Gravity.LEFT);
            gl.addView(imageView, gridParam);
        }
        if(gl!=null){
            layoutGallery.addView(gl);
        }
        initIndex();
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            int halfHeight = height/2;
            int halfWidth = width/2;
            while((halfHeight/inSampleSize) > reqHeight
                    && (halfWidth/inSampleSize) > reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
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
