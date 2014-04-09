
public class Graf {
  public static void main (String[] args){
    ListGraph g = new ListGraph();
    
    Stad s1 = new Stad("Kista");
    Stad s2 = new Stad("Tensta");
    Stad s3 = new Stad("Akalla");
    
    g.add(s1);
    g.add(s2);
    g.add(s3);
    
    g.connect(s1,  s2, "Buss", 25);
    g.connect(s2,  s3, "Tunnelbana", 12);
    
    System.out.println(g.getAnyPath(s1, s3));
  }
}
