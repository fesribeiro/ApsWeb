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
	int quantidade;
	float totalGanho;
	String ano;
	String preco;
	String nomeUsuarioCarro;
	String fabricante;
	String nome;
	String cor;
	String marca;
	ArrayList veiculos;
	ArrayList veiculosNaoAlugados;
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	Connection conn;
	
	public void setTotalGanho(float totalGanho) {
		this.totalGanho = totalGanho;
	}
	
	public float getTotalGanho() {
		return totalGanho;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public String getPreco() {
		return preco;
	}
	
	public void setPreco(String preco) {
		this.preco = preco;
	}
	
	public String getAno() {
		return ano;
	}
	
	public void setAno(String ano) {
		this.ano = ano;
	}
	
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
	
	public ArrayList veiculoCompradosList(){
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
				veiculo.setAno(result.getString("ano"));
				veiculo.setPreco(result.getString("preco"));
				veiculo.setNomeUsuarioCarro(result.getString("nomeCliente"));					
				veiculos.add(veiculo);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return veiculos;
		
	}
	
	public ArrayList veiculoLivresList(){
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
				veiculo.setAno(result.getString("ano"));
				veiculo.setPreco(result.getString("preco"));
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
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String comprarVeiculo(int id)
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
			veiculo.setAno(result.getString("ano"));
			veiculo.setPreco(result.getString("preco"));
			sessionMap.put("edit", veiculo);
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "comprar-veiculo.xhtml?faces-redirect=true";
		
	}
	
	public String alterarVeiculo(int id)
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
			veiculo.setAno(result.getString("ano"));
			veiculo.setPreco(result.getString("preco"));
			sessionMap.put("edit", veiculo);
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "editar-veiculo.xhtml?faces-redirect=true";
		
	}
	
	
	
	public String criarVeiculo()
	{
		System.out.println("teste");
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO veiculos (nome, cor, marca, ano, preco) VALUES (?,?,?,?,?)");
			stmt.setString(1, nome);
			stmt.setString(2, marca);
			stmt.setString(3, cor);
			stmt.setString(4, ano);
			stmt.setString(5, preco);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "veiculos.xhtml?faces-redirect=true";
	}
	
	
	public void removerVeiculo(int id)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM veiculos where id = " + id);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public String alterarDadosVeiculo(Veiculo edit)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("UPDATE veiculos SET cor=?, marca=?, nome=?, ano=?, preco=? where id=?");
			stmt.setString(1, edit.cor);
			stmt.setString(2, edit.marca);
			stmt.setString(3, edit.nome);
			stmt.setString(4, edit.ano);
			stmt.setString(5, edit.preco);
			stmt.setInt(6, edit.id);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "veiculos.xhtml?faces-redirect=true";
		
	}
	
	
	public String comprarVeiculo(Veiculo edit, User user)
	{
		try {
			conn = getDb();
			PreparedStatement stmt = conn.prepareStatement("UPDATE veiculos SET alugado_id=? where id=?");
			stmt.setInt(1, user.id);
			stmt.setInt(2, edit.id);
			stmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return "veiculos.xhtml?faces-redirect=true";
		
	}
	
	public ArrayList vendasRealizadas(){
		try {
			veiculos = new ArrayList();
			conn = getDb();		
			Statement stmt = getDb().createStatement();
			ResultSet result = stmt.executeQuery("SELECT nome, count(nome) as quantidade,  count(nome) * preco as Total from veiculos where alugado_id > 0 group by nome;");
			while (result.next()){
				Veiculo veiculo = new Veiculo();
				veiculo.setNome(result.getString("nome"));
				veiculo.setQuantidade(result.getInt("quantidade"));					
				veiculo.setTotalGanho(result.getFloat("Total"));
				veiculos.add(veiculo);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return veiculos;
		
	}

	
	
}
