
import graphs.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener, MapClickedListener{

  /**
   * 
   */
  private static final long serialVersionUID = -8555779389804914547L;
  private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem, findPathMI, showConnectionMI, newPlaceMI, newConnectionMI, changeConnectionMI, removeConnectionMI, removePlaceMI;
  private JButton findPathB, showConnectionB, removePlaceB, newPlaceB, newConnectionB, changeConnectionB, removeConnectionB;
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
    newConnectionB = new JButton("Ny förbindelse");
    showConnectionB = new JButton("Visa förbindelse");
    changeConnectionB = new JButton("Ändra förbindelse");
    removeConnectionB = new JButton("Ta bort förbindelse");
    newPlaceB = new JButton("Ny plats");
    removePlaceB = new JButton("Ta bort plats");

    panel.add(findPathB);
    panel.add(newConnectionB);
    panel.add(showConnectionB);
    panel.add(changeConnectionB);
    panel.add(removeConnectionB);
    panel.add(newPlaceB);
    panel.add(removePlaceB);

    findPathB.addActionListener(this);
    showConnectionB.addActionListener(this);
    newPlaceB.addActionListener(this);
    newConnectionB.addActionListener(this);
    changeConnectionB.addActionListener(this);
    removeConnectionB.addActionListener(this);
    removePlaceB.addActionListener(this);
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
    removeConnectionMI = new JMenuItem("Ta bort förbindelse");
    removePlaceMI = new JMenuItem("Ta bort plats");

    opMenu.add(findPathMI);
    opMenu.add(newConnectionMI);
    opMenu.add(showConnectionMI);
    opMenu.add(changeConnectionMI);
    opMenu.add(removeConnectionMI);
    opMenu.add(newPlaceMI);
    opMenu.add(removePlaceMI);

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
    removeConnectionMI.addActionListener(this);
    removePlaceMI.addActionListener(this);

    return menubar;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newMenuItem){
      newMap();
    }
    if (e.getSource() == openMenuItem){
      openMap();
    }
    if (e.getSource() == saveMenuItem){
      saveMap();
    }
    if (e.getSource() == saveAsMenuItem){
      int returnVal = fc.showSaveDialog(this);
    }
    if (e.getSource() == exitMenuItem){
      System.exit(0);
    }
    if (e.getSource() == findPathMI || e.getSource() == findPathB){
      findPath();
    }
    if (e.getSource() == newPlaceMI || e.getSource() == newPlaceB){
      map.setActive(true);
    }
    if (e.getSource() == newConnectionMI || e.getSource() == newConnectionB){
      newConnection();
    }
    if (e.getSource() == changeConnectionMI || e.getSource() == changeConnectionB){
      changeConnection();
    }
    if (e.getSource() == removeConnectionB || e.getSource() == removeConnectionMI){
      removeConnection();
    }
    if (e.getSource() == removePlaceB || e.getSource() == removePlaceMI){
      removePlace();
    }
  }

  private void newMap(){
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

  private void openMap(){
    fc.setFileFilter(mapFilter);
    int answer = fc.showOpenDialog(MainWindow.this);
    if(answer != JFileChooser.APPROVE_OPTION)
      return;
    file = fc.getSelectedFile();//test
    StorageObject data  = Storage.<StorageObject>load(file);
    if(map!=null)
      remove(map);
    map = new MapPanel(data.getBackground(), data.getGraph());
    map.setMapClickedListener(this);
    for(Marker<City> m: data.getMarkers()){
      map.addMarker(m);
    }
    add(map);
    validate();
    pack();
    setLocationRelativeTo(null);
  }

  private void saveMap(){
    fc.setFileFilter(mapFilter);
    int answer = fc.showSaveDialog(MainWindow.this);
    if(answer != JFileChooser.APPROVE_OPTION)
      return;
    Storage.save(new StorageObject(map.getMap(), map.getGraph(), map.getMarkers()), fc.getSelectedFile());
  }

  private void findPath(){
    int time = 0;
    ArrayList<Marker<City>> markers = map.getSelectedMarkers();
    if(markers.size() == 2){
      List<Edge<City>> connection = map.getGraph().getAnyPath(markers.get(0).getItem(), markers.get(1).getItem());
      if(connection == null)
        JOptionPane.showMessageDialog(null, "Det finns ingen väg mellan platserna.");
      else{
        printPath(markers.get(0).getItem(), connection);
      }
    }
    else
      JOptionPane.showMessageDialog(null, "Du måste välja två platser");
  }
  private void printPath(City from, List<Edge<City>> edges){
    int time = 0;
    String output = "Från " + from;
    for(int i = 0; i < edges.size(); i++){
      time +=  edges.get(i).getWeight();
      if(i == 0){
        output += " med " + edges.get(i).getNamn() + " till " + edges.get(i).getDestination().getNamn() + "\n";
      } else {
        output += "Från " + edges.get(i - 1).getDestination().getNamn() + " med " + edges.get(i).getNamn() + " till " + edges.get(i).getDestination().getNamn() + "\n";
      }
    }
    output += "Total tid: " + time;
    JOptionPane.showMessageDialog(null, output);
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

  private void newConnection(){
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

  private void changeConnection(){
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

  private void removeConnection(){
    ArrayList<Marker<City>> markers = map.getSelectedMarkers();
    if(markers.size() == 2){
      Edge<City> connection = map.getGraph().getEdgeBetween(markers.get(0).getItem(), markers.get(1).getItem());
      if(connection != null){
        map.getGraph().disconnect(markers.get(0).getItem(), markers.get(1).getItem());
        map.revalidate();
        map.repaint();
      }
      else
        JOptionPane.showMessageDialog(null, "Det finns ingen förbindelse mellan platserna.");
    }
    else
      JOptionPane.showMessageDialog(null, "Du måste välja två platser");
  }

  private void removePlace(){
    ArrayList<Marker<City>> markers = map.getSelectedMarkers();
    if(markers.size() == 1){
      int result = JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort " + markers.get(0).getItem(), "Ta bort plats", JOptionPane.OK_CANCEL_OPTION);
      if(result == JOptionPane.OK_OPTION){
        map.removeMarker(markers.get(0));
        map.revalidate();
        map.repaint();
      }
    }
  }


  @Override
  public void mapClicked(int x, int y) {
    String namn = JOptionPane.showInputDialog("Namn: ");
    City stad = new City(namn);
    Marker<City> m = new Marker<City>(x, y, stad);
    map.getGraph().add(stad);
    map.addMarker(m);
  }

  @Override
  public void markerClicked(int x, int y) {
    Component c = map.getComponentAt(x, y);
    if(c instanceof Marker) 
      ((Marker<?>) c).setActive(true);    
  }
}
