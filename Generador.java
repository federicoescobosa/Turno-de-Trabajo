package Interfaz_Grafica;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import com.toedter.calendar.JCalendar;

import BBDD.ConectarBBDD_Mysql;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ListSelectionModel;

public class Generador extends JFrame {

	private URL fondo;
	private Image imagenFondo;
	private ArrayList lista;
	private JComboBox listaTrabajadores;
	private JTable tabla;
	private Border border;
	private JDateChooser CalendarioInicio, CalendarioFin;
	private Calendar cal;
	private JLabel etiquetaDias;
	private DefaultTableModel model;
	private JCheckBox checkBoxMañana, checkBoxTarde, checkBoxNoche;
	private String[] semana = { " ", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" };
	private JScrollPane scroll;
	private JTextField textNsemana;
	private String nombreTrabajador;
	private int filaSeleccionada, columnaSeleccionada, elementos, dias;
	private String[][] datos;
	private boolean save = false, savePDF=false;
	private String [] name;
	

	public Generador() throws ClassNotFoundException, SQLException {

		Calendario cal = new Calendario();
		this.setResizable(false);
		this.setTitle("Turnos de Trabajo");
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(AccesoSistema.class.getResource("/Imagenes/icono_Programa.jpg"))); // Ruta
																														// para
																														// icono
																														// del
																														// JFrame.
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1000, 800); // Tamaño del JFrame
		this.setLocationRelativeTo(null); // Centrado en el monitor

		// ********************//

		fondo = getClass().getResource("/Imagenes/fondoSistema.jpg");
		imagenFondo = new ImageIcon(fondo).getImage();

		Container contenedor = getContentPane();
		contenedor.add(panelPrincipal);
		panelPrincipal.setLayout(null);

		IntroduceFecha(cal);
		laminaSuperior();
		laminaInferior();
		System.out.println();

	}

	public JPanel panelPrincipal = new JPanel() {

		public void paintComponent(Graphics g) {

			g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
		}
	};

	private void laminaSuperior() throws ClassNotFoundException, SQLException {

		JPanel superior = new JPanel();
		superior.setBounds(10, 57, 974, 279);
		superior.setOpaque(false);
		panelPrincipal.add(superior);
		superior.setLayout(null);

		JLabel etiquetaLista = new JLabel("Lista Trabajadores");
		etiquetaLista.setFont(new Font("Verdana", Font.PLAIN, 12));
		etiquetaLista.setBounds(10, 21, 134, 14);
		superior.add(etiquetaLista);

		listaTrabajadores = new JComboBox();
		listaTrabajadores.setEnabled(true);
		listaTrabajadores.addItem("-seleccione-");
		listaTrabajadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if que descarta la opcion -selecione-
				if (e.getSource() != "-seleccione-") {
					if (!listaTrabajadores.getSelectedItem().equals("-seleccione-")) {

						nombreTrabajador = listaTrabajadores.getSelectedItem().toString();
						filaSeleccionada = tabla.getSelectedRow();// Indica que fila ha sido seleccionada
						columnaSeleccionada = tabla.getSelectedColumn();// Indica que columna ha sido seleccionada
						tabla.setValueAt(nombreTrabajador, filaSeleccionada, columnaSeleccionada);
						
					}

				}
			}
		});

		listaTrabajadores.setFont(new Font("Verdana", Font.PLAIN, 12));
		listaTrabajadores.setOpaque(false);

		ConectarBBDD_Mysql.Conexion();
		lista = ConectarBBDD_Mysql.llenar_combo();

		for (int i = 0; i < lista.size(); i++) { // for que recorre la lista
			listaTrabajadores.addItem(lista.get(i)); // metodo get para que noa de el nombre en cada posicion.
			elementos = IndexCountCombo(i); // indica cuantos elementos tiene el Combo procedente del metodo
											// llenar_combo de la clase ConectarBBDD_Mysql.
		}

		ConectarBBDD_Mysql.Desconexion();
		listaTrabajadores.setBounds(184, 19, 155, 20);
		superior.add(listaTrabajadores);

		JLabel lblcuantasSemanasDeseas = new JLabel("Inicio de la Semana");
		lblcuantasSemanasDeseas.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblcuantasSemanasDeseas.setBounds(364, 21, 139, 14);
		superior.add(lblcuantasSemanasDeseas);

		CalendarioInicio = new JDateChooser();
		CalendarioInicio.setBounds(513, 21, 95, 20);
		superior.add(CalendarioInicio);

		JLabel lblFinDeLa = new JLabel("Fin de la Semana");
		lblFinDeLa.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblFinDeLa.setBounds(654, 22, 125, 14);
		superior.add(lblFinDeLa);

		CalendarioFin = new JDateChooser();
		CalendarioFin.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					etiquetaDias.setText("" + CalculadiasSemana());
					textNsemana.setText(NumeroSemana());

					// ************************************************************//
					CreaTabla(model, Integer.parseInt(etiquetaDias.getText()));

				}
			}
		});
		CalendarioFin.setBounds(811, 21, 95, 20);
		superior.add(CalendarioFin);

		JLabel Ndias = new JLabel("Numero de dias");
		Ndias.setFont(new Font("Verdana", Font.PLAIN, 12));
		Ndias.setBounds(10, 65, 134, 14);
		superior.add(Ndias);

		etiquetaDias = new JLabel("");
		etiquetaDias.setFont(new Font("Verdana", Font.PLAIN, 12));
		etiquetaDias.setBounds(184, 66, 46, 14);

		superior.add(etiquetaDias);

		JLabel lblNewLabel = new JLabel("Turnos");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 99, 95, 14);
		superior.add(lblNewLabel);

		checkBoxMañana = new JCheckBox("Ma\u00F1ana");
		checkBoxMañana.setSelected(true);

		checkBoxMañana.setOpaque(false);
		checkBoxMañana.setFont(new Font("Verdana", Font.PLAIN, 12));
		checkBoxMañana.setBounds(82, 96, 97, 23);
		superior.add(checkBoxMañana);

		checkBoxTarde = new JCheckBox("Tarde");
		checkBoxTarde.setOpaque(false);
		checkBoxTarde.setFont(new Font("Verdana", Font.PLAIN, 12));
		checkBoxTarde.setBounds(174, 96, 97, 23);
		superior.add(checkBoxTarde);

		checkBoxNoche = new JCheckBox("Noche");
		checkBoxNoche.setOpaque(false);
		checkBoxNoche.setFont(new Font("Verdana", Font.PLAIN, 12));
		checkBoxNoche.setBounds(282, 95, 97, 23);
		superior.add(checkBoxNoche);

		JButton botonRandom = new JButton("Cuadrante Aleatorio");
		botonRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (CalculadiasSemana() > 7) {
					dias = 7;
				}
				dias = CalculadiasSemana();
				//System.out.println(IndexCountCombo(elementos) + " " + IndexCountCell(dias));
				//CalculaRatio(IndexCountCombo(elementos), IndexCountCell(dias));
				//System.out.println(listaTrabajadores.getItemAt(Random(elementos)));
				int fila, columna;

				for (fila = 0; fila < CalculaTurnos(); fila++) {
					for (columna = 1; columna < dias + 1; columna++) {
						String nombre = listaTrabajadores.getItemAt(Random(elementos)).toString();
						name = nombre.split(" ");
						
						try {
							ConectarBBDD_Mysql.Conexion();
							int diasTrabajados = ConectarBBDD_Mysql.DiasTrabajados(name[0]);
							diasTrabajados++;
							ConectarBBDD_Mysql.IngresaJornada(name[0],diasTrabajados);
							
							
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						tabla.setValueAt(nombre, fila, columna);
						
					}

				}// Fin for anidado
				try {
					ConectarBBDD_Mysql.Desconexion();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		botonRandom.setBounds(797, 227, 167, 41);
		superior.add(botonRandom);
		botonRandom.setFont(new Font("Verdana", Font.PLAIN, 12));

	}

	private void laminaInferior() {

		JPanel inferior = new JPanel();
		inferior.setOpaque(false);
		inferior.setBounds(10, 359, 974, 384);
		panelPrincipal.add(inferior);
		inferior.setLayout(null);

		DefaultTableModel model = new DefaultTableModel();

		tabla = new JTable(model);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setFillsViewportHeight(true);
		tabla.setCellSelectionEnabled(true);
		tabla.setColumnSelectionAllowed(true);
		
		tabla.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				System.out.println(evt.getPropertyName());

				if ("tableCellEditor".equals(evt.getPropertyName())) {
					listaTrabajadores.setEnabled(true);
					filaSeleccionada = tabla.getSelectedRow();// Indica que fila ha sido seleccionada
					columnaSeleccionada = tabla.getSelectedColumn();// Indica que columna ha sido seleccionada
					System.out.println(filaSeleccionada + " " + columnaSeleccionada);

					if (listaTrabajadores.getSelectedItem().equals("-seleccione-")) { // evita que aparezca -seleccione-
																						// en el cuadrante

					} else {
						String lista =listaTrabajadores.getSelectedItem().toString();
						String [] Alista = lista.split(" ");
						try {
							
							
							ConectarBBDD_Mysql.Conexion();
							int diasTrabajados = ConectarBBDD_Mysql.DiasTrabajados(Alista[0]);
							diasTrabajados++;
							ConectarBBDD_Mysql.IngresaJornada(Alista[0],diasTrabajados);
							tabla.setValueAt(listaTrabajadores.getSelectedItem(), filaSeleccionada, columnaSeleccionada);
							
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						

					}
				}
			}
		});

		tabla.setOpaque(false);
		tabla.setFont(new Font("Verdana", Font.PLAIN, 12));
		tabla.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabla.setRowHeight(25); // Ancho filas
		tabla.setBounds(45, 109, 762, 314);
		scroll = new JScrollPane(tabla);
		scroll.setOpaque(false);
		scroll.setBackground(SystemColor.inactiveCaption);
		scroll.setBounds(10, 50, 933, 191);
		inferior.add(scroll);

		JLabel etinsemana = new JLabel("Semana n\u00FAmero:");
		etinsemana.setFont(new Font("Verdana", Font.PLAIN, 12));
		etinsemana.setBounds(10, 11, 117, 14);
		inferior.add(etinsemana);

		textNsemana = new JTextField();
		textNsemana.setEditable(false);
		textNsemana.setBounds(137, 9, 47, 20);
		inferior.add(textNsemana);
		textNsemana.setColumns(10);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		panelBotones.setBounds(10, 265, 933, 42);
		inferior.add(panelBotones);
		panelBotones.setLayout(new GridLayout(0, 4, 10, 0));
		
				JButton botonImprimir = new JButton("Imprimir");
				botonImprimir.setFont(new Font("Verdana", Font.PLAIN, 12));
				panelBotones.add(botonImprimir);
				
				JButton btnImprimirEnPdf = new JButton("Imprimir en PDF");
				btnImprimirEnPdf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						MessageFormat cabezera = new MessageFormat("Cuadrante semana "+ textNsemana.getText());
					
						MessageFormat footer = new MessageFormat("Página{0, number, integer}");
						try {
							tabla.print(JTable.PrintMode.FIT_WIDTH,cabezera, footer);
							savePDF=true;
						}catch (java.awt.print.PrinterException ex) {
							JOptionPane.showMessageDialog(null,  ex.getMessage(), "Error de impresión", 1);
						}
					}
				});
				btnImprimirEnPdf.setFont(new Font("Verdana", Font.PLAIN, 12));
				panelBotones.add(btnImprimirEnPdf);
				
				JButton btnSalir = new JButton("Salir");
				btnSalir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(save!=true || savePDF!=true) {
							int n = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas salir sin Imprimir en alguno de los formatos?\r\n" + 
									"Si no imprime en alguno de los formatos, perdará toda la información.","Aviso que la vas a liar", JOptionPane.YES_NO_OPTION);
							if(n==JOptionPane.YES_NO_OPTION) {
								dispose();
								
							}
							
						}else {
						dispose();
						
						}
					}
				});
				btnSalir.setFont(new Font("Verdana", Font.PLAIN, 12));
				panelBotones.add(btnSalir);
				botonImprimir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							tabla.print();
							save= true;
						} catch (PrinterException e1) {
							JOptionPane.showMessageDialog(null, "No se ha podido imprimir el domcumento" + e1.getMessage(),
									"Error de impresion", 1);
						}
					}
				});

	}

	private void IntroduceFecha(Calendario cal) {
		JLabel etiquetafecha = new JLabel("");
		etiquetafecha.setFont(new Font("Verdana", Font.BOLD, 12));
		etiquetafecha.setText(cal.ToString());
		etiquetafecha.setSize(getSize());
		etiquetafecha.setBounds(794, 28, 190, 14);
		panelPrincipal.add(etiquetafecha);

	}

	private String FechaInicio() {

		String dia = Integer.toString(CalendarioInicio.getCalendar().get(cal.DAY_OF_MONTH));
		String mes = Integer.toString(CalendarioInicio.getCalendar().get(cal.MONTH) + 1);
		String anyo = Integer.toString(CalendarioInicio.getCalendar().get(cal.YEAR));
		String date = (dia + "/" + mes + "/" + anyo);
		return date;
	}

	private String FechaFin() {

		String dia = Integer.toString(CalendarioFin.getCalendar().get(cal.DAY_OF_MONTH));
		String mes = Integer.toString(CalendarioFin.getCalendar().get(cal.MONTH) + 1);
		String anyo = Integer.toString(CalendarioFin.getCalendar().get(cal.YEAR));
		String date = (dia + "/" + mes + "/" + anyo);
		return date;
	}

	private String NumeroSemana() {
		String n_semana1 = Integer.toString(CalendarioInicio.getCalendar().get(cal.WEEK_OF_YEAR));
		String n_semana2 = Integer.toString(CalendarioFin.getCalendar().get(cal.WEEK_OF_YEAR));
		if (!n_semana1.equals(n_semana2)) {
			return n_semana1 + " / " + n_semana2;
		} else {
			return n_semana1;
		}

	}

	// Calcula los dias de la semana
	private int CalculadiasSemana() {
		int inicio = CalendarioInicio.getCalendar().get(cal.DAY_OF_MONTH);
		int fin = CalendarioFin.getCalendar().get(cal.DAY_OF_MONTH);
		if (fin - inicio < 0) {
			JOptionPane.showMessageDialog(null, "Error", "Macho que has metido mal las fechas", 2);
		}
		return (fin - inicio) + 1;
	}

	// Crea un tabla con los dias y los turnos seleccionados.
	private void CreaTabla(DefaultTableModel model, int dias) {
		// int turnos = 0;
		dias += 1;
		if (dias > 8)
			dias = 8;

		/*
		 * if (checkBoxMañana.isSelected() || checkBoxTarde.isSelected() ||
		 * checkBoxNoche.isSelected()) {
		 * 
		 * if (checkBoxMañana.isSelected()) { turnos++; } if
		 * (checkBoxTarde.isSelected()) { turnos++; } if (checkBoxNoche.isSelected()) {
		 * turnos++; }
		 * 
		 * } else { JOptionPane.showMessageDialog(null,
		 * "Debes Seleccionar al menos un tipo de turno", "Hijo como no metes turnos?",
		 * 1); turnos = 0; }
		 */
		// ******Rellenar un array desde otro array********//
		String rSemana[] = new String[dias]; // Creacion de un segundo array
		for (int i = 0; i < dias; i++) {
			String dia = semana[i];
			rSemana[i] = dia;
		}

		tablaTurnos(CalculaTurnos(), dias, rSemana);

	}

	// ********Creación de la tabla*******//
	private void tablaTurnos(int turnos, int dias, Object[] rSemana) {

		datos = new String[turnos][dias];
		if (checkBoxMañana.isSelected() || checkBoxTarde.isSelected() || checkBoxNoche.isSelected()) {

			for (int i = 0; i < turnos; i++) {
				if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) {

					datos[0][0] = "Mañana";
					datos[1][0] = "Tarde";
					datos[2][0] = "Noche";

				} else if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected()) {

					datos[0][0] = "Mañana";
					datos[1][0] = "Tarde";

				} else if (checkBoxMañana.isSelected() && checkBoxNoche.isSelected()) {

					datos[0][0] = "Mañana";
					datos[1][0] = "Noche";

				} else if (checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) {

					datos[0][0] = "Tarde";
					datos[1][0] = "Noche";

				} else if (checkBoxMañana.isSelected()) {

					datos[0][0] = "Mañana";

				} else if (checkBoxTarde.isSelected()) {

					datos[0][0] = "Tarde";

				} else if (checkBoxNoche.isSelected()) {

					datos[0][0] = "Noche";
				}
			} // fin for
		} // fin if
			// rellenaTurnos(datos, 0, 0);
		tabla.setRowHeight(55);
		tabla.setModel(new DefaultTableModel(datos, rSemana));
	}

	// Cuenta el núemero de elementos del ComboBox
	private int IndexCountCombo(int element) {

		return element + 1;
	}

	// Devuelve el número de celdas que se pueden rellenar
	private int IndexCountCell(int column) {

		if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) { // Si se
																										// selecciona
																										// todos los
																										// checks

			return column * 3;

		} else if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected()) {// Mañana y Tarde

			return column * 2;
		} else if (checkBoxMañana.isSelected() && checkBoxNoche.isSelected()) { // Mañana y Noche

			return column * 2;
		} else if (checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) { // Tarde y Noche

			return column * 2;
		} else {

			return column;
		}

	}

	// Genera Números aleatorios del numero de elementos del ComboBox
	private int Random(int lista) {

		int numero = (int) (Math.random() * lista) + 1;

		return numero;
	}

	// Metodo que calcula el ratio celdas/trabajadores
	private void CalculaRatio(int combo, int cel) {

		if (IndexCountCombo(combo) < IndexCountCell(cel)) {

			JOptionPane.showMessageDialog(null, "Hay más turnos que trabajadores disponibles", "Faltan Trabajadores", 1);
		} else if (IndexCountCombo(combo) == IndexCountCell(cel)) {

			JOptionPane.showMessageDialog(null, "Mola", "Mola", 1);

		} else {
			JOptionPane.showMessageDialog(null, "Sobran Trabajadores", "Informacion", 1);
		}
	}

	// Metodo refactorizado que calcula el numero de turnos
	private int CalculaTurnos() {

		int turnos = 1;

		if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) { // Si se
																										// selecciona
																										// todos los
																										// checks

			return turnos * 3;

		} else if (checkBoxMañana.isSelected() && checkBoxTarde.isSelected()) {// Mañana y Tarde

			return turnos * 2;
		} else if (checkBoxMañana.isSelected() && checkBoxNoche.isSelected()) { // Mañana y Noche

			return turnos * 2;
		} else if (checkBoxTarde.isSelected() && checkBoxNoche.isSelected()) { // Tarde y Noche

			return turnos * 2;
		} else {

			return turnos;
		}
	}
}
