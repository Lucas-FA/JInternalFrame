package janelaAgenda;

import java.util.Comparator;

//classe auxiliar
public class Pessoa {
	private String codigo;
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	
	public static final Comparator ORDEM_CODIGO = new Comparator(){
		public int compare(Object o1, Object o2){
			Pessoa p1 = (Pessoa)o1;
			Pessoa p2 = (Pessoa)o2;
			
            return p1.getCodigo().compareTo(p2.getCodigo());
		}
	};
	
	public Pessoa(String newNome, String newTelefone, String newEmail, String newEndereco){
		this.nome = newNome;
		this.telefone = newTelefone;
		this.email = newEmail;
		this.endereco = newEndereco;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String newCodigo){
		this.codigo = newCodigo;
	}
	
	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}
	
	public void setValores(String[] dados){
		this.nome = dados[0];
		this.telefone = dados[1];
		this.email = dados[2];
		this.endereco = dados[3];
	}
	
	public String ToString(){
		return "Nome: " + this.getNome() + "\n" + "Telefone: " + this.getTelefone() + "\n" + "E-mail: " + this.getEmail() + "\n" + "Endereco: " + this.getEndereco() + "\n";
	}
}
