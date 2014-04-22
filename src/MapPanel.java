import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements MouseListener, MarkerListener<City> {
  private ImageIcon bild;
  private boolean active = false;
  private MapClickedListener mapClickedListener;
  private ArrayList<Marker<City>> selectedMarkers = new ArrayList<Marker<City>>(2);
  private ArrayList<Marker<City>> markers = new ArrayList<Marker<City>>();

  public MapPanel(ImageIcon bild){
    this.bild = bild;
    int w = bild.getIconWidth();
    int h = bild.getIconHeight();
    Dimension d = new Dimension(w, h);
    setPreferredSize(d);
    setMaximumSize(d);
    setMinimumSize(d);
    setLayout(null);
    this.addMouseListener(this);
    this.revalidate();
  }

  public MapClickedListener getMapClickedListener() {
    return mapClickedListener;
  }
  public void setMapClickedListener(MapClickedListener mapClickedListener) {
    this.mapClickedListener = mapClickedListener;
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
      System.out.println(marker);
      Marker<City> other = getMarkerForCity(marker.getItem());
      g.drawLine(marker.getX(), marker.getY(), other.getX(), other.getY());
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
