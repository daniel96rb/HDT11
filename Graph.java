/*
Daniel Rodriguez #15796
Emilio Diaz #15316

Hoja de Trabajo #11 Algoritmo Floyd

El programa calcula la ruta mas corta entre las ciudades de Guatemala.
Permite agregar factores como trafico o desastres naturales que puedan ralentizar el flujo.
Ademas, es posible agregar nuevas conecciones entre las ciudades.

*/

import java.util.ArrayList;

public class Graph {

    ArrayList<String> nodos = new ArrayList<>();
    ArrayList<ArrayList<Integer>> edges = new ArrayList<>(), floyd, floydNodos = new ArrayList<>();

    //Constructor
    public Graph () {}

    //Funcion para añadir una arista
    public void addEdge(String line) {
        String ciudad1 = "", ciudad2 = "";
        String temp = "", tempNum = "";

        //Se recorre cada Char de la linea leida
        for (int i = 0; i < line.length(); i++) {
            //Si no es un espacio, se guarda en un string temporal dependiendo si es letra o digito
            if (Character.isLetter(line.charAt(i)) || Character.isDigit(line.charAt(i))) {
                if (Character.isLetter(line.charAt(i)))
                    temp += line.charAt(i);
                else if (Character.isDigit(line.charAt(i)))
                    tempNum += line.charAt(i);
            } else {
                //Una vez encontrado el espacio, se lee la ciudad y se revisa si ya esta registrada, si no, se añade
                if (!temp.isEmpty()){
                    if (!nodos.contains(temp.toLowerCase()))
                        addNode(temp.toLowerCase());
                    if (ciudad1.isEmpty())
                        ciudad1 = temp.toLowerCase();
                    else
                        ciudad2 = temp.toLowerCase();

                    temp = "";
                }
            }
            // Se guarda la distancia entre las ciudades
            if (!tempNum.isEmpty() && i == line.length()-1) {
                edges.get(nodos.indexOf(ciudad1)).set(nodos.indexOf(ciudad2), Integer.parseInt(tempNum));
                edges.get(nodos.indexOf(ciudad2)).set(nodos.indexOf(ciudad1), Integer.parseInt(tempNum));

                floydNodos.get(nodos.indexOf(ciudad1)).set(nodos.indexOf(ciudad2), nodos.indexOf(ciudad1));
                floydNodos.get(nodos.indexOf(ciudad2)).set(nodos.indexOf(ciudad1), nodos.indexOf(ciudad2));
            }
        }

        //Se reconstruye la matriz de Floyd
        floydAlgorithm();
    }

    //Funcion para agregar un nuevo nodo
    private void addNode(String city) {
        //Se añade una nueva fila en cada matriz
        nodos.add(city);
        edges.add(new ArrayList<>());
        floydNodos.add(new ArrayList<>());

        //Se añade una nueva columna con valores iniciales en las matrices
        for (int i = 0; i < nodos.size()-1; i++) {
            edges.get(i).add(Integer.MAX_VALUE);
            edges.get(edges.size()-1).add(Integer.MAX_VALUE);

            floydNodos.get(i).add(-1);
            floydNodos.get(floydNodos.size()-1).add(-1);
        }

        //Por ultimo se añade el ultimo valor de la matriz
        edges.get(edges.size()-1).add(0);
        floydNodos.get(floydNodos.size()-1).add(-1);
    }

    //Funcion que devuelve la distancia mas corta entre dos ciudades
    public int distBetweenNodes(String ciudad1, String ciudad2) {
        int indexciudad1 = nodos.indexOf(ciudad1);
        int indexciudad2 = nodos.indexOf(ciudad2);

        int dist = floyd.get(indexciudad1).get(indexciudad2);

        return dist;
    }

    //Funcion para eliminar una arista
    public void deleteEdge (String ciudad1, String ciudad2) {
        //Los valores correspondientes de las ciudades se convierten en valores iniciales
        edges.get(nodos.indexOf(ciudad1)).set(nodos.indexOf(ciudad2), Integer.MAX_VALUE);
        edges.get(nodos.indexOf(ciudad2)).set(nodos.indexOf(ciudad1), Integer.MAX_VALUE);

        floydNodos.get(nodos.indexOf(ciudad1)).set(nodos.indexOf(ciudad2), -1);
        floydNodos.get(nodos.indexOf(ciudad2)).set(nodos.indexOf(ciudad1), -1);

        //Se reconstruye la matriz de Floyd
        floydAlgorithm();
    }

    //Funcion que revisa si se contiene a un elemento en el grafo
    public boolean contains (String city) {
        return nodos.contains(city);
    }

    public String toString() {
        return floydNodos.toString();
    }

    //Función que devuelve un string diciendo la distancia entre ciudades y las ciudades intermedias
    public String toString(String ciudad1, String ciudad2) {
        int index1 = nodos.indexOf(ciudad1);
        int index2 = nodos.indexOf(ciudad2);

        String s = "";

        //Se revisan las ciudades intermedias segun el algoritmo de Floyd y se van agregando a un string
        while (floydNodos.get(index1).get(index2) != index1) {
            s += nodos.get(floydNodos.get(index1).get(index2)).toUpperCase().charAt(0) + nodos.get(floydNodos.get(index1).get(index2)).substring(1) + " -> ";
            index1 = floydNodos.get(index1).get(index2);
        }

        if (!s.isEmpty())
            s = s.substring(0,s.length()-4);

        return s;
    }

    //Función que actualiza una matriz de distancias mas cortas utilizando el algoritmo de Floyd
    public void floydAlgorithm(){
        floyd = edges;
        for (int i = 0; i < floyd.size(); i++)
            for (int j = 0; j < floyd.size(); j++)
                for (int k = 0;  k < floyd.size(); k++) {
                    if ((floyd.get(j).get(i) + floyd.get(i).get(k)) < floyd.get(j).get(k)) {
                        floydNodos.get(j).set(k, i);
                    }
                    floyd.get(j).set(k, Integer.min(floyd.get(j).get(k), floyd.get(j).get(i) + floyd.get(i).get(k)));
                }
    }

    //Función que devuelve el centro de la matriz
    public String getCenter () {
        int centerEx = Integer.MAX_VALUE, ex = 0, center = 0;

        //Se revisan los valores maximos de las columnas de la matriz de Floyd para obtener las excentricidades de cada nodo
        for (int i = 0; i < floyd.size(); i++) {
            for (int j = 0; j < floyd.size(); j++) {
                if (floyd.get(j).get(i) > ex)
                    ex = floyd.get(j).get(i);
            }

            //Se lleva un contador de la excentricidad minima para obtener el centro
            if (centerEx > ex) {
                centerEx = ex;
                center = i;
            }
        }

        return nodos.get(floydNodos.get(1).get(center)).toUpperCase().charAt(0) + nodos.get(floydNodos.get(1).get(center)).substring(1);
    }
}
