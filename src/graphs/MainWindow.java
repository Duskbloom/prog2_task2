package graphs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener{
  
  private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem, findPathMI, showConnectionMI, newPlaceMI, newConnectionMI, changeConnectionMI;
  private JButton findPathB, showConnectionB, newPlaceB, newConnectionB, changeConnectionB;
  private final JFileChooser fc = new JFileChooser();
  Graph graph;
  MapPanel p;
  
  String imageName;// = fc.getSelectedFile().getAbsolutePath();
  
  public MainWindow(){
    super("PathFinder");
    setJMenuBar(buildMenuPanel());
    add(buildButtonPanel(), BorderLayout.NORTH);
    //add(p = new MapPanel(imageName), BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  private JPanel buildMapPanel() {
    JPanel panel = new JPanel();
    return panel;
  }

  private JPanel buildButtonPanel() {
    JPanel panel = new JPanel();
    
    FileNameExtensionFilter fnef = new FileNameExtensionFilter("Bilder", "jpg", "png", "gif");
    FileNameExtensionFilter fnef2 = new FileNameExtensionFilter("Kartor", "map");
    fc.addChoosableFileFilter(fnef);
    fc.addChoosableFileFilter(fnef2);
    
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
      graph = new ListGraph<Stad>();
    }
    if (e.getSource() == openMenuItem){
      int answer = fc.showOpenDialog(MainWindow.this);
      if(answer != JFileChooser.APPROVE_OPTION)
        return;
      load(fc.getSelectedFile());
      String fnamn = fc.getSelectedFile().getAbsolutePath();//fast skaffa bildfilen och inte sökvägen??
      if(p!=null)
        remove(p);
      p = new MapPanel(fnamn);
      add(p);
      validate();
      pack();
    }
    if (e.getSource() == saveMenuItem){
      save(fc.getSelectedFile());
    }
    if (e.getSource() == saveAsMenuItem){
      int returnVal = fc.showSaveDialog(this);
    }
    if (e.getSource() == exitMenuItem){
      //kolla om sparat
      System.exit(0);
    }
    
    if (e.getSource() == findPathMI || e.getSource() == findPathB){
    }
    if (e.getSource() == showConnectionMI || e.getSource() == showConnectionB){
    }
    if (e.getSource() == newPlaceMI || e.getSource() == newPlaceB){
      //klicka var den ska hamna, rita en cirkel fär
      showNewPlaceForm(form);
      graph.add(item);
    }
    if (e.getSource() == newConnectionMI || e.getSource() == newConnectionB){
    }
    if (e.getSource() == changeConnectionMI || e.getSource() == changeConnectionB){
    }
  }
  
  private void showNewPlaceForm

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

  private  void save(File file) {
    try {
        FileOutputStream f_out = new FileOutputStream(file);
        ObjectOutputStream o_out = new ObjectOutputStream(f_out);
        //o_out.writeObject(saveObject); Hur ska vi samla sparinfo?
        o_out.close();
        f_out.close();
      } catch (FileNotFoundException e) {
      } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      }
  }
  
  
  
}
