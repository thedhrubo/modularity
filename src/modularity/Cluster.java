/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modularity;

/**
 *
 * @author dhrubo
 */
public class Cluster {
    private String clusterName;
    private boolean isClique;
    private int noVertex;
    private int noEdge;
    private int startVertex;
    private int endVertex;
    private int connectedEdge;
    
    public Cluster(String lclusterName){
        clusterName = lclusterName;
    }
    
    public String getClusterName(){
        return clusterName;
    }
    
    public void setisClique(boolean lisClique){
        isClique = lisClique;
    }
    
    public boolean getisClique(){
        return isClique;
    }
    
    public void setnoVertex(int lnoVertex){
        noVertex = lnoVertex;
    }
    
    public int getnoVertex(){
        return noVertex;
    }
    
    public void setnoEdge(int lnoEdge){
        noEdge = lnoEdge;
    }
    
    public int getnoEdge(){
        return noEdge;
    }
    
    public void setstartVertex(int vertex){
        startVertex = vertex;
    }
    
    public int getstartVertex(){
        return startVertex;
    }
    
    public void setendVertex(int vertex){
        endVertex = vertex;
    }
    
    public int getendVertex(){
        return endVertex;
    }
    
    public void setconnectedEdge(int edge){
        connectedEdge = edge;
    }
    
    public int getconnectedEdge(){
        return connectedEdge;
    }
}
