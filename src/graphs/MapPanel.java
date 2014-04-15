package graphs;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
  private ImageIcon bild;
  
  public MapPanel(String file){
    bild = new ImageIcon(file);
    int w = bild.getIconWidth();
    int h = bild.getIconHeight();
    Dimension d = new Dimension(w, h);
    setPreferredSize(d);
    setMaximumSize(d);
    setMinimumSize(d);
  }
  
}
