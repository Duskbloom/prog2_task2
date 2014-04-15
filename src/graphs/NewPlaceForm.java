package graphs;
import javax.swing.*;

public class NewPlaceForm extends JPanel{
  private JTextField nameField = new JTextField(10);
  private JTextField timeField = new JTextField(5);
  
  public NewPlaceForm(){
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

  public JTextField getNameField() {
    return nameField;
  }

  public void setNameField(JTextField nameField) {
    this.nameField = nameField;
  }

  public JTextField getTimeField() {
    return timeField;
  }

  public void setTimeField(JTextField timeField) {
    this.timeField = timeField;
  }

}
