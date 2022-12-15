import java.io.*;
import java.net.ConnectException;
import java.nio.channels.ScatteringByteChannel;
import java.sql.*;

public class Examen {
    /* * main * */
    public static void main(String[] args) throws SQLException, IOException {
        // System.out.println("hola mundo");
        //Connection con = crearConexion();
        File f = new File("./archivos/funcion.txt");
        File tablaProductos =  new File("./archivos/productos.txt");

        //ejecutarSQL(f);
        //insertarConTransaccion(tablaProductos);
        System.out.println(ejecutarFuncion("hacerPedido","8"));
    }

    /* * crearConexion * */
    public static Connection crearConexion() throws SQLException {
        String host = "containers-us-west-167.railway.app";
        String puerto = "7395";
        String bbdd = "rodriafa";
        String user = bbdd;
        String passwd = user+"1234";

        String url = "jdbc:mysql://"+host+":"+puerto+"/"+bbdd;

        Connection connection = DriverManager.getConnection(url,user,passwd);
        //System.out.println("conexión ok");
        return connection;

    }

    /* * insertarConTransaccion * */
    public static void insertarConTransaccion(File file) throws IOException, SQLException {
        String tabla = file.getName().replace(".txt","");
        //System.out.println(tabla);

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String primeraFila = bufferedReader.readLine();


        //System.out.println(primeraFila);

        Connection con = crearConexion();
        Statement statement = con.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS " +tabla+primeraFila;

        statement.execute(sql);

        String nombreCampos = bufferedReader.readLine();
        //System.out.println(nombreCampos);

        try{
            con.setAutoCommit(false);
            String linea = "";
            while((linea=bufferedReader.readLine())!=null) {
                String sentenciaInsert = " INSERT INTO " + tabla + "(" + nombreCampos + ") VALUES(?,?,?,?)";
                String[] datos = linea.split(",");

                PreparedStatement preparedStatement = con.prepareStatement(sentenciaInsert);

                //String id = datos[0];
                //String nombre = datos[1];
                //String precio = datos[2];
                //String unidades = datos[3];

                for (int i = 0; i < datos.length; i++) {
                    preparedStatement.setString(i+1, datos[i]);
                }

                preparedStatement.executeUpdate();
            }
        con.commit();
        }catch(Exception e){
            try{
                System.out.println("haciendo rollback" + e.toString());
                con.rollback();
            }catch(Exception e2){
                System.out.println("problema haciendo rollback" + e2.toString() );
            }
        }
    }

    /* * insertarConLote * */
    public static void insertarConLote(File file) throws SQLException, IOException {
        String tabla = file.getName().replace(".txt","");
        //System.out.println(tabla);

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String primeraFila = bufferedReader.readLine();


        //System.out.println(primeraFila);

        Connection con = crearConexion();
        Statement statement = con.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS " +tabla+primeraFila;

        statement.execute(sql);

        String nombreCampos = bufferedReader.readLine();
        //System.out.println(nombreCampos);
        String sentenciaInsert = " INSERT INTO " + tabla + "(" + nombreCampos + ") VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sentenciaInsert);
        //System.out.println(sentenciaInsert);

        String linea = "";
        while((linea=bufferedReader.readLine())!=null) {
            String[] datos = linea.split(",");

            //String id = datos[0];
            //String nombre = datos[1];
            //String precio = datos[2];
            //String unidades = datos[3];

            for (int i = 0; i < datos.length; i++) {
                preparedStatement.setString(i+1, datos[i]);
            }
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

    }

    /* * ejecutarSQL * */
    public static void ejecutarSQL(File file) throws IOException, SQLException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String texto ="";
        String linea = "";

        while((linea=bufferedReader.readLine())!=null){
            texto = texto + linea + "\n";
        }

        //System.out.println(texto);
        Connection con = crearConexion();
        Statement statement = con.createStatement();

        statement.execute(texto);

        bufferedReader.close();
        fileReader.close();

    }

    /* * ejecutarFuncion * */
    public static String ejecutarFuncion(String nombreFuncion, String argumentoFuncion) throws SQLException {
        Connection con =  crearConexion();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("{? = call " + nombreFuncion + "(" + argumentoFuncion +")}");
        rs.next();
        return rs.getString(1);
    }

}
