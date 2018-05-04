package janelaAgenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//classe auxiliar
public class Agenda {
	private HashMap cPessoa = new HashMap();
	private int novoCodigo = 1;
	
	public void add(Pessoa p){
		p.setCodigo(Integer.toString(novoCodigo));
		this.cPessoa.put(p.getCodigo(), p);
		novoCodigo+=1;
	}
	
	public boolean remove(Object codigo){
		if (this.cPessoa.containsKey(codigo)){
			this.cPessoa.remove(codigo);
			return true;
		}
		else
			return false;
	}
	
	public Pessoa getPessoa(String cod){
		return (Pessoa)this.cPessoa.get(cod);
	}
	
    public ArrayList getList(){
    	return new ArrayList(cPessoa.values());
    }
    
    public String ToString(){
    	String str = "";
    	
    	Iterator it = this.cPessoa.values().iterator();
    	
    	while (it.hasNext()){
    		Pessoa p = (Pessoa)it.next();
    		str += p.ToString();
    	}
    	return str;
    }
}
