import graphs.Graph;

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
  private Graph<City> graph;
  
  
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
  
  public Graph<City> getGraph(){
    return graph;
  }
 
  public ArrayList<Marker<City>> getSelectedMarkers() {
    return selectedMarkers;
  }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(bild.getImage(), 0, 0, getWidth(), getHeight(), this);
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
    // TODO Auto-generated method stub
    
  }
  @Override
  public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  @Override
  public void mousePressed(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  @Override
  public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
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
