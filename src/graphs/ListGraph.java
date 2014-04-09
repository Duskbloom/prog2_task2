package graphs;
import java.util.*;

class ListGraph {
  private Map<Stad, List<Edge>> data = new HashMap<Stad, List<Edge>>();

  public void add(Stad ny){
    if(!data.containsKey(ny))
      data.put(ny, new ArrayList<Edge>() ); //eller throw exception
  }

  public void connect(Stad from, Stad to, String namn, int vikt){
    List<Edge> fromRoads = data.get(from);
    List<Edge> toRoads = data.get(to);

    if(fromRoads == null || toRoads == null)
      throw new NoSuchElementException("Stad finns ej vid connect");
    if(vikt<0)
      throw new IllegalArgumentException("Vikten måste vara positiv");
    if(pathExists(from, to))
      throw new IllegalStateException("Det finns redan en förbindelse mellan noderna");

    Edge e1 = new Edge(to, namn, vikt);
    fromRoads.add(e1);

    Edge e2 = new Edge(from, namn, vikt);
    toRoads.add(e2);
  }

  public List<Stad> getNodes(){
    return new ArrayList<Stad> (data.keySet()); 
  }

  public List<Edge> getEdgesFrom(Stad stad){
    return new ArrayList<Edge>(data.get(stad)); //ta hand om nullpointerexception
  }

  private void dfs(Stad where, Set<Stad> besökta){
    besökta.add(where);
    for(Edge e : data.get(where))
      if(!besökta.contains(e.getDestination()))
        dfs(e.getDestination(), besökta);
  }

  public boolean pathExists(Stad from, Stad to){
    Set<Stad> besökta = new HashSet<Stad>();
    dfs(from, besökta);
    return besökta.contains(to);
  }


  private void dfs2(Stad where, Stad fromWhere, Set<Stad> besökta, Map<Stad, Stad> via){
    besökta.add(where);
    via.put(where, fromWhere);
    for(Edge e : data.get(where))
      if(!besökta.contains(e.getDestination()))
        dfs2(e.getDestination(), where, besökta, via);
  }

  public List<Edge> getAnyPath(Stad from, Stad to){
    Map<Stad, Stad> via = new HashMap();
    Set<Stad> besökta = new HashSet<Stad>();
    dfs2(from, null, besökta, via);
    List<Edge> vägen = new ArrayList<Edge>();
    Stad where = to;
    while(!where.equals(from)){
      Stad whereFrom = via.get(where);
      Edge e = getEdgeBetween(whereFrom, where);
      vägen.add(e);
      where = whereFrom;
    }
    Collections.reverse(vägen);
    return vägen;
  }

  public Edge getEdgeBetween(Stad from, Stad to){
    for (Edge e : data.get(from))
      if(e.getDestination().equals(to))
        return e;
    return null;
  }

  public void setConnectionWeight(Stad from, Stad to, int weight){
    if(!data.keySet().contains(from) && !data.keySet().contains(to)){
      throw new NoSuchElementException("En eller båda av noderna existerar inte i grafen");
    }

    if(weight < 0){
      throw new IllegalArgumentException("vikt får inte vara mindre än 0");
    }

    Edge edge1 = getEdgeBetween(from, to);
    Edge edge2 = getEdgeBetween(to, from);
    if(edge1 != null && edge2 != null){
      edge1.setWeight(weight);
      edge2.setWeight(weight);
    }else{
      throw new NoSuchElementException("Det existerar ingen anslutning mellan städerna");
    }
  }

  public int count(){
    return data.size();
  }

  public String toString(){
    String str = "";

    for(Map.Entry<Stad, List<Edge>> me : data.entrySet()){
      Stad staden = me.getKey();
      List<Edge> bågar = me.getValue();
      str +=  staden + ": " + bågar.toString() + "\n";
    }
    return str;
  }
}
