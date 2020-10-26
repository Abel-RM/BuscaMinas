import java.io.*;
import java.util.ArrayList;

public class Juego {
    static ArrayList<Jugador> tablaPuntuaciones = new ArrayList<>();
    public static void main(String[] args) {
        //leer y validar la entrada del nombre
        String valorTemp = "";
        String regexp = "[a-z/A-Z/0-9]+";
        do {
            System.out.println("Escribe tu nombre:");
            valorTemp = Keyboard.readString();
        }while(!valorTemp.matches(regexp));

        Jugador jugador = new Jugador(valorTemp);

        //leer y validar el nivel de dificultad
        regexp = "[1-4]";
        do {
            System.out.println("Escoge la dificultad:");
            System.out.println(Dificultad.PRINCIPIANTE+":1");
            System.out.println(Dificultad.BASICO+"\t\t:2");
            System.out.println(Dificultad.MEDIO+"\t\t:3");
            System.out.println(Dificultad.AVANZADO+"\t:4");
            valorTemp = Keyboard.readString();
        }while(!valorTemp.matches(regexp));


        Dificultad dificultad = Dificultad.PRINCIPIANTE;

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

        Tablero tablero = new Tablero(dificultad);
        System.out.println("---------------------Estado Inicial del tablero--------------------------------------------");
        System.out.println(tablero.mostrarTableroReal());
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("El juego inicia:\n");

        long horaInicio = System.currentTimeMillis();

        System.out.println(tablero);
        String  t = String.valueOf(tablero.getTamaño()-1);

        String abrir = "[0-"+t+"]{1,2},[0-"+t+"]{1,2}";
        String bloquear = "[0-"+t+"]{1,2},[0-"+t+"]{1,2},B";
        String desbloquear = "[0-"+t+"]{1,2},[0-"+t+"]{1,2},D";
        String marcar = "[0-"+t+"]{1,2},[0-"+t+"]{1,2},M";
        String accion = "";

        do {

            do {
                System.out.println("Acciones validas(abrir,bloquear,desbloquear,marcar): ");
                accion = Keyboard.readString();
                accion = accion.replaceAll("(\\s)*","");
            }while (!(accion.matches(abrir) || accion.matches(bloquear) || accion.matches(desbloquear) || accion.matches(marcar)));

            String aux[] = accion.split(",");
            int x = Integer.parseInt(aux[0]), y = Integer.parseInt(aux[1]);
            if (accion.matches(abrir)){
                if(!tablero.abrirCelda(x,y)){
                    System.out.println("Accion no valida!!");
                    continue;
                }
                else
                    if (!tablero.getEstadotablero()){
                        System.out.println("Has perdido!!!");
                        System.out.println(tablero.mostrarTableroReal());
                        break;
                    }
            }
            else
                if (accion.matches(bloquear)){
                    if(!tablero.bloquearCelda(x,y)){
                        System.out.println("Accion no valida!!");
                        continue;
                    }
                }
                else
                    if (accion.matches(desbloquear)){
                        if(!tablero.desbloquearCelda(x,y)){
                            System.out.println("Accion no valida!!");
                            continue;
                        }
                    }
                    else
                        if (accion.matches(marcar)) {
                            if (!tablero.marcarCelda(x, y)) {
                                System.out.println("Accion no valida!!");
                                continue;
                            }else
                                if (tablero.validarError(x,y)){
                                    jugador.incrementarErrores();
                                }
                        }
            System.out.println(tablero);
            if (tablero.celdasAbiertas()){
                long horaFin = System.currentTimeMillis();
                int tiempo = calcularTiempo(horaInicio,horaFin);
                int puntos = calcularPuntaje(tiempo, jugador.getErrores(),dificultad );
                jugador.setTiempo(tiempo);
                jugador.setPuntos(puntos);
                System.out.println("Felicidades has encontrado todas las minas\nGanaste la partida!!");
                System.out.println("Tiempo: "+tiempo+" segundos");
                System.out.println("Errores: "+jugador.getErrores());
                System.out.println("Puntaje final: "+puntos);
                break;
            }
        }while (true);
        System.out.println("Tabla de mejores puntuaciones:");
        System.out.println("-------------------------------------------------------------------------");
        obtenerPuntos(leerPuntaje());
        try {
            actualizarTabla(jugador);
        } catch (IOException e) {
            System.out.println("Error al actualizar la tabla");
        }
        System.out.println(leerPuntaje());
    }

    private static int calcularTiempo(long inicio, long fin){
        return (int) ((fin - inicio)/1000);
    }

    private static int calcularPuntaje(int tiempo, int errores, Dificultad dificultad){
        int p = 0;
        switch (dificultad){
            case PRINCIPIANTE:
                p = 1000 - tiempo - (errores * 100);
                break;
            case BASICO:
                p = 2000 - tiempo - (errores * 100);
                break;
            case MEDIO:
                p = 4000 - tiempo - (errores * 100);
                break;
            case AVANZADO:
                p = 8000 - tiempo - (errores * 100);
        }
        if ( p < 0 )
            return 0;
        else
            return p;
    }

    private static String leerPuntaje() {
        File archivo = new File ("C:\\Users\\abely\\Desktop\\Maestria\\semestre 1\\Tecnologias de programacion\\Proyectos\\BuscaMinas\\src\\tablaPuntos.txt");
        String linea = "";
        String aux = "";
        BufferedReader br = null;
        try{
            FileReader fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            while((aux=br.readLine())!=null)
                linea += aux+"\n";
            br.close();

        }catch (Exception e){
            return "Error al abrir la tabla";
        }
        return linea;
    }

    private static void obtenerPuntos(String l){
        tablaPuntuaciones.clear();
        String arr[] = l.split("\n");
        String nombre = "";
        int puntos = -1;
        int tiempo =-1;
        for (String g: arr){
            if (g.contains("Jugador:")){
                nombre = g.replaceFirst("Jugador:","");
            }
            if (g.contains("Puntos:")){
                puntos = Integer.parseInt(g.replaceFirst("Puntos:",""));
            }
            if (g.contains("Tiempo:")){
                tiempo = Integer.parseInt(g.replaceFirst("Tiempo:",""));
            }
            if (nombre.length()>0 && puntos != -1 && tiempo != -1){
                Jugador jugador = new Jugador(nombre,puntos,tiempo);
                tablaPuntuaciones.add(jugador);
                nombre = "";
                puntos = -1;
                tiempo = -1;
            }

        }
    }

    private static void actualizarTabla(Jugador jugador) throws IOException {
        tablaPuntuaciones.add(jugador);
        tablaPuntuaciones.sort(Jugador::compareTo);
        ArrayList<Jugador> temp =  new ArrayList<>();
        temp.addAll(tablaPuntuaciones);
        tablaPuntuaciones.clear();
        int c = 0;
        for (Jugador j: temp){
            if (c<10)
                tablaPuntuaciones.add(j);
            c++;
        }
        String sFichero = "C:\\Users\\abely\\Desktop\\Maestria\\semestre 1\\Tecnologias de programacion\\Proyectos\\BuscaMinas\\src\\tablaPuntos.txt";
        File fichero = new File(sFichero);
        BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
        String cont = "";
        if (fichero.exists()) {
            for (Jugador j : tablaPuntuaciones){
                cont += "Jugador:"+j.getNombre()+"\n";
                cont +="Puntos:"+j.getPuntos()+"\n";
                cont +="Tiempo:"+j.getTiempo()+"\n";
                cont +="-------------------------------------------------------------------------\n";
            }
            bw.write(cont);
            bw.close();
        }

    }
}
