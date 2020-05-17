package com.projectHandClap.youruniv.Gallery.utils;

import java.util.ArrayList;

public interface itemClickListener {

    void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics);
    void onPicClicked(String pictureFolderPath, String folderName);
}
