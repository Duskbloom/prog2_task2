package graphs;
class Edge { //båge
  private Stad dest;
  private String namn;
  private int vikt;
  
  public Edge(Stad dest, String namn, int vikt){
    this.dest = dest;
    this.namn = namn;
    this.vikt = vikt;
  }
  
  public Stad getDestination(){
    return dest;
  }
  
  public String toString(){
    return "med " + namn + " till " + dest + " tar " + vikt;
  }
}
