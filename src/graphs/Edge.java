package graphs;
class Edge { //båge
  private Stad dest;
  private String namn;
  private int weight;
  
  public Edge(Stad dest, String namn, int weight){
    this.dest = dest;
    this.namn = namn;
    this.weight = weight;
  }
  
  public Stad getDestination(){
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
