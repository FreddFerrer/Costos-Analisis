package Vistas;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import config.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ventana extends JFrame{

    private JPanel panel;
    private  JTable lista;
    private JTextField textoResultado;
    private JButton botonSumar;
    private JList marcados;
    List<Integer> preciosASumar = new ArrayList<>();
    TableColumnModel columnModel = lista.getColumnModel();
    DefaultListModel<Integer> marcadosModel = new DefaultListModel<>();


    public ventana(){
        initPanel();

        botonSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = sumarTodo(preciosASumar);
                textoResultado.setText(String.valueOf(result));

            }
        });

        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(e.getClickCount() == 2){
                    int precio = tomarPrecio(lista);
                    marcados.setModel(marcadosModel);
                    marcadosModel.addElement(precio);
                    preciosASumar.add(precio);
                }
            }
        });
    }

    public void initPanel(){
        setTitle("Costos Analisis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800); //Dimensiones del JFrame
        setLocationRelativeTo(null);
        setContentPane(panel);
        createJTable();
        pack();
        setVisible(true);

    }

    public void createJTable(){
        consultar();
    }

    public void consultar()  {
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        Statement st;
        ResultSet rs;
        String sql = "SELECT * FROM lista";

        model.addColumn("ANALISIS");
        model.addColumn("PRECIO");
        lista.setModel(model);

        Object[] datos = new Object[3];

        try {
            st = (Statement) conexion.createStatement();
            rs = st.executeQuery(sql);

            while(rs.next()){
                datos[0] = rs.getString(2);
                datos[1] = rs.getInt(3);
                model.addRow(datos);
            }
            columnModel.getColumn(0).setPreferredWidth(300);
            columnModel.getColumn(1).setPreferredWidth(10);


        }catch (Exception e){
            System.out.println("no funciona");
        }

    }

    public int tomarPrecio(JTable tabla){
        int row = tabla.getSelectedRow();
        int precio = (int) tabla.getValueAt(row, 1);
        return precio;
    }

    public int sumarTodo(List<Integer> precios){
        int resultado = 0;
        for(Integer i : precios){
            resultado = resultado + i;
        }
        return resultado;
    }

}
