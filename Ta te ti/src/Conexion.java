import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/tateti";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "root";
	
	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("Error al cargar controlador");
			e.printStackTrace();
		}
	}
	public Connection conectarBD() {
		Connection conexion = null;
		try {
			
			conexion = DriverManager.getConnection(URL,USUARIO,PASSWORD);
			System.out.println("Conexion establecida");
		}
		catch(SQLException e) {
			System.out.println("Error en la conexion");
		}
		return conexion;
	}
	
	
}
