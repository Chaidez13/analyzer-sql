import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class escaner {
	int con;
	String del, ope, id, cons, rel, regex, text;
	String[] lineas;
	int error;
	ArrayList<token> todo;
	ArrayList<String> ides, cones;
	DefaultTableModel modeloCo, modeloId, modeloEr, modelo;
	JTable tablaCo, tablaId;
	JScrollPane caja5, caja2;
	JLabel label1;
	
	public escaner(JTextArea cuadroTexto, JLabel label) {
		ides = new ArrayList<String>(); 
		cones = new ArrayList<String>(); 
		todo = new ArrayList<token>(); 

		this.label1 = label;
		con=0;
		
		text = cuadroTexto.getText();
		lineas = text.split("\n");
	}
	
	public void setModels(DefaultTableModel modeloCo, DefaultTableModel modeloId, DefaultTableModel modeloEr,
			DefaultTableModel modelo) {
		this.modeloCo = modeloCo;
		this.modeloId = modeloId;
		this.modeloEr = modeloEr;
		this.modelo = modelo;
	}
	
	public void setTables(JTable tablaCo, JTable tablaId) {
		this.tablaCo = tablaCo;
		this.tablaId = tablaId;
	}
	
	public void setPanes(JScrollPane caja5, JScrollPane caja2) {
		this.caja5 = caja5;
		this.caja2 = caja2;
	}
	
	private int addArrayInesCones(String simbolo, ArrayList<String> array, DefaultTableModel modelo,
			JTable tabla, int minimo, int tipo, int linea) {
		int valor;
		if(!array.contains(simbolo)) {
			array.add(simbolo);
   			valor = array.indexOf(simbolo) + minimo;
   			Object datos[] = {linea, simbolo, tipo, valor};
	        modelo.addRow(datos);
    	}else {
    		valor = array.indexOf(simbolo) + minimo;
    		String newRen = ""+tabla.getValueAt(array.indexOf(simbolo), 0)+", "+linea;
   			tabla.setValueAt(newRen, ides.indexOf(simbolo), 0);
    	}
		return valor;
	}
	
	private palabrasReservadas[] cargarRegex() {
		del = "[().,'‘’;]";
		ope = "[*+-/]";
		id = "[a-zA-Z]\\w*[#]?";
		cons = "\\d+[,.]?\\d*[Ee]?\\d*[,.]?\\d*";
		rel = ">=|<=|[<>=]";
		regex = ">=|<=|(?<=([\"']\\b))(?:(?=(\\?))\\2.)*?(?=\\1)|\\w+[#]?|\\S";
		palabrasReservadas res[] = {new palabrasReservadas("SELECT", "s", 10),
				new palabrasReservadas("FROM", "f", 11),
				new palabrasReservadas("WHERE", "w", 12),
				new palabrasReservadas("IN", "n", 13),
				new palabrasReservadas("AND", "y", 14),
				new palabrasReservadas("OR", "o", 15),
				new palabrasReservadas("CREATE", "c", 16),
				new palabrasReservadas("TABLE", "t", 17),
				new palabrasReservadas("CHAR", "h", 18),
				new palabrasReservadas("NUMERIC", "u", 19),
				new palabrasReservadas("NOT", "e", 20),
				new palabrasReservadas("NULL", "g", 21),
				new palabrasReservadas("CONSTRAINT", "b", 22),
				new palabrasReservadas("KEY", "k", 23),
				new palabrasReservadas("PRIMARY", "p", 24),
				new palabrasReservadas("FOREIGN", "j", 25),
				new palabrasReservadas("REFERENCES", "l", 26),
				new palabrasReservadas("INSERT", "m", 27),
				new palabrasReservadas("INTO", "q", 28),
				new palabrasReservadas("VALUES", "v", 29),};
		
		return res;
	}
	
	public String llenarTablas() {
		String kad = null;
		int errLin = 0;
		String messageReturn = "106: Error en carga de datos. Por favor reintente";
		int tipo=0, valor=0;
		error=100;
		palabrasReservadas res[] = cargarRegex();
		boolean comilla = false;
		
		for (int i = 0; i < this.lineas.length; i++) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(this.lineas[i]);  	
			
			while(m.find()) {
				valor=0;
				tipo=0;
				String cad = m.group();
				int simb = 0;
		    	
		    	if(cad.matches(del)) {
			   		tipo = 5;
			   		switch (cad) {
					case ",": valor=50; break;
					case ".": valor=51; break;
					case "(": valor=52; break;
					case ")": valor=53; break;
					case ";": valor=55; break;
					default: 
						valor = 54;
						comilla = !comilla;
					break;
					}
			   		simb = valor;
			   	} else if(cad.matches(ope)){
			   		tipo = 7;
			   		switch (cad) {
					case "+": valor=70; break;
					case "-": valor=71; break;
					case "*": valor=72; break;
					case "/": valor=73; break;
					}
			   		simb = valor;
		    	}else if(cad.matches(rel)) {
		    		tipo = 8;
			   		switch (cad) {
					case ">": valor=81; break;
					case "<": valor=82; break;
					case "=": valor=83; break;
					case ">=": valor=84; break;
					case "<=": valor=85; break;
					}
			   		simb = valor;
		    	}else if(cad.matches(id) || cad.matches(cons) || comilla) {
		    		boolean isRes = false;
		    		for (palabrasReservadas pal : res) {
						if(cad.toUpperCase().matches(pal.lexema)) {
							valor = pal.valor;
							simb = valor;
							tipo = 1;
							isRes = true;
							break;
						}
					}
		    		if(!isRes) {
		    			if(comilla) {
		    				simb = 62;
		    				tipo = 6;  	
		    				valor = addArrayInesCones(cad, cones, modeloCo, tablaCo, 600, 62, (i+1));
		    			}else if(cad.matches(cons)) {
		    				simb = 61;
		    				tipo = 6;  	
		    				valor = addArrayInesCones(cad, cones, modeloCo, tablaCo, 600, 61, (i+1));
		    			}else {
		    				simb = 4;
		    				tipo = 4;
		    				valor = addArrayInesCones(cad, ides, modeloId, tablaId, 401, tipo, (i+1));
		    			}	
		    		}
			   	} else {
			   		if(cad.matches("\\W+")) 
			   			error = 101;	
			   		else if(cad.matches("\\w+[,.:]*\\w*")) 
			   			error = 102;
			   		else 
			   			error = 103;
			   	}
		    	
		    	if(error==100) {
		    		con++;
		    		Object datos[] = {con, (i+1), cad, tipo, valor};
		    		todo.add(new token(simb, (i+1)));
		    		modelo.addRow(datos);
		    	}else if(error!=100){
		    		errLin = (i+1); 
		    		kad = cad;
		    		i = (lineas.length+1);
		    		break;
		    	}
	
			}
		}
		
		if(error!=100) {
	    		caja5.setVisible(true);
	    		messageReturn ="Tabla de Errores";
	    		caja2.setBounds(10, 630, 660, 210);
				label1.setBounds(10, 550, 100, 20);
	    		String val;
			    switch (error) {
					case 101: val = "Símbolo Desconocido"; break;
					case 102: val = "Identificador Inválido"; break;
					default: val = "Error no Especificado"; break;
				}
	    		Object datosEr[] = {error, errLin, val, kad};
	    		modeloEr.addRow(datosEr);
	    	}
		
		if(!caja5.isVisible())
			messageReturn= "100: Sin Error";
		if(text.equals("")) {
			error = 105;
			messageReturn = "105: No se Ingreso Texto";
		}
		
		return messageReturn;
	}
	
	public int getError() {
		return error;
	}
	
	public ArrayList<token> getTodo(){
		return this.todo;
	}
}

/*



*/