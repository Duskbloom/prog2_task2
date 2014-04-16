import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Marker<T> extends JPanel{
  private T item;
  private int y;
  private int x;
  private JLabel name;
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
