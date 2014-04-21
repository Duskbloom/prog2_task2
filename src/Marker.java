import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Marker<T> extends JPanel{
  private T item;
  private int y;
  private int x;
  private boolean active = false;
    
  Marker(int x, int y, T item){
    this.x = x-25;
    this.y = y-15;
    this.item = item;
    Dimension d = new Dimension(50,50);
    setPreferredSize(d);
    setLocation(x, y);
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
}
