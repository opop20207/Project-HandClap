package com.projectHandClap.youruniv.Drawer.Gallery.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.projectHandClap.youruniv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class pictureFolderAdapter extends RecyclerView.Adapter<pictureFolderAdapter.FolderHolder>{

    private ArrayList<imageFolder> folders;
    private Context folderContx;
    private itemClickListener listenToClick;

    public pictureFolderAdapter(ArrayList<imageFolder> folders, Context folderContx, itemClickListener listen) {
        this.folders = folders;
        this.folderContx = folderContx;
        this.listenToClick = listen;
    }

    @NonNull
    @Override
    public FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.picture_folder_item, parent, false);
        return new FolderHolder(cell);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FolderHolder holder, int position) {
        final imageFolder folder = folders.get(position);

        Glide.with(folderContx)
                .load(folder.getFirstPic())
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderPic);

        String text = folder.getFolderName();
        String[] Day=text.split("-");
        String Dayofweek=getDayOfweek(text);

        holder.folderName.setText(Day[0]+"년 "+Day[1]+"월 "+Day[2]+"일 "+"("+Dayofweek+")");

        holder.folderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenToClick.onPicClicked(folder.getPath(),folder.getFolderName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return folders.size();
    }


    public class FolderHolder extends RecyclerView.ViewHolder{
       ImageView folderPic;
       TextView folderName;
       CardView folderCard;

        public FolderHolder(@NonNull View itemView) {
            super(itemView);
           folderPic = itemView.findViewById(R.id.folderPic);
           folderName = itemView.findViewById(R.id.folderName);
           folderCard = itemView.findViewById(R.id.folderCard);
        }
    }

    public String getDayOfweek(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] week = {"일","월","화","수","목","금","토"};
        int w=0;
        Calendar cal = Calendar.getInstance();
        Date getDate;
        try { getDate = format.parse(date); cal.setTime(getDate);
            w = cal.get(Calendar.DAY_OF_WEEK)-1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return week[w];
    }


}
