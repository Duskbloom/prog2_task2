package graphs;

import java.io.Serializable;

public class Edge<T> implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 9124562038203273412L;
  private T dest;
  private String name;
  private int weight;
  
  public Edge(T dest, String name, int weight){
    this.dest = dest;
    this.name = name;
    this.weight = weight;
  }
  
  public T getDestination(){
    return dest;
  }

  public int getWeight(){
    return weight;
  }
  
  public String getNamn(){
    return name;
  }
  public void setWeight(int weight){
    this.weight = weight;
  }
  
  public void setName(String name){
    this.name = name;
  }
  public String toString(){
    return "med " + name + " till " + dest + " tar " + weight;
  }
}
