package Proyectos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Proyectos.Vertex;

// Proceso de almacenamiento de datos (Nodos y Distancias) en el Hash Map 
public class Grafico {
    // 
    // clase de java vertices 

    Vertex[] nodos;
   

    /*throws FileNotFoundException = si el fichero nombrado en el argumento del
constructor de FileInputStream no existe, el constructor lanza una excepción
java.io.FileNotFoundException. Mediante el lanzamiento de esta excepción,
FileInputStream permite que el método llamador maneje ese error de la forma que
considere más apropiada. */
    //Constructor 
    public Grafico(String archivo) throws FileNotFoundException {
        // flujo de lectura de un fichero al Scanner 
        Scanner sc = new Scanner(new File(archivo));
        // se ingresa el total de los nodos 
        nodos = new Vertex[sc.nextInt()];
        // vamos a colocar todas las letras de los nodos que son 7, como esta en un array de la clase Vertex
        for (int i = 0; i < nodos.length; i++) 
            nodos[i] = new Vertex(sc.next());
        
        // Iterando para poder visitar a todos los nodos(vecinos) y saber su distancia,
        while (sc.hasNext()) {   // usando este objeto iterador, podemos tener acceso a cada elemneto del arreglo, uno por uno 
            //Tomo el indice de la letra de mi segunda columna 
            int v1 = indiceLetra(sc.next());
            // aqui se guarda las letras los destinos o ciudades en este caso A B C etc ...
            String destino = sc.next(); // me da la segunda columna de letras 

            // aqui las distancias que esta entre cada destino 
            int distancia = sc.nextInt(); // se coloca todas las distancias de mi tercera columna de mi txt;
            // y aqui se almacena mediante un objeto Map (key, values) 
            // la distancia de los vecinos de cada uno ejemplo: "E" 5 
            nodos[v1].vecinos.put(destino, distancia);
        }
        // cierra el interface del scanner ;
        sc.close();
    }
    // metodo para recorrer toda mi lista y retornar el indice 
 // String nod; de la clase vertex
    public int indiceLetra(String letra) {
        // manda la letra de la primera columna;
        for (int i = 0; i < nodos.length; i++) {
              if (nodos[i].nod.equals(letra)) {
                return i;
            }
        }
        return -1;
    }
}
