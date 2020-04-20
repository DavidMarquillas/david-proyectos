import javax.swing.JFrame;

public class Hilo implements Runnable{
	private JFrame ventana;
	
	public Hilo(JFrame v) {
	
		this.ventana = v;
	}
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		if(!Thread.currentThread().isInterrupted())
			ventana.setVisible(true);
	}

}
