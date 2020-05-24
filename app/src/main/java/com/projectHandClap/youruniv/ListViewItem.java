package com.projectHandClap.youruniv;

import android.widget.ToggleButton;

public class ListViewItem{
    private int Type;

    private String Tittle;
    private String Deadline;
    private ToggleButton isDone;

    public void setType(int type){
        this.Type = type;
    }

    public void setTittle(String tittle){
        Tittle = tittle;
    }

    public void setDeadline(String deadline){
        Deadline = deadline;
    }

    public void setIsDone(ToggleButton isdone){
        isDone = isdone;
    }

    public int getType(){
        return this.Type;
    }

    public String getTittle(){
        return this.Tittle;
    }

    public String getDeadline(){
        return this.Deadline;
    }

    public ToggleButton getIsDone(){
        return this.isDone;
    }
}