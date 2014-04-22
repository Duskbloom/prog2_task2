import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Marker<T> extends JPanel implements MouseListener{
  /**
   * 
   */
  private static final long serialVersionUID = -2787103106463702589L;
  private T item;
  private int y;
  private int x;
  private MarkerListener<T> markerListener;
  private boolean active = false;
    
  Marker(int x, int y, T item){
    this.x = x - 25;
    this.y = y - 15;
    this.item = item;
    Dimension d = new Dimension(50,50);
    setOpaque(false);
    setSize(d);
    setLocation(this.x, this.y);
    addMouseListener(this);
  }
  public int getY() {
    return y;
  }
  public void setY(int y) {
    this.y = y;
  }
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  public T getItem(){
    return item;
  }
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
    this.revalidate();
    this.repaint();
  }

  public void setMarkerListener(MarkerListener<T> listener){
    this.markerListener = listener;
  }

  public MarkerData<T> getData(){
    return new MarkerData<T>(this);
  }

  //public boolean contains(int x, int y){
  //  return x>10&&x<40&&y>10&&x<40; //ändra storlek på clickable area
  //  //setBounds för att sättta bounds som den ska ritas inom
//  }
  protected void paintComponent(Graphics g){
    if(!active){
      g.setColor(Color.CYAN);
      g.fillOval(10, 0, 30, 30);
    }
    else{
      g.setColor(Color.RED);
      g.fillOval(10, 0, 30, 30);
    }
    g.setColor(Color.BLACK);
    g.drawString(item.toString(), 10, 45);
  }

  @Override
  public void mouseExited(MouseEvent event){
  }

  @Override
  public void mouseClicked(MouseEvent event){
    if(markerListener != null){
      markerListener.markerClicked(this);
    }
  }

  @Override
  public void mousePressed(MouseEvent event){
  }

  @Override
  public void mouseEntered(MouseEvent event){
  }

  @Override
  public void mouseReleased(MouseEvent event){
  }
}
