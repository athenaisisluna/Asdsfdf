package net.sgoliver.android.navigationdrawer;


import android.graphics.Bitmap;
import java.util.ArrayList;
import android.graphics.Bitmap;
import java.util.ArrayList;

public class Cine {

    private int IdCine;
    private String NombreCine, FotoCine;
    private String URLFoto;
    public Cine() {
    }

    public Cine(String NombreCine, String FotoCine, int IdCine) {
        this.NombreCine = NombreCine;
        this.FotoCine = FotoCine;
        this.IdCine = IdCine;
        this.URLFoto = FotoCine;
        

    }

    public String getNombreCine()
    {
        return NombreCine;
    }
    public String getFotoCine()
    {
        return FotoCine;
    }
    public String getURLFoto()
    {
        return URLFoto;
    }
    public int getIdCine() {
        return IdCine;
    }
    
    public void setNombreCine(String NombreCine)
    {
        this.NombreCine = NombreCine;
    }
    public void setFotoCine(String FotoCine)
    {
        this.FotoCine = FotoCine;
    }
    public void setURLFoto(String URLFoto)
    {
        this.URLFoto = URLFoto;
    }
    public void setIdCine(int IdCine){this.IdCine = IdCine;}
    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    Bitmap thumb;

}


