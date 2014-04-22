import java.io.*;
import java.util.*;
import graphs.*;
import javax.swing.ImageIcon;

public class StorageObject implements Serializable{
  private ImageIcon background;
  private Graph<City> graph;
  private ArrayList<MarkerData<City>> markerData;
  
  public StorageObject(ImageIcon background, Graph<City> graph, ArrayList<Marker<City>> markers){
    this.background = background;
    this.graph = graph;
    this.markerData = new ArrayList<MarkerData<City>>();
    for(Marker<City> m: markers){
      markerData.add(m.getData());
    }
  }

  public ImageIcon getBackground(){
    return background;
  }

  public Graph<City> getGraph(){
    return graph;
  }

  public ArrayList<Marker<City>> getMarkers(){
    ArrayList<Marker<City>> markers = new ArrayList<Marker<City>>();
    for(MarkerData<City> data: markerData){
      markers.add(data.getMarker());
    }
    return markers;
  }
}
