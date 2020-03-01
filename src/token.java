import java.util.ArrayList;

public class token {
	public String key;
	public ArrayList<String> primeros;
	public ArrayList<String> siguientes;
	
	public token(String key) {
		this.key = key;
	}
	
	public void addPrimero(String primero) {
		this.primeros.add(primero);
	}
}
