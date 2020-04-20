	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.sql.PreparedStatement;
import java.util.ArrayList;


public class ConsultaSQL {
	
	private int id;
	private String usuario;
	private int victorias;
	private int derrotas;
	ArrayList<String>cod =new ArrayList<String>();
	ArrayList<String>usu =new ArrayList<String>();
	ArrayList<String>vic =new ArrayList<String>();
	ArrayList<String>der =new ArrayList<String>();
	private int i=0;
	Conexion c ;
	Connection con = null;
	Statement stm = null;
	ResultSet rs = null; 
	PreparedStatement ps = null;
	java.sql.CallableStatement cts;
	 
	public ConsultaSQL() {

	}
	
		
	boolean  insertarUsuario(String dato) {
		c = new Conexion();

		boolean res=false;
		try {
			con = c.conectarBD();
		
			ps = con.prepareStatement("INSERT INTO usuario(id,nombre,victorias,derrotas) VALUE(default,?,0,0)");
			ps.setString(1, dato);
			ps.executeUpdate();
			res = true;
			
		}catch(SQLException e){
			res = false;
		}
		return res;
	}
	
	boolean eliminarUsuario(String dato) {
		c = new Conexion();

		boolean res=false;
		try {
			con = c.conectarBD();
		
			ps = con.prepareStatement("DELETE FROM usuario where nombre = ?");
			ps.setString(1, dato);
			ps.executeUpdate();
			res = true;
			
		}catch(SQLException e){
			res = false;
		}
		return res;
	}
	
	public void consultaTabla() {
			c = new Conexion();
		
			try {
				con = c.conectarBD();
				stm = con.createStatement();
				rs = stm.executeQuery("select * from usuario");
				
				while(rs.next()) {
					 this.id = rs.getInt(1);
					 this.usuario = rs.getString(2);
					 this.victorias = rs.getInt(3);
					 this.derrotas = rs.getInt(4);
					 
					 this.cod.add(Integer.toString(id)); 
					 this.usu.add(usuario);
					 this.vic.add(Integer.toString(victorias));
					 this.der.add(Integer.toString(derrotas));
					 i++;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) {
						rs.close();
					}
					
					if(stm != null) {
						stm.close();
					}	
					
					if(con != null){
						con.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	
		
		public int getId() {
			return id;
		}

		public String getUsuario() {
			return usuario;
		}

		public int getVictorias() {
			return victorias;
		}

		public int getDerrotas() {
			return derrotas;
		}
	
}	

