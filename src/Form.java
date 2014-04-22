import javax.swing.*;

abstract class Form extends JPanel{
  /**
   * 
   */
  private static final long serialVersionUID = 8539110994206508820L;

  protected boolean isValidInteger(String input){
    try{
      Integer.parseInt(input);
    }catch(NumberFormatException e){
      return false;
    }
    return true;
  }

  protected boolean isValidDouble(String input){
    try{
      Double.parseDouble(input);
    }catch(NumberFormatException e){
      return false;
    }
    return true;
  }
  
  protected boolean isValidString(String input){
    return input != null && input.length() > 0;
  }

  public abstract boolean isValidForm();
}
