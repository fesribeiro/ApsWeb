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
	String endereco;
	ArrayList usuarios;
	Connection conn;
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
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
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getSexo() {
		return sexo;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revendaveiculos", "root", "f0rt@123");
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
			usuario.setId(result.getInt("id"));
			usuario.setNome(result.getString("nome"));
			usuario.setTelefone(result.getString("telefone"));
			usuario.setCpf(result.getString("cpf"));
			usuario.setEndereco(result.getString("endereco"));
			usuario.setSexo(result.getString("sexo"));
			usuarios.add(usuario);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return usuarios;
		
	}
	
	
	public ArrayList usersNameList(){
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
	
	public String criarUsuario()
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO clientes (nome, cpf, telefone, endereco, sexo) VALUES (?,?,?,?,?)");
			stmt.setString(1, nome);
			stmt.setString(2, cpf);
			stmt.setString(3, telefone);
			stmt.setString(4, endereco);
			stmt.setString(5, sexo);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "clientes.xhtml?faces-redirect=true";
	}
	
	public void removerUsuario(int id)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM clientes where id = " + id);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public String alterarUsuario(int id)
	{
		User usuario = null;
		try {
			conn = getDb();
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("select * from clientes where id = " + id);
			result.next();
			usuario = new User();
			usuario.setId(result.getInt("id"));
			usuario.setNome(result.getString("nome"));
			usuario.setEndereco(result.getString("endereco"));
			usuario.setTelefone(result.getString("telefone"));
			usuario.setCpf(result.getString("cpf"));
			usuario.setSexo(result.getString("sexo"));
			sessionMap.put("edit", usuario);
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "editar-usuario.xhtml?faces-redirect=true";
		
	}
	
	public String alterarDadosUsuario(User edit)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("UPDATE clientes SET nome=?, cpf=?, telefone=?, endereco=?, sexo=? where id=?");
			stmt.setString(1, edit.nome);
			stmt.setString(2, edit.cpf);
			stmt.setString(3, edit.telefone);
			stmt.setString(4, edit.endereco);
			stmt.setString(5, edit.sexo);
			stmt.setInt(6, edit.id);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "clientes.xhtml?faces-redirect=true";
		
	}
	
}

