package graphs;
import java.util.*;

public interface Graph<T>{
  public void add(T item);
  public void connect(T from, T to, String name, int weight);
  public List<T> getNodes();
  public List<Edge<T>> getEdgesFrom(T item);
  public List<Edge<T>> getAnyPath(T from, T to);
  public Edge<T> getEdgeBetween(T from, T to);
  public void setConnectionWeight(T from, T to, int weight);
}