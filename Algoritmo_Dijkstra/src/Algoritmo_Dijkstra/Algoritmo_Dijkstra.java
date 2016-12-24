
package Proyectos;

import Proyectos.Vertex;
import Proyectos.Grafico;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
// Vertex [] nodos; viene de la clase de Grafico 
// Vertex es una clase que viene con sus metodos 
class Vertex   {
// Declaramos variables variables de la clase Vertex

    String nod;
    int mini_distancia;  // para almacenar la minima distancia 
    Vertex ultim_nodo;    // para almacenar el ultimo nodo desde el cual la minima distancia es alcansada.

    // utilizamos el Objeto Map para almacenar (key, values), inicializamos 
    Map<String, Integer> vecinos;

    public Vertex(String nod) {
        this.nod = nod;
        vecinos = new HashMap<String, Integer>();
    }
}
//***************************************************************************************

public class Algoritmo_Dijkstra {

    Grafico graf;
    // constructor 

    /*throws FileNotFoundException = si el fichero nombrado en el argumento del
    constructor de FileInputStream no existe, el constructor lanza una excepción
    java.io.FileNotFoundException. Mediante el lanzamiento de esta excepción,
    FileInputStream permite que el método llamador maneje ese error de la forma que
    considere más apropiada. */
    public Algoritmo_Dijkstra(String archivo) throws FileNotFoundException {
        // aqui pasamos al constructor Grafico toda la informacion; 
        graf = new Grafico(archivo);
    }
    // inicializamos todos los nodos o vertices hacia una distancia infinita 
    //por que no sabemos la distancia y lo setamos en una minima distancia;

    public void inicializandoLetras(Grafico graf, int indice) {
        // Un Objeto Vertex  llamado nodos;
        for (Vertex nodo : graf.nodos) {
            nodo.mini_distancia = 1000;
            nodo.ultim_nodo = null;
        }
        // colocamos la minima distancia de la fuente en 0, el nodo A;
        graf.nodos[indice].mini_distancia = 0;
    }

    public void funcion_dist_minima(Vertex nodo_extraer, Vertex nod, int dist) {
        if (nod.mini_distancia > (nodo_extraer.mini_distancia + dist)) {
            nod.mini_distancia = nodo_extraer.mini_distancia + dist;
            nod.ultim_nodo = nod;
        }
    }
    // Obtener la distancia entre dos nodos 
    public int distanciaFuncion( Vertex nod_extraer, Vertex v) {  
       
        int  dist = nod_extraer.vecinos.get(v.nod);
        return dist;
    }
  
  // minima prioridad de la cola para sus (minima distancia desde la fuente) valores. 
    public class Comparador_nodos implements Comparator<Vertex> {   
        // Utilizamos un metodo del interface 
     
        @Override
        public int compare(Vertex nod1, Vertex nod2) {
             // resta disatancias sumadas para poder obtener referencias distancias minimas 
            return (nod1.mini_distancia - nod2.mini_distancia);
        }
    }
    // Obtener el indice desde el nodo del vertice 
    public int indiceLetra(Grafico Graf, String letra) {  
        for (int i = 0; i < Graf.nodos.length; i++) {
        //      System.out.println(Graf.nodos[i].nod);
            if (Graf.nodos[i].nod.equals(letra)) {
                return i;
            }
        }
        return -1;
    }
    // **** Proceso del Algoritmo *****//

    public Set<Vertex> Algoritmo(Grafico graf, int indice) {
        // llamos el metodo para inicializar las letras a infinito 
        inicializandoLetras(graf, indice);
        //Interfaz Set hace que no pueda contener elementos duplicados 
        //Inicializando mi interfase Set
        Set<Vertex> nodos_set = new HashSet<Vertex>();
        // para tener prioridad de los elmentos de mi cola, el que tiene priorirada sale primero en la cola 
        // si en caso dos elementos de la cola tiene la misma prioridad, se establecera segun el orden de la cola
        // minima prioridad de cola.
        //Manda valores al constructor (capacidad)
        /* substraido de la libreria:
          public PriorityQueue(int initialCapacity,
                         Comparator<? super E> comparator) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new Object[initialCapacity];
        this.comparator = comparator; }
        */
        //el constructor PriorityQueue() ordena sus elementos de acuerdo a su orden natual.
        Queue<Vertex> cola = new PriorityQueue<Vertex>(10, new Comparador_nodos());
        
       // en los nodos se adjunta el primer indice 0 
            cola.add(graf.nodos[indice]);
       
       
        while (cola.size()!= 0) {
           
            // .poll() extrae el nodo que esta en la cabeza de la cola en este caso la minima distancia.
            // si se encuentra vacia retorna null;
            Vertex nod_extraer = cola.poll();   
            // Adjunta  los nodos que se extrae de la cola 
            nodos_set.add(nod_extraer);             
           //Vecinos (Map; hashMap).. extrae la llave en este caso la letra(Nodo o Vertice); 
           //keySet() retorna todos los elmentos contenidos en el Mapa.
            for (String nodo_letra : nod_extraer.vecinos.keySet()) { 
               // se obtiene el indice de la letra 
               // el indice que se dio en la clase grafico lo generamos de nuevo;
                int nodo_Num = indiceLetra(graf, nodo_letra);
                
                Vertex nod = graf.nodos[nodo_Num];
                 // las distancias de todas las aristas 
                int dist = distanciaFuncion(nod_extraer, nod);

                funcion_dist_minima(nod_extraer, nod, dist);
                cola.add(nod);
            }
        }

        return nodos_set;

    }

    public static void main(String[] args) throws FileNotFoundException {
        String Direc_txt = "C:/Users/User/Documents/NetBeansProjects/Grafos/src/Proyectos/cordenadas_grafo.txt";
        // Llevamos  en string el txt al constructor.
        Algoritmo_Dijkstra dij = new Algoritmo_Dijkstra(Direc_txt);
        //  creamos un objeto Grafico llamado graf;
        Set<Vertex> datosNodos = dij.Algoritmo(dij.graf, 0);
        System.out.println("La distancia de todos los vecinos(nodos) desde el vecino(nodo) A ");
        // Iteracion en un for especial 
        for (Vertex verte : datosNodos) {
            System.out.println("Vecino: " + verte.nod + "  hay una distancia de " + verte.mini_distancia + " Km");
        }
    }
}

