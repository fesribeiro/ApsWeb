import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.context.FacesContext;

@ManagedBean
public class User {
	int id;
	String nome;
	String sexo;
	String cpf;
	String telefone;
	ArrayList usuarios;
	Connection conn;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Connection getDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revendaveiculos", "root", "");
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}	
	
	public ArrayList usersList(){
		try {
			usuarios = new ArrayList();
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("select * from clientes");
			while (result.next()){
			User usuario = new User();
				if(result.getInt("id") != 0) {				
					usuario.setId(result.getInt("id"));
					usuario.setNome(result.getString("nome"));					
					usuarios.add(usuario);
				}
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return usuarios;
		
	}
}

