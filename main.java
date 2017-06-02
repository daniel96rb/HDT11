import java.util.Scanner; //Importa la clase del Scanner
import java.io.BufferedReader;
import java.io.FileReader;

public class main {
    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in); //Variable para leer los datos ingresados por el usuario
        int opcion = 0;
        Graph ciudades = new Graph();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\guategrafo.txt")); // Direccion donde se encuentra el archivo

            String line;

            //Realiza el ciclo, mientras se encuentren datos
            while ((line = bf.readLine()) != null) {
                //Se agrega la arista correspondiente (y nodo si es necesario)
                ciudades.addEdge(line);
            }

            //En caso de no poder abrir el archivo, imprime "ERROR"
        } catch (Exception e) {
            System.err.println("ERROR: No se encontro el archivo.");
        }

        System.out.println("Bienvenido, podras encontrar la ruta mas corta entre las ciudades con este programa." +
        "\n Las ciudades son:" +
        "\n guatemala" +
        "\n antigua" +
        "\n panajachel" +
        "\n escuintla" +
        "\n retalhuleu" +
        "\n riodulce" +
        "\n flores" +
        "\n tikal");

        while (opcion != 5) {
            //Se muestra en pantalla el menu
            System.out.println(
                    "\n1. Distancia entre ciudades\n" +
                    "2. Centro del grafo\n" +
                    "3. Interrupcion de trafico entre ciudades\n" +
                    "4. Establecer ruta entre ciudades\n" +
                    "5. Salir");

            //Se lee la opcion del usuario
            opcion = (int)scan.nextDouble();

            //Codigo para la primera opcion
            if (opcion == 1) {
                System.out.println ("Por favor ingrese el nombre de la ciudad inicial:");
                String city1 = scan.next();
                while (!ciudades.contains(city1)) {
                    System.out.println("La ciudad ingresada no se encuentra registrada.");
                    city1 = scan.next();
                }

                System.out.println("Por favor ingrese el nombre de la ciudad final:");
                String city2 = scan.next();
                while (!ciudades.contains(city2)) {
                    System.out.println("La ciudad ingresada no se encuentra registrada.");
                    city2 = scan.next();
                }

                city1 = city1.toLowerCase();
                city2 = city2.toLowerCase();

                //Se muestra la distancia y las ciudades intermedias
                String temp = ciudades.toString(city1, city2);
                if (temp.isEmpty())
                    System.out.println("La distancia mas corta es de " + ciudades.distBetweenNodes(city1, city2) + "KM");
                else
                    System.out.println("La distancia mas corta es de " + ciudades.distBetweenNodes(city1, city2) + "KM y debe pasar por: " + temp);

            } else if (opcion == 3) { //Codiog para la tercera opcion

                //Se leen las ciudades ingresadas por el usuario
                System.out.println ("Por favor ingrese el nombre de la primera ciudad:");
                String city1 = scan.next();
                while (!ciudades.contains(city1)) {
                    System.out.println("La ciudad ingresada no se encuentra registrada.");
                    city1 = scan.next();
                }

                System.out.println ("Por favor ingrese el nombre de la segunda ciudad:");
                String city2 = scan.next();
                while (!ciudades.contains(city2)) {
                    System.out.println("La ciudad ingresada no se encuentra registrada.");
                    city2 = scan.next();
                }

                //Se elimina la arista correspondiente
                ciudades.deleteEdge(city1, city2);

            } else if (opcion == 2) { //Codigo para la segunda opcion
                System.out.println("El centro del grafo es: " + ciudades.getCenter());

            } else if (opcion == 4) { // Codigo para la cuarta opcion
                //Se leen las ciudades y la distancia
                System.out.println("Por ingrese el nombre de las ciudades y su distancia. (Ej. Ciudad1 Ciudad2 XXX)");
                String line = scan.next() + " " + scan.next() + " " + scan.next();

                //Se agrega la arista correspondientes (y nodo si es necesario)
                ciudades.addEdge(line);
            }
        }
    }
}
