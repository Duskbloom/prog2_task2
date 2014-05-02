package graphs;
import java.util.*;

public class ListGraph<T> implements Graph<T> {
  /**
   * 
   */
  private static final long serialVersionUID = 7206624012985749434L;
  private Map<T, List<Edge<T>>> data = new HashMap<T, List<Edge<T>>>();

  public void add(T item){
    if(!data.containsKey(item))
      data.put(item, new ArrayList<Edge<T>>() );
  }

  public void connect(T from, T to, String name, int weight){
    List<Edge<T>> fromRoads = data.get(from);
    List<Edge<T>> toRoads = data.get(to);
    if(fromRoads == null || toRoads == null)
      throw new NoSuchElementException("The given node does not exist in the graph");
    if(weight<0)
      throw new IllegalArgumentException("Weight can´t be less than 0");
    for(Edge<T> edge: fromRoads){
      if(edge.getDestination().equals(to))
        throw new IllegalStateException("A path already exists between the given nodes");
    }

    Edge<T> e1 = new Edge<T>(to, name, weight);
    fromRoads.add(e1);

    Edge<T> e2 = new Edge<T>(from, name, weight);
    toRoads.add(e2);
  }


  public void disconnect(T from, T to) {
    if(!data.containsKey(from) || !data.containsKey(to))
      throw new NoSuchElementException("Element was not found in graph");
    Edge<T> e1 = getEdgeBetween(from, to);
    Edge<T> e2 = getEdgeBetween(to, from);
    List<Edge<T>> fromRoads = data.get(from);
    List<Edge<T>> toRoads = data.get(to);
    fromRoads.remove(e1);
    toRoads.remove(e2); 
  }

  public void remove(T item) {
    if(data.containsKey(item)){
      List<Edge<T>> edges = new ArrayList<Edge<T>>(data.get(item));
      for(Edge<T> edge: edges){
        disconnect(item, edge.getDestination());
      }
      data.remove(item); 
    }

  }

  public List<T> getNodes(){
    return new ArrayList<T> (data.keySet()); 
  }

  public List<Edge<T>> getEdgesFrom(T item){
    if(!data.containsKey(item))
      throw new NoSuchElementException("Element was not found in graph");
    return new ArrayList<Edge<T>>(data.get(item));
  }

  private void dfs2(T where, T fromWhere, Set<T> visited, Map<T, T> via){
    visited.add(where);
    via.put(where, fromWhere);
    for(Edge<T> e : data.get(where))
      if(!visited.contains(e.getDestination()))
        dfs2(e.getDestination(), where, visited, via);
  }

  public List<Edge<T>> getAnyPath(T from, T to){
    Map<T, T> via = new HashMap<T, T>();
    Set<T> besökta = new HashSet<T>();
    dfs2(from, null, besökta, via);
    List<Edge<T>> vägen = new ArrayList<Edge<T>>();
    T where = to;
    if(via.get(where) != null){
      while(!where.equals(from)){
        T whereFrom = via.get(where);
        Edge<T> e = getEdgeBetween(whereFrom, where);
        vägen.add(e);
        where = whereFrom;
      }
      Collections.reverse(vägen);
      return vägen;
    }
    return null;
  }

  public Edge<T> getEdgeBetween(T from, T to){
    if(!data.containsKey(from) || !data.containsKey(to))
      throw new NoSuchElementException("One of the nodes does not exist in the graph");
    for (Edge<T> e : data.get(from))
      if(e.getDestination().equals(to))
        return e;
    return null;
  }

  public void setConnectionWeight(T from, T to, int weight){
    if(!data.keySet().contains(from) && !data.keySet().contains(to)){
      throw new NoSuchElementException("One of the nodes does not exist in the graph");
    }

    if(weight < 0){
      throw new IllegalArgumentException("Weight can´t be less than 0");
    }

    Edge<T> edge1 = getEdgeBetween(from, to);
    Edge<T> edge2 = getEdgeBetween(to, from);
    if(edge1 != null && edge2 != null){
      edge1.setWeight(weight);
      edge2.setWeight(weight);
    }else{
      throw new NoSuchElementException("No connection exists between the nodes");
    }
  }

  @Override
  public void setConnectionName(T from, T to, String name) {
    if(!data.keySet().contains(from) && !data.keySet().contains(to)){
      throw new NoSuchElementException("One of the nodes does not exist in the graph");
    }

    if(name == null){
      throw new IllegalArgumentException("You must enter a name");
    }

    Edge<T> edge1 = getEdgeBetween(from, to);
    Edge<T> edge2 = getEdgeBetween(to, from);
    if(edge1 != null && edge2 != null){
      edge1.setName(name);
      edge2.setName(name);
    }else{
      throw new NoSuchElementException("No connection exists between the nodes");
    }

  }

  public int count(){
    return data.size();
  }

  public String toString(){
    String str = "";

    for(Map.Entry<T, List<Edge<T>>> me : data.entrySet()){
      T staden = me.getKey();
      List<Edge<T>> bågar = me.getValue();
      str +=  staden + ": " + bågar.toString() + "\n";
    }
    return str;
  }

}
