/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modularity;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author dhrubo
 */
public class Modularity {

    private int totalVertexNumber = 0;
    private HashMap<Integer, Cluster> clusterList;
    private int test;
    private HashMap<Integer, List<Integer>> adjacencyListInt = new HashMap<Integer, List<Integer>>();

    public Modularity(int lnoClique, int lnoStar, int lnoVertexClique, int lnoVertexStar, double lcohesion) {
        clusterList = new HashMap<Integer, Cluster>();
        for (int i = 1; i <= lnoClique; i++) {

            Cluster objCluster = new Cluster("Clique");
            objCluster.setisClique(true);
            objCluster.setnoVertex(lnoVertexClique);

            objCluster.setisClique(true);
            objCluster.setnoVertex(lnoVertexClique);
            if (i == 1) {
                objCluster.setstartVertex(i);
                objCluster.setendVertex(lnoVertexClique);
                totalVertexNumber = totalVertexNumber + lnoVertexClique;
            } else {
                objCluster.setstartVertex(totalVertexNumber + 1);
                totalVertexNumber = totalVertexNumber + lnoVertexClique;
                objCluster.setendVertex(totalVertexNumber);
            }
            int edge_clique = (int) (Math.ceil(((lcohesion * lnoVertexClique * (lnoVertexClique - 1)) / 2)));
            if (edge_clique < lnoVertexClique) {
                edge_clique = lnoVertexClique;
            }
            objCluster.setnoEdge(edge_clique);
            clusterList.putIfAbsent(i, objCluster);

        }
        for (int i = lnoClique + 1; i <= lnoClique + lnoStar; i++) {
            Cluster objCluster = new Cluster("Clique");
            objCluster.setisClique(false);
            objCluster.setnoVertex(lnoVertexStar);
            objCluster.setstartVertex(totalVertexNumber + 1);
            totalVertexNumber = totalVertexNumber + lnoVertexStar;
            objCluster.setendVertex(totalVertexNumber);
            int edge_star = (int) (Math.ceil(2 * (lnoVertexStar - 1) * lcohesion));
            if (edge_star < lnoVertexStar) {
                edge_star = lnoVertexStar;
            }
            objCluster.setnoEdge(edge_star);
            clusterList.putIfAbsent(i, objCluster);
        }
        for (int i = 1; i <= totalVertexNumber; i++) {
            adjacencyListInt.putIfAbsent(i, new ArrayList<Integer>());
        }

    }

    public void completeGraph(int lstartVertex, int lendVertex, int lnoEdge) {
        int edgecount = 0;
        int[] vertices = new int[lendVertex - lstartVertex + 1];
        for (int i = 0; i < lendVertex - lstartVertex + 1; i++) {
            vertices[i] = i + lstartVertex;
        }
        shuffle(vertices);
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                if (edgecount <= lnoEdge) {
                    if (setEdge(vertices[i], vertices[j])) {
                        edgecount++;
                    }
                }
            }
        }
    }

    public static double uniform() {
        Random randomNumber = new Random();
        return randomNumber.nextDouble();
    }

    public static int uniform(int N) {
        return (int) (Math.random() * N);
    }

    // take as input an array of objects and rearrange them in random order
    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public boolean setEdge(int sourceVertex, int destinationVertex) {
        if (sourceVertex > adjacencyListInt.size() || destinationVertex > adjacencyListInt.size()) {
            System.out.println("Vertices does not exist");
        }
        List<Integer> sourceListInt = adjacencyListInt.get(sourceVertex);
        if (sourceListInt.isEmpty()) {
            sourceListInt.add(destinationVertex);
            adjacencyListInt.put(sourceVertex, sourceListInt);
            return true;
        }
        if (sourceListInt.contains(destinationVertex)) {
            return false;
        } else {
            sourceListInt.add(destinationVertex);
            adjacencyListInt.put(sourceVertex, sourceListInt);
            return true;
        }

    }

    public void wheelGraph(int lstartVertex, int lendVertex, int lnoEdge) {
        if (lendVertex - lstartVertex + 1 <= 1) {
            throw new IllegalArgumentException("Number of vertices must be at least 2");
        }
        int[] vertices = new int[lendVertex - lstartVertex + 1];
        for (int i = 0; i < lendVertex - lstartVertex + 1; i++) {
            vertices[i] = i + lstartVertex;
        }

        shuffle(vertices);
        int edgecount = 0;
        // simple cycle on V-1 vertices
        for (int i = 1; i < lendVertex - lstartVertex; i++) {
            if (edgecount <= lnoEdge) {
                if (setEdge(vertices[i], vertices[i + 1])) {
                    edgecount++;
                }
            } else {
                break;
            }
        }
        if (edgecount <= lnoEdge) {
            if (setEdge(vertices[lendVertex - lstartVertex], vertices[1])) {
                edgecount++;
            }
        }

        // connect vertices[0] to every vertex on cycle
        for (int i = 1; i < lendVertex - lstartVertex + 1; i++) {
            if (edgecount <= lnoEdge) {
                if (setEdge(vertices[0], vertices[i])) {
                    edgecount++;
                }
            }
        }
    }

    public boolean export_d3j() {
        String d3jSring = "{\"graph\": [],\"directed\": false,\"multigraph\": false,\"nodes\":[";
        Iterator<Integer> keysetIteratorInt = adjacencyListInt.keySet().iterator();
        while (keysetIteratorInt.hasNext()) {
            Integer keyint = keysetIteratorInt.next();
            d3jSring = d3jSring + "{\"id\":\"" + (keyint) + "\",\"description\":\"Node" + keyint + "\", \"type\": \"circle\",\"size\": 30,\"score\": 0},";
        }
//        d3jSring.substring(0,d3jSring.length()-1);
        d3jSring = d3jSring.replaceFirst(".$", "");
        d3jSring = d3jSring + "],\"links\": [";
        keysetIteratorInt = adjacencyListInt.keySet().iterator();
        while (keysetIteratorInt.hasNext()) {
            Integer keyint = keysetIteratorInt.next();
            List<Integer> elements = adjacencyListInt.get(keyint);
            if (elements.size() > 0) {
                for (int i = 0; i < elements.size(); i++) {
                    d3jSring = d3jSring + "{\"source\":" + (keyint - 1) + ",\"target\":" + (elements.get(i) - 1) + "},";
                }
            }
        }
        d3jSring = d3jSring.substring(0, d3jSring.length() - 1);
        d3jSring = d3jSring + "]}";
        try {
            PrintWriter writer = new PrintWriter("d3viz.json");
            writer.write(d3jSring);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void innerConnection() {
        Iterator<Integer> keysetIteratorInt = clusterList.keySet().iterator();
        while (keysetIteratorInt.hasNext()) {
            Integer keyint = keysetIteratorInt.next();
            Cluster objCluster = clusterList.get(keyint);
            if (objCluster.getisClique()) {
                completeGraph(objCluster.getstartVertex(), objCluster.getendVertex(), objCluster.getnoEdge());
            } else {
                wheelGraph(objCluster.getstartVertex(), objCluster.getendVertex(), objCluster.getnoEdge());
            }
        }
    }

    public double calculateModularity() {
        double modularity = 0;
        int totalEdge = 0;
        int totalInnerEdge = 0;
        int totalConnectedEdge = 0;
        Iterator<Integer> keysetIteratorInt = clusterList.keySet().iterator();
        while (keysetIteratorInt.hasNext()){
            Integer keyint = keysetIteratorInt.next();
            Cluster objCluster = clusterList.get(keyint);
            totalInnerEdge = totalInnerEdge + objCluster.getnoEdge();
            totalConnectedEdge = totalConnectedEdge + objCluster.getconnectedEdge();
        }
        totalConnectedEdge = (int) Math.ceil(totalConnectedEdge/2);
        totalEdge = totalInnerEdge+totalConnectedEdge;
        Iterator<Integer> keysetIteratorInt1 = clusterList.keySet().iterator();
        while (keysetIteratorInt1.hasNext()){
            Integer keyint = keysetIteratorInt1.next();
            Cluster objCluster = clusterList.get(keyint);
            double temp = (2 * (double) objCluster.getnoEdge()) + (double) objCluster.getconnectedEdge();
            double temp2 = Math.pow((temp / (2 * (double) totalEdge)), 2);
            modularity = modularity + (((double) objCluster.getnoEdge() / (double) totalEdge) - temp2);
        }
        
        return modularity;
    }

    public List<List<Integer>> Combination(int[] vertices) {
        List<List<Integer>> combs = new ArrayList<>();
        int x = 0;
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                List<Integer> row = new ArrayList<>();
                row.add(vertices[i]);
                row.add(vertices[j]);
                combs.add(x, row);
                x++;
            }
        }
        return combs;
    }

    public void outerconnection(double lcohesion, List<List<Integer>> combinationList) {
        for (int i = 0; i < combinationList.size(); i++) {
            List<Integer> subList = combinationList.get(i);
            Cluster objCluster1 = clusterList.get(subList.get(0));
            Cluster objCluster2 = clusterList.get(subList.get(1));
            int connectedEdge = (int) Math.ceil(objCluster1.getnoVertex() * objCluster2.getnoVertex()* Math.log1p(lcohesion) * (-1));
            if (connectedEdge < 1) {
                connectedEdge = 1;
            }
            Random ran = new Random();
            
            int j = 1;
            while (j <= connectedEdge) {
                int sourceVertex = ran.nextInt(objCluster1.getendVertex() - objCluster1.getstartVertex() + 1) + objCluster1.getstartVertex();
                int destinationVertex = ran.nextInt(objCluster2.getendVertex() - objCluster2.getstartVertex() + 1) + objCluster2.getstartVertex();
                if (setEdge(sourceVertex, destinationVertex)) {
                    j++;
                }
            }
            objCluster1.setconnectedEdge(objCluster1.getconnectedEdge()+connectedEdge);
            objCluster2.setconnectedEdge(objCluster2.getconnectedEdge()+connectedEdge);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // I will take care of these codes later.
//        // TODO code application logic here
//        System.out.println("Enter the number of vertex in Clique :");
//        Scanner input_vertex = new Scanner(System.in);
//        double vertex_star = input_vertex.nextDouble();
//        System.out.println("Enter the number of vertex in Star :");
//        double vertex_clique = input_vertex.nextDouble();
////        System.out.println("Enter the number of Connected edges between the clusters :");
////        double inner_edge = input_vertex.nextDouble();       
//        System.out.println("Cohesion :");
//        double cohesion = input_vertex.nextDouble();
////        cohesion = cohesion - 0.5;
//        int edge_star = (int) (Math.ceil(2 * vertex_star * cohesion));
//        int edge_clique = (int) (Math.ceil(((cohesion *vertex_clique * (vertex_clique - 1)) / 2)));
//
//        int cohesion_inner_edge = (int)Math.ceil(((1-cohesion)* vertex_clique * vertex_star));
////        int cohesion_inner_edge = 1;
//        int total_edge = edge_star + edge_clique + cohesion_inner_edge;
//        double temp;
//        temp = (2 * (double) edge_clique) + (double) cohesion_inner_edge;
//        double temp2 = Math.pow((temp / (2 * (double) total_edge)), 2);
//        double first_part = ((double) edge_clique / (double) total_edge) - temp2;
//        temp = (2 * (double) edge_star) + (double) cohesion_inner_edge;
//        temp2 = Math.pow((temp / (2 * (double) total_edge)), 2);
//        double second_part = ((double) edge_star / (double) total_edge) - temp2;
////        double first_part = ((double(edge_clique)/total_edge)-(((2*double(edge_clique))+double(cohesion_inner_edge))/2*double(total_edge));
////        double second_part = ((edge_star/total_edge)-((2*edge_star+cohesion_inner_edge)/2*total_edge));
//
////        double second_part = (2 * Math.sqrt(cohesion_inner_edge/total_edge));
//        double modularity = first_part + second_part;
//        System.out.println("Modularity is :" + modularity);
        long initialstartTime = System.nanoTime();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of Clique :");
        int noClique = input.nextInt();
        System.out.println("Enter the number of Vertex in Clique");
        int noVertexClique = input.nextInt();
        System.out.println("Enter the number of Star :");
        int noStar = input.nextInt();
        System.out.println("Enter the number of Vertex in Star :");
        int noVertexStar = input.nextInt();
        System.out.println("Enter cohesion value");
        double cohesion = input.nextDouble();

        Modularity objModularity = new Modularity(noClique, noStar, noVertexClique, noVertexStar, cohesion);
        objModularity.innerConnection();
        int clusterSize = objModularity.clusterList.size();
        int[] vertices = new int[clusterSize];
        for (int i = 0; i < clusterSize; i++) {
            vertices[i] = i + 1;
        }
        List<List<Integer>> combinationList = objModularity.Combination(vertices);
        objModularity.outerconnection(cohesion, combinationList);
        long startTime = System.nanoTime();
        objModularity.export_d3j();
        long endTime = System.nanoTime();
        System.out.println("Took "+(endTime - startTime) + " ns"); 
        double modularity = objModularity.calculateModularity();
        System.out.println(modularity);
        int i = 0;
        long finalstartTime = System.nanoTime();
        System.out.println("Took "+(finalstartTime - initialstartTime) + " ns"); 
    }

}
