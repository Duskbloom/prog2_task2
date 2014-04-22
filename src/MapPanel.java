import graphs.Edge;
import graphs.Graph;
import graphs.ListGraph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements MouseListener, MarkerListener<City> {
  private ImageIcon bild;
  private boolean active = false;
  private MapClickedListener mapClickedListener;
  private ArrayList<Marker<City>> selectedMarkers = new ArrayList<Marker<City>>(2);
  private ArrayList<Marker<City>> markers = new ArrayList<Marker<City>>();
  private Graph<City> graph;

  public MapPanel(ImageIcon bild){
    graph = new ListGraph<City>();
    this.bild = bild;
    int w = bild.getIconWidth();
    int h = bild.getIconHeight();
    Dimension d = new Dimension(w, h);
    setPreferredSize(d);
    setMaximumSize(d);
    setMinimumSize(d);
    setLayout(null);
    graph = new ListGraph<City>();
    this.addMouseListener(this);
    this.revalidate();
  }

  public MapClickedListener getMapClickedListener() {
    return mapClickedListener;
  }
  public void setMapClickedListener(MapClickedListener mapClickedListener) {
    this.mapClickedListener = mapClickedListener;
  }

  public Graph<City> getGraph(){
    return graph;
  }

  public ArrayList<Marker<City>> getSelectedMarkers() {
    return selectedMarkers;
  }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(bild.getImage(), 0, 0, getWidth(), getHeight(), this);
    paintConnections(g);
  }

  private void paintConnections(Graphics g){
    g.setColor(Color.CYAN);
    for(Marker<City> marker: markers){
      List<Edge<City>> connections = graph.getEdgesFrom(marker.getItem());
      for(Edge<City> edge: connections){
        Marker<City> other = getMarkerForCity(edge.getDestination());
        int x1 = marker.getX() + 25;
        int y1 = marker.getY() + 15;
        int x2 = other.getX() + 25;
        int y2 = other.getY() + 15;
        g.drawLine(x1, y1, x2, y2);
      }
    }
  }

  private Marker<City> getMarkerForCity(City c){
    for(Marker<City> marker: markers){
      if(marker.getItem().equals(c)){
        return marker;
      }
    }
    return null;
  }

  public void setActive(boolean active){
    this.active = active;
    if(active)
      this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    else
      this.setCursor(Cursor.getDefaultCursor());
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    if(active){
      if(mapClickedListener != null)
        mapClickedListener.mapClicked(arg0.getX(), arg0.getY());
      setActive(false);
    }
    else{
      mapClickedListener.markerClicked(arg0.getX(), arg0.getY());  
    }
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
  }
  @Override
  public void mouseExited(MouseEvent arg0) {
  }
  @Override
  public void mousePressed(MouseEvent arg0) {
  }
  @Override
  public void mouseReleased(MouseEvent arg0) {
  }

  public void addMarker(Marker<City> marker){
    graph.add(marker.getItem());
    markers.add(marker);
    add(marker);
  }

  @Override
  public Component add(Component c){
    Component ret = super.add(c);
    revalidate();
    repaint();
    return ret;
  }

  @Override
  public void markerClicked(Marker<City> marker){
    if(!marker.isActive()){
      if(selectedMarkers.size() < 2){
        selectedMarkers.add(marker);
        marker.setActive(!marker.isActive());
      }
    }
    else{
      selectedMarkers.remove(marker);
      marker.setActive(!marker.isActive());
    }    
  }  
}
