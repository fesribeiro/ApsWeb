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
public class Veiculo {
	int id;
	String nomeUsuarioCarro;
	String fabricante;
	String nome;
	String cor;
	String marca;
	ArrayList veiculos;
	ArrayList veiculosNaoAlugados;
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	Connection conn;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getNomeUsuarioCarro() {
		return nomeUsuarioCarro;
	}
	
	public void setNomeUsuarioCarro(String nomeUsuarioCarro) {
		this.nomeUsuarioCarro = nomeUsuarioCarro;
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
	
	public ArrayList veiculosList(){
		try {
			veiculos = new ArrayList();
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("SELECT v.*, c.nome as nomeCliente FROM veiculos as v inner join clientes as c on v.alugado_id = c.id;");
			while (result.next()){
				Veiculo veiculo = new Veiculo();
				veiculo.setId(result.getInt("id"));
				veiculo.setNome(result.getString("nome"));
				veiculo.setMarca(result.getString("marca"));
				veiculo.setCor(result.getString("cor"));
				veiculo.setNomeUsuarioCarro(result.getString("nomeCliente"));					
				veiculos.add(veiculo);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return veiculos;
		
	}
	
	public ArrayList veiculosAlugadosList(){
		try {
			veiculosNaoAlugados = new ArrayList();
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("SELECT * from veiculos where alugado_id is null;");
			while (result.next()){
				Veiculo veiculo = new Veiculo();
				veiculo.setId(result.getInt("id"));
				veiculo.setNome(result.getString("nome"));
				veiculo.setMarca(result.getString("marca"));
				veiculo.setCor(result.getString("cor"));			
				veiculosNaoAlugados.add(veiculo);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return veiculosNaoAlugados;
		
	}
	
	public void removerLocacao(int id) {
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("UPDATE veiculos SET alugado_id = null where id = " + id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String editarVeiculo(int id)
	{
		Veiculo veiculo = null;
		try {
			conn = getDb();
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("select * from veiculos where id = " + id);
			result.next();
			veiculo = new Veiculo();
			veiculo.setId(result.getInt("id"));
			veiculo.setNome(result.getString("nome"));
			veiculo.setMarca(result.getString("marca"));
			veiculo.setCor(result.getString("cor"));
			sessionMap.put("edit", veiculo);
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "/alugarVeiculo.xhtml?faces-redirect=true";
		
	}
	
	public String alugarVeiculo(Veiculo edit, User user)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("UPDATE veiculos SET alugado_id=? where id=?");
			stmt.setInt(1, user.id);
			stmt.setInt(2, edit.id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "index.xhtml?faces-redirect=true";
		
		
	}
	
}
