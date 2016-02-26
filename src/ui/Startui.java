package ui;

public class Startui {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MainWindow window =new MainWindow();
		MainWindowController control= new MainWindowController();
		control.showWindow();
		System.out.println(control.getNameFromPlayer());
		//window.setVisible(true);
	}
}
