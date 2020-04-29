import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class window  extends JFrame{
	private static final long serialVersionUID = 1L;
	int ancho = 1100, alto = 900;
	
	public window() {
		setSize(ancho, alto);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Escaner");
		setLayout(null);
		setResizable(false);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension sS = kit.getScreenSize();
		setLocation((sS.width-ancho)/2, (sS.height-alto)/2);
		
		JScrollPane caja1 = new JScrollPane();
		JScrollPane caja2 = new JScrollPane();
		JScrollPane caja3 = new JScrollPane();
		JScrollPane caja4 = new JScrollPane();
		JScrollPane caja5 = new JScrollPane();
		
		JLabel label1 = new JLabel("Tabla Léxica");
		JLabel label2 = new JLabel("Tabla de Identificadores");
		JLabel label3 = new JLabel("Tabla de Constantes");
		JLabel label4 = new JLabel("");
		JTable tabla = new JTable();
		JTable tablaId = new JTable();
		JTable tablaCo = new JTable();
		JTable tablaEr = new JTable();
		JTextArea cuadroTexto = new JTextArea();
		DefaultTableModel modelo;
		DefaultTableModel modeloId;
		DefaultTableModel modeloCo;
		DefaultTableModel modeloEr;
		JButton boton = new JButton("€scanear");

		caja1.setBounds(10, 10, 660, 490);
		boton.setBounds(10, 510, 660, 30);
		label1.setBounds(10, 550, 100, 20);
		label2.setBounds(700, 210, 200, 20);
		label3.setBounds(700, 530, 200, 20);
		label4.setBounds(700, 100, 200, 20);
		caja2.setBounds(10, 570, 660, 270);
		caja3.setBounds(700, 240, 360, 280);
		caja4.setBounds(700, 560, 360, 280);
		caja5.setBounds(10, 550, 660, 70);

		String cabecera[] = {"Número","Línea","Token","Tipo","Código"};
		String cabeceraId[] = {"Linea","ID","Tipo","Valor"};
		String cabeceraCo[] = {"Linea","Constante","Tipo","Valor"};
		String cabeceraEr[] = {"Código","Linea","Tipo","Error"};
		String datos[][] = {};
		
		modelo = new DefaultTableModel(datos, cabecera);
		tabla.setModel(modelo);
		tabla.setEnabled(false);
		modeloId = new DefaultTableModel(datos, cabeceraId);
		tablaId.setModel(modeloId);
		tablaId.setEnabled(false);
		modeloCo = new DefaultTableModel(datos, cabeceraCo);
		tablaCo.setModel(modeloCo);
		tablaCo.setEnabled(false);
		modeloEr = new DefaultTableModel(datos, cabeceraEr); 
		tablaEr.setModel(modeloEr);
		tablaEr.setEnabled(false);
		caja5.setVisible(false);
		
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				caja5.setVisible(false);
				label1.setBounds(10, 550, 100, 20);
				caja2.setBounds(10, 570, 660, 270);

				modelo.setRowCount(0);
				modeloId.setRowCount(0);
				modeloCo.setRowCount(0);
				modeloEr.setRowCount(0);
				
				escaner scan = new escaner(cuadroTexto, label1);
				scan.setModels(modeloCo, modeloId, modeloEr, modelo);
				scan.setPanes(caja5, caja2);
				scan.setTables(tablaCo, tablaId);
				String scanText = scan.llenarTablas();
				
				
				//Esto agrega el PARSER
				if(scan.getError() == 100) {
					ArrayList<token> todo = scan.getTodo();
					parsero parser = new parsero(todo);
					parser.setElements(caja5, caja2, label1, modeloEr);
					String par = parser.doParser();
					label4.setText(scanText + "       " + par);

				}else
					label4.setText(scanText);
			}
		});
		
		caja1.setViewportView(cuadroTexto);
		caja2.setViewportView(tabla);
		caja3.setViewportView(tablaId);
		caja4.setViewportView(tablaCo);
		caja5.setViewportView(tablaEr);
		add(caja1);
		add(caja2);
		add(caja3);
		add(caja4);
		add(caja5);
		add(boton);
		add(label1);
		add(label2);
		add(label3);
		add(label4);
	}
		
}