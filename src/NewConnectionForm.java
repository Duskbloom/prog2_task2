import javax.swing.*;

public class NewConnectionForm extends Form {

  /**
   * 
   */
  private static final long serialVersionUID = -2516318575209397812L;
  private JTextField nameField = new JTextField(10);
  private JTextField timeField = new JTextField(4);
  
  public NewConnectionForm(){
   JPanel p1 = new JPanel();
   JPanel p2 = new JPanel();
   setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
   JLabel nameLabel = new JLabel("Namn: ");
   JLabel timeLabel = new JLabel("Tid: ");
   p1.add(nameLabel);
   p1.add(nameField);
   p2.add(timeLabel);
   p2.add(timeField);
   this.add(p1);
   this.add(p2);   
  }
  
  public void setNameField(String str) {
    nameField.setText(str);
  }

  public void setTimeField(int i) {
    timeField.setText(""+i);
  }

  public String getName(){
    return nameField.getText();
  }
  public int getTime(){
    return Integer.parseInt(timeField.getText());
  }
  
  @Override
  public boolean isValidForm() {
    if (!isValidString(nameField.getText())){
      JOptionPane.showMessageDialog(null, "Du måste ange ett namn!");
      return false;
    }
    if (!isValidInteger(timeField.getText())){
      JOptionPane.showMessageDialog(null, "Du måste ange en tid i siffror");
      return false;
    }
    return true;
  }

}
