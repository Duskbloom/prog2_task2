import java.util.*;


class MatrixGraph {
  private Edge[][] data;
  private Map<Stad, Integer> stadIndex = new HashMap<Stad, Integer>();
  
  public MatrixGraph(int maxNodes){
    data = new Edge[maxNodes][maxNodes];
  }
  
  public void add(Stad ny){
    stadIndex.put(ny, stadIndex.size());
  }
  
  public void connect(Stad from, Stad to, String namn, int vikt){
    int fromIndex = stadIndex.get(from);
    int toIndex = stadIndex.get(to);
    
    data[fromIndex][toIndex] = new Edge(to, namn, vikt);
    data[toIndex][fromIndex] = new Edge(to, namn, vikt);
  }

}
