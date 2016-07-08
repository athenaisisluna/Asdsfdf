package net.sgoliver.android.navigationdrawer;


import android.graphics.Bitmap;
import java.util.ArrayList;
import android.graphics.Bitmap;
import java.util.ArrayList;

public class Pelicula {

    private int IdPelicula, TiempoDuracion,Estreno;
    private String NombrePelicula, FotoPelicula, ClasificacionPelicula,GeneroPelicula,FechaEstreno;
    private String URLFoto;
    //private String name;
    private String small, medium, large;
    private String timestamp;


    public Pelicula() {
    }

    public Pelicula(String NombrePelicula, String FotoPelicula,String GeneroPelicula,String FechaEstreno,
                    String ClasificacionPelicula, int IdPelicula, int TiempoDuracion, int Estreno, String small, String medium, String large, String timestamp) {
        this.NombrePelicula = NombrePelicula;
        this.FotoPelicula = FotoPelicula;
        this.ClasificacionPelicula = ClasificacionPelicula;
        this.IdPelicula = IdPelicula;
        this.FechaEstreno = FechaEstreno;
        this.TiempoDuracion = TiempoDuracion;
        this.Estreno = Estreno;
        this.GeneroPelicula = GeneroPelicula;
        this.URLFoto = FotoPelicula;
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.timestamp = timestamp;

    }

    public String getNombrePelicula()
    {
        return NombrePelicula;
    }
    public String getFotoPelicula()
    {
        return FotoPelicula;
    }
    public String getClasificacionPelicula()
    {
        return ClasificacionPelicula;
    }
    public String getFechaEstreno(){return FechaEstreno;}
    public String getGeneroPelicula(){return GeneroPelicula;}
    public String getURLFoto()
    {
        return URLFoto;
    }
    public int getIdPelicula() {
        return IdPelicula;
    }
    public int getEstreno() {return Estreno;}
    public int getTiempoDuracion() {return TiempoDuracion;}


    public void setNombrePelicula(String NombrePelicula)
    {
        this.NombrePelicula = NombrePelicula;
    }
    public void setFotoPelicula(String FotoPelicula)
    {
        this.FotoPelicula = FotoPelicula;
    }
    public void setClasificacionPelicula(String ClasificacionPelicula){this.ClasificacionPelicula = ClasificacionPelicula;}
    public void setFechaEstreno(String FechaEstreno)
    {
        this.FechaEstreno = FechaEstreno;
    }
    public void setGeneroPelicula(String GeneroPelicula)
    {
        this.GeneroPelicula = GeneroPelicula;
    }
    public void setURLFoto(String URLFoto)
    {
        this.URLFoto = URLFoto;
    }
    public void setIdPelicula(int IdPelicula)
    {
        this.IdPelicula = IdPelicula;
    }
    public void setEstreno(int Estreno)
    {
        this.Estreno = Estreno;
    }
    public void setTiempoDuracion(int TiempoDuracion){this.TiempoDuracion = TiempoDuracion;}
    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }




    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    Bitmap thumb;

}


