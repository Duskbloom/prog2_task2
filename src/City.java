import java.io.Serializable;


public class City implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = -2057187219518259898L;
  private String namn;
  
  City(String namn){
    this.namn = namn;
  }
  
  public String getNamn(){
    return namn;
  }
  
  public String toString(){
    return namn;
  }
}
