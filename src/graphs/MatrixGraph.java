package graphs;

import java.util.HashMap;
import java.util.Map;

class MatrixGraph<N> {
  private Edge<N>[][] data;
  private Map<N, Integer> stadIndex = new HashMap<N, Integer>();
  
  public MatrixGraph(int maxNodes){
   // data = new Edge<N>[maxNodes][maxNodes];
  }
  
  public void add(N ny){
    stadIndex.put(ny, stadIndex.size());
  }
  
  public void connect(N from, N to, String namn, int vikt){
    int fromIndex = stadIndex.get(from);
    int toIndex = stadIndex.get(to);
    
    data[fromIndex][toIndex] = new Edge<N>(to, namn, vikt);
    data[toIndex][fromIndex] = new Edge<N>(to, namn, vikt);
  }

}
