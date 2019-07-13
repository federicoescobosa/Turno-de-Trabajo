public class Personal extends JFrame {

        private Image imagenFondo;
        private URL fondo;
        private JTextField campoNombre,campoApellido1,campoBuscar,campoApellido2,campoFecha,campoDni;
        private JTable tabla;
        private JPanel panelTablaResultados,panelBotones,panelIngresoDatos;
        private JLabel etiquetaNombre,etiquetaApellido1,etiquetaApellido2,etiquetaFecha,etiquetaDNI,etiquetaBuscar;
        private JButton botonNuevo,botonEditar,botonEliminar,botonImprimir,botonSalir;
        private JScrollPane scroll;


        public Personal() throws ClassNotFoundException, SQLException {


                this.setResizable(false);
                this.setTitle("Gestion de Personal");
                this.setIconImage(Toolkit.getDefaultToolkit().getImage(AccesoSistema.class.getResource("/Imagenes/icono_Programa.jpg"))); // Ruta para icono del JFrame.
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setBounds(100, 100, 1000, 800); //Tamaño del JFrame
                this.setLocationRelativeTo(null); // Centrado en el monitor

                //********************//

                fondo = getClass().getResource("/Imagenes/fondoSistema.jpg");
                imagenFondo = new ImageIcon(fondo).getImage();

                Container contenedor = getContentPane();
                contenedor.add(panel);
                panel.setLayout(null);


                componentesGraficos ();



                this.setVisible(true);


        }
        public JPanel panel = new JPanel() {


        public void paintComponent(Graphics g) {

                g.drawImage(imagenFondo, 0,0,getWidth(),getHeight(),this);
        }
        };
        private JButton btnPdf; //Impresion en pdf



        public void componentesGraficos () throws ClassNotFoundException, SQLException  {

                panelIngresoDatos = new JPanel();
                panelIngresoDatos.setBackground(SystemColor.inactiveCaption);
                panelIngresoDatos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingreso de Datos", TitledBorder.LEFT, TitledBorder.TOP, null, Color.DARK_GRAY));
                panelIngresoDatos.setBounds(10, 11, 680, 234);
                panel.add(panelIngresoDatos);
                panelIngresoDatos.setLayout(null);

                etiquetaNombre = new JLabel("Nombre");
                etiquetaNombre.setFont(new Font("Verdana", Font.PLAIN, 12));
                etiquetaNombre.setBounds(357, 26, 114, 25);
                panelIngresoDatos.add(etiquetaNombre);

                etiquetaApellido1 = new JLabel("Primer Apellido");

