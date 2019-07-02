package jp.ac.titech.itpro.sdl.appalarm;

import android.graphics.drawable.Drawable;

class AppData{
    String label;
    Drawable icon;
    String pname;
    long alerttime;

    public AppData(String label, Drawable icon, String pname, long alerttime){
        this.label = label;
        this.icon = icon;
        this.pname = pname;
        this.alerttime = alerttime;
    }
}
