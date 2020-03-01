import java.util.ArrayList;
import java.util.Stack;

public class parsero {
	
	public String texto;
	public int code, ap;
	private token[] tokens = new token[20];
	
	public parsero(ArrayList<String> list) {
		Stack<String> pila = new Stack<String>();

		pila.push("$");
		pila.push("P");
		list.add("$");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		
		/*
		System.out.println("");
		int apun = 0;
		String x="", k="";
		do {
			x = pila.pop();
			k = lista.get(apun);
			System.out.println(x + " " + k);
			if(x.equals("(")||x.equals("I")||x.equals(")")||x.equals("H")||x.equals(";")||x.equals("$")) {
				if (x.equals(k)) 
					apun++;
				else {
					ap = apun;
					break;
				}
			} else {
				String prod = getTabla(x, k);
				System.out.println("PROD: " + prod);
				if(!prod.equals("")) {
					if(!prod.equals("λ")) {
						for (int i = 0; i < prod.length(); i++) {
							pila.push(prod.substring(i, i+1));
						}
					}
				}else {
					ap = apun;
					break;
				}
			}
		} while(!x.equals("$"));
		
		System.out.println(x);
		switch (x) {
		case "F":
			code = 203;
			texto = "Falta Identificador/Constante";
			break;
		case "G":
			code = 202;
			texto = "Falta Operador";
			break;
		case "$":
			code = 200;
			texto = "Sin Error";
			break;
		default:
			code = 201;
			texto = "Falta Delimitador";
			break;
		}
		*/
	}
	
	public void fillTokens() {

	}
	
	public String getTabla(String x, String k) {
		String prod = "";
		if(x.equals("P")) {
			if(k.equals("("))
				prod = "P;AF(";
			if(k.equals("I"))
				prod = "P;F";
			if(k.equals("$"))
				prod = "λ";
		}if(x.equals("A")) {
			if(k.equals("("))
				prod = ")P";
			if(k.equals("I"))
				prod = ")P";
			if(k.equals(")"))
				prod = "G)";
		}if(x.equals("F")) {
			if(k.equals("("))
				prod = "AF(";
			if(k.equals("I"))
				prod = "GO";
		}if(x.equals("G")) {
			if(k.equals(")"))
				prod = "λ";
			if(k.equals("H"))
				prod = "FR";
			if(k.equals(";"))
				prod = "λ";
			if(k.equals("$"))
				prod = "λ";
		}if(x.equals("O")) {
			if(k.equals("I"))
				prod = "I";
		}if(x.equals("R")) {
			if(k.equals("H"))
				prod = "H";
		}
		return prod;
	}
}
