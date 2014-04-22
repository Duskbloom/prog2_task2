
import graphs.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener, MapClickedListener{

  /**
   * 
   */
  private static final long serialVersionUID = -8555779389804914547L;
  private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem, findPathMI, showConnectionMI, newPlaceMI, newConnectionMI, changeConnectionMI;
  private JButton findPathB, showConnectionB, newPlaceB, newConnectionB, changeConnectionB;
  private final JFileChooser fc = new JFileChooser();
  private FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Bilder", "jpg", "png", "gif");
  private FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("Kartor", "map");
  private File file;
  private ImageIcon bild = new ImageIcon();
  MapPanel map;

  public MainWindow(){
    super("PathFinder");
    setJMenuBar(buildMenuPanel());
    add(buildButtonPanel(), BorderLayout.NORTH);
    add(map = new MapPanel(bild), BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private JPanel buildButtonPanel() {
    JPanel panel = new JPanel();

    findPathB = new JButton("Hitta väg");
    showConnectionB = new JButton("Visa förbindelse");
    newPlaceB = new JButton("Ny plats");
    newConnectionB = new JButton("Ny förbindelse");
    changeConnectionB = new JButton("Ändra förbindelse");

    panel.add(findPathB);
    panel.add(showConnectionB);
    panel.add(newPlaceB);
    panel.add(newConnectionB);
    panel.add(changeConnectionB);

    findPathB.addActionListener(this);
    showConnectionB.addActionListener(this);
    newPlaceB.addActionListener(this);
    newConnectionB.addActionListener(this);
    changeConnectionB.addActionListener(this);
    return panel;
  }

  private JMenuBar buildMenuPanel(){
    JMenuBar menubar = new JMenuBar();
    JMenu arkivMenu = new JMenu("Arkiv");   
    JMenu opMenu = new JMenu("Operationer");
    menubar.add(arkivMenu); 
    menubar.add(opMenu);

    newMenuItem = new JMenuItem("Ny");
    openMenuItem = new JMenuItem("Öppna");
    saveMenuItem = new JMenuItem("Spara");
    saveAsMenuItem = new JMenuItem("Spara som...");
    exitMenuItem = new JMenuItem("Avsluta");
    arkivMenu.add(newMenuItem);
    arkivMenu.add(openMenuItem);
    arkivMenu.add(saveMenuItem);
    arkivMenu.add(saveAsMenuItem);
    arkivMenu.add(exitMenuItem);

    findPathMI = new JMenuItem("Hitta väg");
    showConnectionMI = new JMenuItem("Visa förbindelse");
    newPlaceMI = new JMenuItem("Ny plats");
    newConnectionMI = new JMenuItem("Ny förbindelse");
    changeConnectionMI = new JMenuItem("Ändra förbindelse");
    opMenu.add(findPathMI);
    opMenu.add(showConnectionMI);
    opMenu.add(newPlaceMI);
    opMenu.add(newConnectionMI);
    opMenu.add(changeConnectionMI);

    newMenuItem.addActionListener(this);
    openMenuItem.addActionListener(this);
    saveMenuItem.addActionListener(this);
    saveAsMenuItem.addActionListener(this);
    exitMenuItem.addActionListener(this);
    findPathMI.addActionListener(this);
    showConnectionMI.addActionListener(this);
    newPlaceMI.addActionListener(this);
    newConnectionMI.addActionListener(this);
    changeConnectionMI.addActionListener(this);

    return menubar;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newMenuItem){
      fc.setFileFilter(imageFilter);
      int answer = fc.showOpenDialog(MainWindow.this);
      if(answer != JFileChooser.APPROVE_OPTION)
        return;
      bild = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
      if(map!=null)
        remove(map);
      map = new MapPanel(bild);
      map.setMapClickedListener(this);
      add(map);
      validate();
      pack();
      setLocationRelativeTo(null);
    }
    if (e.getSource() == openMenuItem){
      fc.setFileFilter(mapFilter);
      int answer = fc.showOpenDialog(MainWindow.this);
      if(answer != JFileChooser.APPROVE_OPTION)
        return;
      file = fc.getSelectedFile();//test
      map = Storage.<MapPanel>load(file);
      if(map!=null)
        remove(map);
      map = new MapPanel(bild);
      add(map);
      validate();
      pack();
      setLocationRelativeTo(null);
    }
    if (e.getSource() == saveMenuItem){
      fc.setFileFilter(mapFilter);
      int answer = fc.showSaveDialog(MainWindow.this);
      if(answer != JFileChooser.APPROVE_OPTION)
        return;
      Storage.save(map, fc.getSelectedFile());
    }
    if (e.getSource() == saveAsMenuItem){
      int returnVal = fc.showSaveDialog(this);
    }
    if (e.getSource() == exitMenuItem){
      /* if(){
        int answer = JOptionPane.showConfirmDialog(this, "Du har osparade ändringar. Vill du spara?", "Varning", JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer == JOptionPane.YES_OPTION)
          save(file);
      }*/
      System.exit(0);
    }

    if (e.getSource() == findPathMI || e.getSource() == findPathB){
    }
    if (e.getSource() == showConnectionMI || e.getSource() == showConnectionB){
      ArrayList<Marker<City>> markers = map.getSelectedMarkers();
      if(markers.size() == 2){
        Edge<City> connection = map.getGraph().getEdgeBetween(markers.get(0).getItem(), markers.get(1).getItem());
        if(connection != null)
          JOptionPane.showMessageDialog(null, "Från " + markers.get(0).getItem() + " " + connection);
        else
          JOptionPane.showMessageDialog(null, "Det finns ingen förbindelse mellan platserna.");
      }
      else
        JOptionPane.showMessageDialog(null, "Du måste välja två platser");
    }
    if (e.getSource() == newPlaceMI || e.getSource() == newPlaceB){
      map.setActive(true);
    }
    if (e.getSource() == newConnectionMI || e.getSource() == newConnectionB){
      NewConnectionForm form = new NewConnectionForm();
      ArrayList<Marker<City>> markers = map.getSelectedMarkers();
      if(markers.size() == 2){
        Edge<City> connection = map.getGraph().getEdgeBetween(markers.get(0).getItem(), markers.get(1).getItem());
        if(connection == null){
          showNewConnectionForm(form);
        }
        else
          JOptionPane.showMessageDialog(null, "Det finns redan en förbindelse mellan platserna.");
      }
      else
        JOptionPane.showMessageDialog(null, "Du måste välja två platser");
      
    }
    //
    if (e.getSource() == changeConnectionMI || e.getSource() == changeConnectionB){
      NewConnectionForm form = new NewConnectionForm();
      ArrayList<Marker<City>> markers = map.getSelectedMarkers();
      if(markers.size() == 2){
        Edge<City> connection = map.getGraph().getEdgeBetween(markers.get(0).getItem(), markers.get(1).getItem());
        if(connection != null){
          form.setNameField(connection.getNamn());
          form.setTimeField(connection.getWeight());
          showChangeConnectionForm(form);
        }
        else
          JOptionPane.showMessageDialog(null, "Det finns ingen förbindelse mellan platserna.");
      }
      else
        JOptionPane.showMessageDialog(null, "Du måste välja två platser");
    }
  }

  private void showNewConnectionForm(NewConnectionForm form){
    ArrayList<Marker<City>> markers = map.getSelectedMarkers();
    int result = JOptionPane.showConfirmDialog(null, form, "Ny förbindelse", JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION);
    if(result == JOptionPane.OK_OPTION){
      if(form.isValidForm()){
        map.getGraph().connect(markers.get(0).getItem(), markers.get(1).getItem(), form.getName(), form.getTime());
        map.revalidate();
        map.repaint();
      } else 
        showNewConnectionForm(form);
    }
  }
  
  private void showChangeConnectionForm(NewConnectionForm form){
    ArrayList<Marker<City>> markers = map.getSelectedMarkers();
    int result = JOptionPane.showConfirmDialog(null, form, "Ändra förbindelse", JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION);
    if(result == JOptionPane.OK_OPTION){
      if(form.isValidForm()){
        map.getGraph().setConnectionName(markers.get(0).getItem(), markers.get(1).getItem(), form.getName());
        map.getGraph().setConnectionWeight(markers.get(0).getItem(), markers.get(1).getItem(), form.getTime());
        map.revalidate();
        map.repaint();
      } else 
        showNewConnectionForm(form);
    }
  }


  @Override
  public void mapClicked(int x, int y) {
    String namn = JOptionPane.showInputDialog("Namn: ");
    City stad = new City(namn);
    Marker<City> m = new Marker<City>(x, y, stad);
    m.setMarkerListener(map);
    map.getGraph().add(stad);
    map.addMarker(m);
  }

  @Override
  public void markerClicked(int x, int y) {
    Component c = map.getComponentAt(x, y);
    if(c instanceof Marker) //INGET FUNKAR D:
      ((Marker<?>) c).setActive(true);    
  }


  //    if(m1==null){
  //      m1 = m;
  //      m.setActive(true);
  //    }else if(m2==null){
  //      m2 = m;
  //      m.setActive(true);

}
