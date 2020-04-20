
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;



public class Tateti extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton[] botons = new JButton[9];
	private JButton boton,boton2,elegir,nuevo,delete;
	private JPanel panel1, panel2;
	private boolean turno = true;
	private JLabel label1,label2,label3,label4;
	private JTextField edit1;
	private JMenuBar barra;
	private JMenu menu;
	private JMenuItem bd,ingreso;
	private ConsultaSQL sql;
	private JComboBox<String>nombres1,nombres2,posEliminados;
	private Font font = new Font("Agency FB",Font.BOLD, 20);
	private Font font2 = new Font("Arial",Font.BOLD, 15);
	private int cont = 0;
	private String dato,pj1="",pj2="",del;
	public Tateti() {
		

		
		//propiedades de la ventana
		this.setTitle("Ta te ti");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		
		//instancia del oyente
		Oyente o = new Oyente();
		
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3,3));
		getContentPane().add(panel1);
		panel1.setBounds(0, 0, 230, 311);
		
		//agregar botones al panel 1
		for(int i=0;i<botons.length;i++) {
			botons[i] = new JButton("");
			botons[i].addActionListener(o);
			panel1.add(botons[i]);
			botons[i].setBackground(Color.WHITE);
			botons[i].setFont(font2);
			
		}
		
		
		panel2 = new JPanel();
		panel2.setBackground(new Color(142, 170, 185));
		getContentPane().add(panel2);
		
		panel2.setLayout(null);
		
		//menu y barra de menu
		barra = new JMenuBar();
		menu = new JMenu("Opciones");
		bd = new JMenuItem("Datos");
		ingreso = new JMenuItem("Usuarios");
		barra.add(menu);
		barra.setSize(155, 20);
		barra.setLocation(230, 0);
		menu.add(bd);
		menu.add(ingreso);
		
		//ingreso y seleccion de usuarios
		ingreso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame login = new JFrame();
				JPanel  loginPanel = new JPanel();
	
				
				sql = new ConsultaSQL();
				sql.consultaTabla();
			
			
				
				login.setSize(300, 300);
				login.setVisible(true);
				login.setResizable(false);
				login.setLocationRelativeTo(null);
				login.getContentPane().add(loginPanel);
				loginPanel.setLayout(new FlowLayout(1,1,50));
	
				nombres1 = new JComboBox<String>();
				loginPanel.add(nombres1);
				nombres2 = new JComboBox<String>();
				loginPanel.add(nombres2);
				
				elegir = new JButton("    Confirmar   ");
				loginPanel.add(elegir);
				label3 = new JLabel("Nuevo usuario: ");
				loginPanel.add(label3);
				edit1 = new JTextField(10);
				loginPanel.add(edit1);
				nuevo = new JButton("Aceptar");
				loginPanel.add(nuevo);
				label4 = new JLabel("Eliminar usuario: ");
				loginPanel.add(label4);
				posEliminados = new JComboBox<String>();
				//cargar combo con usuarios a eliminar
				for(int i=0;i<sql.usu.size();i++) {
					posEliminados.addItem(sql.usu.get(i));
					
				}
				loginPanel.add(posEliminados);
				delete = new JButton("Eliminar");
				loginPanel.add(delete);
				
				//boton eliminar
				delete.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						del = (String) posEliminados.getSelectedItem();
						if(sql.eliminarUsuario(del))
							JOptionPane.showMessageDialog(null, "Usuario eliminado");
						else
							JOptionPane.showMessageDialog(null, "No se elimino usuario");
						posEliminados.removeItem(del);
					}
				});
				
				nuevo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dato = edit1.getText();
						System.out.println("dato "+dato);
						if(sql.insertarUsuario(dato) == true) {
							JOptionPane.showMessageDialog(null,"Usuario agregado.");
						}else
							JOptionPane.showMessageDialog(null,"No se agrego usuario.");
					
						
					}
				});
				
				elegir.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						pj1 = (String) nombres1.getSelectedItem();
						pj2 = (String) nombres2.getSelectedItem();
						login.setVisible(false);
						SwingUtilities.updateComponentTreeUI(panel1);
						panel1.validate();
						if(cont%2==0)
							label2.setText("Jugador X: "+pj1);
						else
							label2.setText("Jugador O: "+pj2);
						
						reiniciarJuego();
							
						
					}
				});
				nombres1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						Object n1 = nombres1.getSelectedItem();
						nombres2.removeItem(n1);
						
					}
				});
				
				nombres2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Object n2 = nombres2.getSelectedItem();
						nombres1.removeItem(n2);
					}
				});
				
				for(int i=0;i<sql.usu.size();i++) {
					nombres1.addItem(sql.usu.get(i));
					nombres2.addItem(sql.usu.get(i));
					
				}
				
			}
		});
		
		//datos de la bd
		bd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				JFrame puntos = new JFrame();
				JPanel  panelpuntos = new JPanel();
				
				
				
				DefaultTableModel model = new DefaultTableModel(); 
			
				
				sql = new ConsultaSQL();
				sql.consultaTabla();
		
				panelpuntos.setLayout(new FlowLayout());
				puntos.setSize(400, 350);
				puntos.setVisible(true);
				puntos.setResizable(false);
				puntos.setLocationRelativeTo(null);
				JTable tabla =new JTable(model);
				model.addColumn("Usuario");
				model.addColumn("Victorias");
				model.addColumn("Derrotas");
				for (int j = 0; j < sql.cod.size(); j++) {
					model.addRow(new Object[] {sql.usu.get(j),sql.vic.get(j),sql.der.get(j)});
				}
					
				
				puntos.getContentPane().add(panelpuntos);
				//panelpuntos.setBackground(Color.black);
				panelpuntos.add(tabla);
			
				
			}
		});
		
		//boton reiniciar
		boton = new JButton("Reiniciar");
		boton.setSize(100, 30);
		boton.setLocation(257, 205);
		boton.addActionListener(o);
		boton.setFont(font);
		
		//boton salir
		boton2 = new JButton("Salir");
		boton2.setSize(100, 30);
		boton2.setLocation(257, 255);
		boton2.addActionListener(o);
		boton2.setFont(font);
		
		//label turno/ganador
		label1 = new JLabel("Turno:");
		label1.setSize(100, 100);
		label1.setLocation(275,5);
		label1.setFont(font);
		label1.setForeground(Color.BLACK);
		
		
		//label jugador x/o
		label2 = new JLabel("Jugador X");
		label2.setSize(200, 100);
		label2.setLocation(250,35);
		label2.setFont(font);
		label2.setForeground(Color.BLACK);
		
		panel2.setLayout(null);
		
		//agregar los componentes al panel 2
		
		panel2.add(label1);
		panel2.add(label2);
		panel2.add(boton);
		panel2.add(boton2);
		panel2.add(barra);
	}
	

	
	public void terminarJuego() {
		for(int i=0;i<botons.length;i++)
			botons[i].setEnabled(false);
	}
	
	public boolean verificarGanador(String op) {
		if(botons[0].getText() == op && botons[1].getText() == op && botons[2].getText() == op) {
			botons[0].setBackground(Color.GREEN);
			botons[1].setBackground(Color.GREEN);
			botons[2].setBackground(Color.GREEN);
			return true;
		}else
			if(botons[3].getText() == op && botons[4].getText() == op && botons[5].getText() == op) {
				botons[3].setBackground(Color.GREEN);
				botons[4].setBackground(Color.GREEN);
				botons[5].setBackground(Color.GREEN);
				return true;
				
			}else
				if(botons[6].getText() == op && botons[7].getText() == op && botons[8].getText() == op) {
					botons[6].setBackground(Color.GREEN);
					botons[7].setBackground(Color.GREEN);
					botons[8].setBackground(Color.GREEN);
					return true;
				}else
					if(botons[0].getText() == op && botons[3].getText() == op && botons[6].getText() == op) {
						botons[0].setBackground(Color.GREEN);
						botons[3].setBackground(Color.GREEN);
						botons[6].setBackground(Color.GREEN);
						return true;
					}else
						if(botons[1].getText() == op && botons[4].getText() == op && botons[7].getText() == op) {
							botons[1].setBackground(Color.GREEN);
							botons[4].setBackground(Color.GREEN);
							botons[7].setBackground(Color.GREEN);
							return true;
						}else
							if(botons[2].getText() == op && botons[5].getText() == op && botons[8].getText() == op) {
								botons[2].setBackground(Color.GREEN);
								botons[5].setBackground(Color.GREEN);
								botons[8].setBackground(Color.GREEN);
								return true;
							}else
								if(botons[0].getText() == op && botons[4].getText() == op && botons[8].getText() == op) {
									botons[0].setBackground(Color.GREEN);
									botons[4].setBackground(Color.GREEN);
									botons[8].setBackground(Color.GREEN);
									return true;
								}else
									if(botons[6].getText() == op && botons[4].getText() == op && botons[2].getText() == op) {
										botons[6].setBackground(Color.GREEN);
										botons[4].setBackground(Color.GREEN);
										botons[2].setBackground(Color.GREEN);
										return true;
									}	
		
		return false;
		
	}
	
	public void reiniciarJuego() {
		for(int i=0;i<botons.length;i++) {
			botons[i].setText("");
			botons[i].setEnabled(true);
			botons[i].setBackground(Color.WHITE);
			label1.setText("Turno:");
		}	
	}
	
	private void colorearEmpate() {
		for(int i=0;i<botons.length;i++) {
			botons[i].setBackground(Color.RED);
		}	
	}

	class Oyente implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton boton=(JButton) e.getSource();
			
			if(e.getActionCommand() == "Reiniciar") {
				cont = 0;
				reiniciarJuego();
				turno = true;
				label2.setText("Jugador X");			}
			
			if(e.getActionCommand() == "Salir") {
				System.exit(JFrame.DISPOSE_ON_CLOSE);
			}
			
				for(int i=0;i<botons.length;i++) {
					if(boton.equals(botons[i]) && botons[i].getText() == "") {
						if(turno == true) {
							botons[i].setText("X");
							turno = false;
							label2.setText("Jugador O: "+pj2);
							botons[i].setEnabled(false);
						}else {
							botons[i].setText("O");
							turno = true;
							label2.setText("Jugador X: "+pj1);
							botons[i].setEnabled(false);
						}
						
					}
					//verifica ganador o empate
					if(verificarGanador("X") == true) {
						label1.setText("Ganador:");
						label2.setText("Jugador X: "+pj1);
						verificarGanador("X");
						terminarJuego();	
						label2.setLocation(250, 35);
					}
					if(verificarGanador("O") == true) {
						label1.setText("Ganador:");
						label2.setText("Jugador O: "+pj2);
						label2.setLocation(250, 35);
						verificarGanador("O");
						terminarJuego();
					}
					if(cont == botons.length-1) {
						label1.setText("Empate!");
						label2.setText("");
						colorearEmpate();
						terminarJuego();
					}
					
					
				}

				cont++;
			}
				
		}
		
		
		
	}