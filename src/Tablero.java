import java.util.Random;

public class Tablero {
    private Celda [][]celdas;
    private int tamaño;
    private boolean estadotablero = true;
    private int cantidadMinas = 0;

    public Tablero(Dificultad dificultad) {
        tamaño = calcularTamañoTablero(dificultad);
        celdas = new Celda[tamaño][tamaño];
        for (int i =0;i<tamaño;i++){
            for (int j=0;j<tamaño;j++){
                celdas[i][j] = new Celda();
            }
        }
        cantidadMinas = calcularCantidadMinas(dificultad);
        colocarMinas(cantidadMinas,0);
    }

    //regresa true si todas las celdas que no son minas fueron abiertas
    public boolean celdasAbiertas(){
        int count = 0;
        for (int i =0;i<tamaño;i++){
            for (int j=0;j<tamaño;j++){
                if (celdas[i][j].getEstado() == Estado.ABIERTA)
                    count++;
            }
        }
        if (count+cantidadMinas == tamaño*tamaño)
            return true;
        else
            return false;
    }

    //regresa el estado actual del tablero
    public boolean getEstadotablero() {
        return estadotablero;
    }


    @Override
    public String toString(){
        String res = "";
        for (int i =0;i<celdas.length;i++){
            for (int j=0;j<celdas.length;j++){
                res += celdas[i][j]+"  ";
            }
            res += "\n";
        }
        return res;
    }



    public int getTamaño() {
        return tamaño;
    }
    //muestra el tablero indicando donde hay minas
    public String mostrarTableroReal(){
        String res = "";
        for (int i =0;i<celdas.length;i++){
            for (int j=0;j<celdas.length;j++){
                if (celdas[i][j].isMina())
                    res += "X  ";
                else
                    res += celdas[i][j]+"  ";
            }
            res += "\n";
        }
        return res;
    }

    //calcula el tamaño que tendrá el tablero dependiendo de la dificultad
    private int calcularTamañoTablero(Dificultad dificultad){
        switch (dificultad){
            case PRINCIPIANTE:
                return 8;
            case BASICO:
                return 15;
            case MEDIO:
                return 30;
            case AVANZADO:
                return 50;
            default:
                return 0;
        }
    }

    //determina la cantidad de minas dependiendo de la dificultad
    private int calcularCantidadMinas(Dificultad dificultad){
        Random rn = new Random();
        switch (dificultad){
            case PRINCIPIANTE:
                return 6;
            case BASICO:
                //33-45
                return 225 * (rn.nextInt(6)+15)/100;
            case MEDIO:
                //180-225
                return 900 * (rn.nextInt(6)+20)/100;
            case AVANZADO:
                //625-1000
                return 2500 * (rn.nextInt(16)+25)/100;
            default:
                return 0;
        }
    }

    //elige aleatoriamente las posiciones de las minas
    private void colocarMinas(int cantidadMinas, int cont){
        Random rn = new Random();
        int x = rn.nextInt(tamaño), y = rn.nextInt(tamaño);
        if (!celdas[x][y].isMina()){
            celdas[x][y].setMina();
            actualizarNumeros(x,y);
            cont++;
        }
        if(cont != cantidadMinas)
            colocarMinas(cantidadMinas,cont);
    }

    private void actualizarNumeros(int x, int y){
        for (int i = x-1 ;i <= x+1; i++){
            for (int j= y-1; j <= y+1; j++){
                if(i >= 0 && j >= 0 && i < tamaño && j < tamaño )
                    if (!celdas[i][j].isMina())
                        celdas[i][j].setNumero(celdas[i][j].getNumero()+1);
            }
        }
    }

    public boolean abrirCelda(int x, int y){
        if (celdas[x][y].getEstado() == Estado.CERRADA){
            celdas[x][y].setEstado(Estado.ABIERTA);
            if (celdas[x][y].isMina()){
                estadotablero = false;
            }
            else
                if(celdas[x][y].getNumero()==0){
                    abrirVecinas(x,y,0,0);
                }
            return true;
        }
        return false;
    }

    public boolean bloquearCelda(int x, int y){
        if (celdas[x][y].getEstado() == Estado.CERRADA){
            celdas[x][y].setEstado(Estado.BLOQUEADA);
            return true;
        }
        return false;
    }

    public boolean desbloquearCelda(int x, int y){
        if (celdas[x][y].getEstado() == Estado.BLOQUEADA){
            celdas[x][y].setEstado(Estado.CERRADA);
            return true;
        }
        return false;
    }

    public boolean marcarCelda(int x, int y){
        if (celdas[x][y].getEstado() != Estado.ABIERTA && celdas[x][y].getEstado() != Estado.MARCADA){
            celdas[x][y].setEstado(Estado.MARCADA);
            return true;
        }
        return false;
    }

    //abre las celdas vecinas de una celda
    private void abrirVecinas(int x,int y,int x2,int y2){
        if (x2 != tamaño && y2 != tamaño){
            if (isVecina(x,y,x2,y2)){
                if(celdas[x2][y2].getEstado() == Estado.CERRADA){
                    if (celdas[x2][y2].getNumero() == 0 ){
                        celdas[x2][y2].setEstado(Estado.ABIERTA);
                        abrirVecinas(x2,y2,0,0);
                    }else
                        celdas[x2][y2].setEstado(Estado.ABIERTA);
                }

            }
            if (y2 == tamaño-1)
                abrirVecinas(x,y,++x2,0);
            else
                abrirVecinas(x,y,x2,++y2);
        }
    }

    private boolean isVecina(int x,int y,int x2,int y2){
        if((x == x2 && Math.abs(y-y2) == 1) || (y == y2 && Math.abs(x-x2) == 1) || (Math.abs(x-x2)==1 && Math.abs(y-y2)==1) )
            return true;
        return false;
    }

    public boolean validarError(int x, int y){
        if (celdas[x][y].isMina()){
            return false;
        }
        else
            return true;
    }

}
