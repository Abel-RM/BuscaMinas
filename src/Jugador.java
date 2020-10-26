public class Jugador {
    private String nombre;
    private int puntos;
    private int errores = 0;
    private int tiempo;
    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public Jugador(String nombre,int puntos, int tiempo) {
        this.nombre = nombre;
        this.puntos=puntos;
        this.tiempo=tiempo;
    }

    public int compareTo(Jugador o) {
        if (puntos < o.puntos) {
            return 1;
        }
        if (puntos > o.puntos) {
            return -1;
        }
        return 0;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getErrores() {
        return errores;
    }

    public void incrementarErrores(){
        errores++;
    }
}
