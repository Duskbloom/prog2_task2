package graphs;
import java.io.Serializable;
import java.util.*;

public interface Graph<T> extends Serializable{
  public void add(T item);
  public void connect(T from, T to, String name, int weight);
  public void disconnect(T from, T to);
  public void remove(T item);
  public List<T> getNodes();
  public List<Edge<T>> getEdgesFrom(T item);
  public List<Edge<T>> getAnyPath(T from, T to);
  public List<Edge<T>> getPath(T from, T to);
  public Edge<T> getEdgeBetween(T from, T to);
  public void setConnectionWeight(T from, T to, int weight);
  public void setConnectionName(T from, T to, String name);
}
