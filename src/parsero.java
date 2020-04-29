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
	
	private int[] indicesRawX = {4,8,10,11,12,13,14,15,16,18,19,20,22,24,25,26,27,50,51,53,54,61,62,72,199};
	private int[] indicesRawY = {200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,700};
	private ArrayList<Integer> indicesX;
	private ArrayList<Integer> indicesY;
	// La tabla Sintactica
	private String [][] tablaSintactica = { 
			//200
			{"", "", "", "", "", "", "", "", "16 17 4 52 202 53 55 201", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "200", "", "", "", "", "", "", "", "211", "", "", "", "", "", "",  "", "99"},
			{"4 203 52 61 53 204 205", "", "", "", 	"", "", "", "",	"", "", "", "", "", "", "", "", "", "", "", "", "", "", "",  "", ""},
			{"", "", "", "", "", "", "", "", "", "18", "19", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "20 21", "", "", "", "", "", "99", "", "", "", "", "",  "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "50 206", "", "99", "", "", "", "", ""},
			{"202", "", "", "", "", "", "", "",	"", "", "", "", "207", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "22 4 208 52 4 53 209", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "24 23", "25 23", "", "", "", "", "", 	"", "", "",  "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "26 4 52 4 53 210", "", "50 207", "", "99", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "50 207", "", "99", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "27 28 4 29 52 212 53 55 215", "", "", "", "", "", "",  "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "213 214", "213 214", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "54 62 54", "61", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "50 212", "", "99", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "200", "", "", "", "", "", "", "", "211", "", "", "", "", "", "", "", "99"},
			//300
			{"", "", "10 301 11 306 310","", "", "", "", "", "", "", "", "", "", "", "", "", "",  "", "", "", "", "", "", "", ""},
			{"302", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "72", ""},
			{"304 303","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "99", "", "", "", "", "", "", "", "", "", "", "", "", "", "50 302", "", "", "", "", "", "", "99"},
			{"4 305", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 	""},
			{"", "99", "", "99", "", "99", "99", "99", "", "", "", "", "", "", "", "", "", "99", "51 4", "99", "", "", "", "", "99"},
			{"308 307", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "99", "", "", "", "", "", "", "", "", "", "", "", "", "50 306", "", "99", "", "", "", "", "99"},
			{"4 309", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 	""},
			{"4", "", "", "", "99", "", "", "",	"", "", "", "", "", "", "", "", "", "99", "", "99", "", "", "", "", "99"},
			{"", "", "", "", "12 311", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "99", "", "", "", "", "99"},
			{"313 312", "", "", "", "", "", "", "",	"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "317 311", "317 311", "", "", "", "", "", "", "", "", "",	"", "", "99", "", "", "", "", "99"},
			{"304 314", "", "", "", "", "", "", "",	"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "315 316", "", "", "", "13 52 300 53",	"", "",	"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "8", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"304", "",	"", "", "", "", "", "",	"", "", "", "", "", "", "", "", "", "", "", "", "54 318 54","319", "", "", ""},
			{"", "", "", "", "", "", "14", "15", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "62", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "61", "", "", ""},
			{"300", "", "", "", "", "", "", "",	"200", "", "", "", "", "", "", "", "211", "", "", "", "", "", "", "", ""}
		};
	
	
	public parsero(ArrayList<token> list) {
		this.list = list;
		indicesX = new ArrayList<Integer>();
		indicesY = new ArrayList<Integer>();

		for (int N : indicesRawX) {
			indicesX.add(N);
		}
		for (int N : indicesRawY) {
			indicesY.add(N);
		}
	}
	
	public void setElements(JScrollPane caja5, JScrollPane caja2, JLabel label1,
			DefaultTableModel modeloEr) {
		this.caja2 = caja2;
		this.caja5 = caja5;
		this.label1 = label1;
		this.modeloEr = modeloEr;
	}
	
	public String doParser() {
		Stack<Integer> pila = new Stack<Integer>();
		int error = 200;
		
		pila.push(199);
		pila.push(700);
		list.add(new token(199, 0));

		int apun = 0;
		int x=0, k=0;
		do {
			x = pila.pop();
			k = list.get(apun).key;
			System.out.println(x + " " + k);
			if(x == 99 || x < 200) {
				if (x == k) 
					apun++;
				else {
					ap = apun;
					break;
				}
			} else {
				String prod = M(x, k);
				System.out.println(prod);
				if(!prod.equals("")) {
					if(!prod.equals("99")) {
						String[] siguiente = prod.split("\\s");
						for (int i = siguiente.length-1; i >= 0; i--) {
							pila.add(Integer.parseInt(siguiente[i]));
						}
					}
				}else {
					ap = apun;
					break;
				}
			}
		} while(x != 199);
		
		if(x != 199) {
			int linea;
			if(apun==0)
				linea = 1;
			else
				linea = list.get(apun-1).linea;
			
			caja5.setVisible(true);
			caja2.setBounds(10, 630, 660, 210);
			label1.setBounds(10, 550, 100, 20);
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

	public String M(int x, int k) {
		if(indicesX.contains(k))
			return tablaSintactica[indicesY.indexOf(x)][indicesX.indexOf(k)];
		else
			return "";
	}
	
	public int getError(int x) {
		switch (x) {
		case 300: case 303: case 305: case 307: case 310: case 312: case 316: case 200: case 201: case 203: case 204:
		case 207: case 208: case 211: case 215:
			return 201;
		case 301: case 302: case 304: case 306: case 308: case 309: case 311: case 313: case 317: case 202: case 206:
			return 204;
		case 314: case 315:
			return 208;
		case 318: case 205: case 209: case 210: case 214:
			return 205;
		case 319: case 212: case 213: 
			return 206;
		default:
			return 211;
		}
	}
}
