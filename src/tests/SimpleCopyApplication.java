package tests;

import static javax.swing.SwingUtilities.invokeAndWait;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class SimpleCopyApplication extends JFrame{
  private static final long serialVersionUID = 1L;

  public SimpleCopyApplication() {

    final JTextField textField = new JTextField("textToCopy");
    JButton button = new JButton("copyButton");
    final JLabel label = new JLabel("copiedText");
    button.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		label.setText(textField.getText());
	}
} );
  

    //add(textField);
    add(button);
    //add(label);

    pack();
  }

  public static void main(String[] args) throws InvocationTargetException, InterruptedException {
    invokeAndWait(new Runnable() {

      public void run() {
        JFrame frame = new SimpleCopyApplication();
        frame.setVisible(true);
      }
    });
  }
}