package graphs;
public class Edge<T> {
  private T dest;
  private String namn;
  private int weight;
  
  public Edge(T dest, String namn, int weight){
    this.dest = dest;
    this.namn = namn;
    this.weight = weight;
  }
  
  public T getDestination(){
    return dest;
  }

  public int getWeight(){
    return weight;
  }
  
  public void setWeight(int value){
    weight = value;
  }
  public String toString(){
    return "med " + namn + " till " + dest + " tar " + weight;
  }
}
