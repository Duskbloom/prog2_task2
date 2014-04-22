import java.io.*;
public class MarkerData<T> implements Serializable{
  private T item;
  private int x;
  private int y;

  public MarkerData(Marker<T> marker){
    this.item = marker.getItem();
    this.x = marker.getX() + 25;
    this.y = marker.getY() + 15;
  }

  public T getItem(){
    return item;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public Marker<T> getMarker(){
    return new Marker<T>(x, y, item);
  }
}
