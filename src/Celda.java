public class Celda {
    private int numero = 0;
    private Estado estado = Estado.CERRADA;
    private boolean mina;


    public Celda() {
    }

    @Override
    public String toString(){
        switch (estado){
            case ABIERTA:
                return String.valueOf(numero);
            case CERRADA:
                return "C";
            case MARCADA:
                return "M";
            case BLOQUEADA:
                return "B";
            default:return "";
        }
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina() {
        this.mina = true;
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
