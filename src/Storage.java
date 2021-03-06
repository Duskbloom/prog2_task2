import java.io.*;
public class Storage{
  @SuppressWarnings("unchecked")
  public static <T> T load(File file) {
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

  public static void save(StorageObject object, File f) {
    try {
      if(!f.getName().endsWith(".map"))
        f = new File(f.getAbsolutePath() + ".map");
      FileOutputStream f_out = new FileOutputStream(f);
      ObjectOutputStream o_out = new ObjectOutputStream(f_out);
      o_out.writeObject(object);
      o_out.close();
    } catch (FileNotFoundException e) {
      System.err.println("Filen går ej att skriva!");
    } catch (IOException e) {
      System.err.println("IOException!");
      e.printStackTrace();
    }
  }
}
