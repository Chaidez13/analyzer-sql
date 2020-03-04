import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class parsero {
	
	public String texto;
	public int code, ap;
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
		int error = 200;
		
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
			error = getError(x);
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
		
		switch (x) {
		case "Q":
			if(k.equals("s"))
				return "JFfAs";
			break;
		case "A":
			if(k.equals("*"))
				return "*";
			if(k.equals("i"))
				return "B";
			break;
		case "B":
			if(k.equals("i"))
				return "DC";
			break;
		case "C":
			if(k.equals("i"))
				return "Ei";	
			break;
		case "D":
			if(k.equals(",")) 
				return"B,";
			if(k.equals("f"))  
				return "";
			break;
		case "E":
			if(k.equals(".")) 
				return "i.";
			if(k.equals(",") || k.equals("f") || k.equals("r") || k.equals("n") 
					|| k.equals("y") || k.equals("o") || k.equals(")")) 
				return "";
			break;
		case "F":
			if(k.equals("i")) 
				return "HG";
			break;
		case "G":
			if(k.equals("i")) 
				return "Ii";
			break;
		case "H":
			if(k.equals(",")) 
				return "F,";
			if(k.equals("w") || k.equals(")") || k.equals("$")) 
				return "";
			break;
		case "I":
			if(k.equals("i")) 
				return "i";
			if(k.equals(",") || k.equals("w") || k.equals(")") || k.equals("$")) 
				return "";
			break;
		case "J":
			if(k.equals("$") || k.equals(")"))
				return "";
			if(k.equals("w"))  
				return "Kw";
			break;
		case "K":
			if(k.equals("i")) 
				return "VL";
			break;
		case "V":
			if(k.equals("y") || k.equals("o"))
				return "KP";
			if(k.equals(")") || k.equals("$"))
				return "";
			break;
		case "L":
			if(k.equals("i")) 
				return "MC";
			break;
		case "M":
			if(k.equals("r"))
				return "ON";
			if(k.equals("n"))
				return ")Q(n";
			break;
		case "N":
			if(k.equals("r"))
				return "r";
			break;
		case "O":
			if(k.equals("i"))
				return "C";
			if(k.equals("'"))
				return "'R'";
			if(k.equals("d"))
				return "T";
			break;
		case "P":
			if(k.equals("y")) 
				return "y";
			if(k.equals("o")) 
				return "o";
			break;
		case "R":
			if(k.equals("a"))
				return "a";
			break;
		case "T":
			if(k.equals("d"))
				return "d";
			break;
		default:
			return "~";
		}
		return "~";
	}
	
	public int getError(String x) {
		switch (x) {
		case "Q":  case "D": case "E": case "H": case "J": case "V": case "O":
			return 201;
		case "A": case "B": case "C": case "F": case "G": case "I": case "K": case "L": case "P":
			return 204;
		case "M": case "N":
			return 208;
		case "R": case "'":
			return 205;
		case "T":
			return 206;
		default:
			return 210;
		}
	}
}
