
import graphs.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener, MapClickedListener{
  
  private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem, findPathMI, showConnectionMI, newPlaceMI, newConnectionMI, changeConnectionMI;
  private JButton findPathB, showConnectionB, newPlaceB, newConnectionB, changeConnectionB;
  private final JFileChooser fc = new JFileChooser();
  private FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Bilder", "jpg", "png", "gif");
  private FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("Kartor", "map");
  private Graph<City> graph;
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
      graph = new ListGraph<City>();
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
      load(file);
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
      save(graph);
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
      
      Edge<City> connection = graph.getEdgeBetween(markers.get(0).getItem(), markers.get(1).getItem());
      JOptionPane.showMessageDialog(null, "Från " + markers.get(0).getItem() + " " + connection);
      
    }
    if (e.getSource() == newPlaceMI || e.getSource() == newPlaceB){
      map.setActive(true);
    }
    if (e.getSource() == newConnectionMI || e.getSource() == newConnectionB){
      NewConnectionForm form = new NewConnectionForm();
      showNewConnectionForm(form);
      }
      //
    if (e.getSource() == changeConnectionMI || e.getSource() == changeConnectionB){
      
    }
  }
  
private void showNewConnectionForm(NewConnectionForm form){
  ArrayList<Marker<City>> markers = map.getSelectedMarkers();
  int result = JOptionPane.showConfirmDialog(null, form, "Ny förbindelse", JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION);
  if(result == JOptionPane.OK_OPTION){
    if(form.isValidForm())    
      graph.connect(markers.get(0).getItem(), markers.get(1).getItem(), form.getName(), form.getTime());
    else
      showNewConnectionForm(form);
  }
}

  @SuppressWarnings("unchecked")
  private static <T> T load(File file) {
    try {
      FileInputStream f_in = new FileInputStream(file);
      ObjectInputStream o_in = new ObjectInputStream(f_in);
      Object read = o_in.readObject();
      o_in.close();
      f_in.close();
      return (T) read;
      } catch (FileNotFoundException e) {
          System.out.println(file + " not found");
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }    
    return null;
  }

  private void save(Graph<City> g) {
   int answer = fc.showSaveDialog(MainWindow.this);
   if(answer==JFileChooser.APPROVE_OPTION){   
     File f = fc.getSelectedFile();
     String filename = f.getAbsolutePath();
     
        try {
          FileOutputStream f_out = new FileOutputStream(file);
          ObjectOutputStream o_out = new ObjectOutputStream(f_out);
          o_out.writeObject(graph); //Hur ska vi samla sparinfo?
          o_out.close();
        } catch (FileNotFoundException e) {
          System.err.println("Filen går ej att skriva!");
        } catch (IOException e) {
          System.err.println("IOException!");
        e.printStackTrace();
        }
     }
  }

  @Override
  public void mapClicked(int x, int y) {
    String namn = JOptionPane.showInputDialog("Namn: ");
    City stad = new City(namn);
    Marker<City> m = new Marker<City>(x, y, stad);
    m.setMarkerListener(map);
    graph.add(stad);
    map.add(m);
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
