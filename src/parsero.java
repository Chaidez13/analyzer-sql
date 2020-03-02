import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class parsero {
	
	public String texto;
	public int code, ap;
	private int error;
	JScrollPane caja5, caja2;
	JLabel label1;
	DefaultTableModel modeloEr;
	ArrayList<token> list;
	
	public parsero(ArrayList<token> list) {
		this.list = list;
	}
	
	public void setElements(JScrollPane caja5, JScrollPane caja2, JLabel label1,
			DefaultTableModel modeloEr) {
		this.caja2 = caja2;
		this.caja5 = caja5;
		this.label1 = label1;
		this.modeloEr = modeloEr;
	}
	
	public String doParser() {
		Stack<String> pila = new Stack<String>();
		error = 200;
		
		pila.push("$");
		pila.push("Q");
		list.add(new token("$", 0));
		
		/*
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).key + " ");
		}
		*/

		int apun = 0;
		String x="", k="";
		do {
			x = pila.pop();
			k = list.get(apun).key;
			// System.out.println(x + " " + k);
			if(x.equals("$") || !Character.isUpperCase(x.charAt(0))) {
				if (x.equals(k)) 
					apun++;
				else {
					ap = apun;
					break;
				}
			} else {
				String prod = getTabla(x, k);
				// System.out.println("PROD: " + prod);
				if(!prod.equals("~")) {
					for (int i = 0; i < prod.length(); i++) {
						pila.push(prod.substring(i, i+1));
					}
				}else {
					ap = apun;
					break;
				}
			}
		} while(!x.equals("$"));
		
		if(!x.equals("$")) {
			int linea;
			if(apun==0)
				linea = 1;
			else
				linea = list.get(apun-1).linea;
			
			caja5.setVisible(true);
			caja2.setBounds(10, 400, 660, 440);
			label1.setBounds(10, 370, 100, 20);
			String val;
		    switch (error) {
				case 201: val = "Se esperaba Palabra Reservada"; break;
				case 204: val = "Se esperaba Identificador"; break;
				case 205: val = "Se esperaba Delimitador"; break;
				case 206: val = "Se esperaba Constante"; break;
				case 207: val = "Se esperaba Operador"; break;
				case 208: val = "Se esperaba Operador Relacional"; break;
				default: val = "Error no Especificado"; break;
			}
			Object datosEr[] = {error, linea, val, x};
			modeloEr.addRow(datosEr);
			
			return "";
		}
		
		return "200: Sin error";
			
	}

	public String getTabla(String x, String k) {
		String prod = "~";
		
		if(x.equals("Q")) { // Regla Q
			prod = "JFfAs";
			this.error = 201;
		}if(x.equals("A")) { // Regla A 
			if(k.equals("*"))
				prod = "*";
			if(k.equals("i"))
				prod = "B";
			this.error = 204;
		}if(x.equals("B")) { // Regla B 
			if(k.equals("i"))
				prod = "DC";
		}if(x.equals("C")) { // Regla C 
			if(k.equals("i"))
				prod = "Ei";
			this.error = 204;
		}if(x.equals("D")) { // Regla D 
			if(k.equals(",")) 
				prod = "B,";
			if(k.equals("f"))  
				prod = "";
			this.error = 201;
		}if(x.equals("E")) { // Regla E
			if(k.equals(".")) 
				prod = "i.";
			if(k.equals(",") || k.equals("f") || k.equals("r") || k.equals("n") 
					|| k.equals("y") || k.equals("o") || k.equals(")")) 
				prod = "";
			this.error = 201;
		}if(x.equals("F")) { // Regla F
			if(k.equals("i")) 
				prod = "HG";
			this.error = 204;
		}if(x.equals("H")) { // Regla H
			if(k.equals(",")) 
				prod = "F,";
			if(k.equals("w") || k.equals(")") || k.equals("$")) 
				prod = "";
			this.error = 201;
		}if(x.equals("G")) { // Regla G
			if(k.equals("i")) 
				prod = "Ii";
			this.error = 204;
		}if(x.equals("I")) { // Regla I
			if(k.equals("i")) 
				prod = "i";
			if(k.equals(",") || k.equals("w") || k.equals(")") || k.equals("$")) 
				prod = "";
			this.error = 204;
		}if(x.equals("J")) { // Regla J
			if(k.equals("$") || k.equals(")"))
				prod = "";
			if(k.equals("w"))  
				prod = "Kw";
			this.error = 201;
		}if(x.equals("K")) { // Regla K
			if(k.equals("i")) 
				prod = "VL";
			this.error = 204;
		}if(x.equals("V")) { // Regla V
			if(k.equals("y") || k.equals("o"))
				prod = "KP";
			if(k.equals(")") || k.equals("$"))
				prod = "";
			this.error = 201;
		}if(x.equals("L")) { // Regla L
			if(k.equals("i")) 
				prod = "MC";
			this.error = 204;
		}if(x.equals("M")) { // Regla M
			if(k.equals("r"))
				prod = "ON";
			if(k.equals("n"))
				prod = ")Q(n";
			this.error = 208;
		}if(x.equals("N")) { // Regla N
			if(k.equals("r"))
				prod = "r";
			this.error = 208;
		}if(x.equals("O")) { // Regla O
			if(k.equals("i"))
				prod = "C";
			if(k.equals("'"))
				prod = "'R'";
			if(k.equals("d"))
				prod = "T";
			this.error = 201;
		}if(x.equals("P")) { // Regla P
			if(k.equals("y")) 
				prod = "y";
			if(k.equals("o")) 
				prod = "o";
			this.error = 204;
		}if(x.equals("R")) { // Regla R
			if(k.equals("a"))
				prod = "a";
			this.error = 205;
		}if(x.equals("T")) { // Regla T
			if(k.equals("d"))
				prod = "d";
		}
		
		return prod;
	}
	
}
