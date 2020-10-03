import java.util.regex.Pattern;

public class Juego {
    public static void main(String[] args) {

        Jugador jugador;
        Dificultad dificultad = Dificultad.PRINCIPIANTE;
        Celda[][] tablero;
        long horaInicio;
        long horaFin;

        //leer y validar la entrada del nombre
        String valorTemp = "";
        String regexp = "[a-z/A-Z]+";
        do {
            System.out.println("Escribe tu nombre:");
            valorTemp = Keyboard.readString();
        }while(!Pattern.matches(regexp, valorTemp));

        //leer y validar el nivel de dificultad
        regexp = "[1-4]";
        do {
            System.out.println("Escoge la dificultad:");
            System.out.println("PRINCIPIANTE:1");
            System.out.println("BASICO:\t\t 2");
            System.out.println("MEDIO:\t\t 3");
            System.out.println("AVANZADO:\t 4");
            valorTemp = Keyboard.readString();
        }while(!Pattern.matches(regexp, valorTemp));

        switch (valorTemp){
            case("1"):
                dificultad = Dificultad.PRINCIPIANTE;
                break;
            case ("2"):
                dificultad = Dificultad.BASICO;
                break;
            case ("3"):
                dificultad = Dificultad.MEDIO;
                break;
            case ("4"):
                dificultad = Dificultad.AVANZADO;
                break;
        }

        tablero = crearTablero(dificultad);
        

    }

    private static Celda[][] crearTablero(Dificultad dif){
        return new Celda[1][1] ;
    }

    private void calcularTiempo(){
        long TInicio, TFin, tiempo; //Variables para determinar el tiempo de ejecución
        TInicio = System.currentTimeMillis();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TFin = System.currentTimeMillis(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
        tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
        System.out.println("Tiempo de ejecución en segundos: " + tiempo/1000);
    }
}