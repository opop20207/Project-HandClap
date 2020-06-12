package com.projectHandClap.youruniv;

public class GalleryData {
    public long gallery_id;
    public String gallery_class_string;
    public String gallery_image_path;
    public String gallery_title;
    public String gallery_memo;
    public long gallery_time;

    public GalleryData() {
    }

    public GalleryData(long gallery_id, String gallery_class_string, String gallery_image_path, String gallery_title, String gallery_memo, long gallery_time) {
        this.gallery_id = gallery_id;
        this.gallery_class_string = gallery_class_string;
        this.gallery_image_path = gallery_image_path;
        this.gallery_title = gallery_title;
        this.gallery_memo = gallery_memo;
        this.gallery_time = gallery_time;
    }
}
