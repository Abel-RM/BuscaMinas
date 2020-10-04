public class Celda {
    private int numero;
    private Estado estado;

    public Celda(int numero, Estado estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
