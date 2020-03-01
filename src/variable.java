import java.util.ArrayList;

public class variable {
	String cadena;
	ArrayList<Integer> lineas;
	int tipo, valor;
	
	public variable() {
		
	}
	
	public variable(String cadena, int linea, int tipo, int valor) {
		this.cadena = cadena;
		this.lineas.add(linea);
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public void addLinea(int linea) {
		this.lineas.add(linea);
	}
}
