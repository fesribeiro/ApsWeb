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
//	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
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
	
	public String getUsuarioLocado(int id) {
		try {
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("select nome from clientes where id = " + id);
			while(result.next()) {				
				this.setNomeUsuarioCarro(result.getString("nome"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return this.nomeUsuarioCarro;		
	}
	
	public void alugarVeiculo() {
		
	}
	
	
	public ArrayList veiculosList(){
		try {
			veiculos = new ArrayList();
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("select * from veiculos");
			while (result.next()){
				Veiculo veiculo = new Veiculo();
				veiculo.setId(result.getInt("id"));
				veiculo.setNome(result.getString("nome"));
				veiculo.setMarca(result.getString("marca"));
				veiculo.setCor(result.getString("cor"));
				if(result.getInt("alugado_id") != 999)
				{
					veiculo.setNomeUsuarioCarro(this.getUsuarioLocado(result.getInt("alugado_id")));					
				} else {
					veiculo.setNomeUsuarioCarro("Veiculo Disponivel");
				}
				veiculos.add(veiculo);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return veiculos;
		
	}
	
	
}
