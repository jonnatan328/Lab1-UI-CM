package co.edu.udea.compumovil.gr04_20171.lab1;

/**
 * Created by jonnatan on 15/02/17.
 */
public class Pasatiempo {

    private boolean seleccionado;
    private String descripcion;
    private float ratingStar;

    public Pasatiempo(boolean seleccionado, String descripcion, float ratingStar) {
        this.seleccionado = seleccionado;
        this.descripcion = descripcion;
        this.ratingStar = ratingStar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }
}
