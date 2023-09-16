import java.sql.*;
import java.util.Scanner;

public class Restaurante {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****WELCOME*****");

        boolean aux = true;
        while (aux) {

            System.out.println("1. Tomar pedido: ");
            System.out.println("2. Preparar pedidos: ");
            System.out.println("3. Servir pedido: ");
            System.out.println("4. cosultar pedidos: ");
            System.out.println("5. Terminar");

            System.out.println("Ingrese un numero entre 1 - 5: ");
            int result = Integer.parseInt(scanner.nextLine());

            switch (result) {

                case 1:

                System.out.println("Ingrese codigo del pedido: ");
                String code = scanner.nextLine();

                System.out.println("Ingrese elementos del pedido: ");
                String elements = scanner.nextLine();

                System.out.print("Ingrese el estado del pedido: ");
                String state = scanner.nextLine();

                System.out.println("Ingrese el nombre del cliente: ");
                String name = scanner.nextLine();

                System.out.println("Ingrese el valor de pedido: ");
                String value = scanner.nextLine();

                System.out.println("Ingrese su metodo de pago: ");
                String payment = scanner.nextLine();

                Insert(code, elements, state, name, value, payment);

                break;

                case 2:

                    System.out.println("Ingrese el codigo del pedido: ");
                    code = scanner.nextLine();

                    Editar(code);

                    break;

                case 3:

                    System.out.println("Ingrese el codigo del pedido: ");
                    code = scanner.nextLine();

                    Editar_serve(code);

                    break;

                case 4:

                    System.out.println("Ingrese el codigo del pedido: ");
                    code = scanner.nextLine();

                    Select_One(code);

                    break;

                case 5:

                    System.out.println("Finalizando...");

                    aux = false;

                    break;

                default:
                    System.out.println("Ingrese un numero valido");
            }

        }
    }

    private static void Select_One(String code) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/restaurante";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM pedidos WHERE codigo = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, code); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String codigo = resultSet.getString("codigo");
            String estado = resultSet.getString("estado");

            System.out.println("El codigo del pedido es: " + codigo + " Y su estado es: " + estado);

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    private static void Editar_serve(String code) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/restaurante";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE pedidos SET estado = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, "Pedido entregado");
        preparedStatement.setString(2, code);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Pedido actualizado exitosamente");
        } else {
            System.out.println("No se encontró el pedido para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }


    private static void Editar(String code) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/restaurante";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE pedidos SET estado = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, "Pedido listo");
        preparedStatement.setString(2, code);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Pedido actualizado exitosamente");
        } else {
            System.out.println("No se encontró el pedido para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static void Insert(String code, String elements, String state, String name, String value, String payment) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/restaurante";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pedidos");


            // Sentencia INSERT
            String sql = "INSERT INTO pedidos (codigo, elementos, estado, nombre_cliente, valor, medio_pago) VALUES (?, ?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, elements);
            preparedStatement.setString(3, state);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, value);
            preparedStatement.setString(6, payment);


            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("pedido registrado de exitosamente.");
            } else {
                System.out.println("No se pudo registrar su viaje");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
    }

