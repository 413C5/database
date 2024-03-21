package newpackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;          
import java.sql.DriverManager;     
import java.sql.ResultSet;
import java.sql.SQLException;       
import java.sql.Statement;

@WebServlet(name = "VerUsuario", urlPatterns = {"/VerUsuario"})
public class VerUsuario extends HttpServlet {
    
    Connection con=null;
    String mensaje="Hola";
    String consulta=" ";
    Statement sentencia;
    private final String url = "jdbc:mysql://localhost/bdprueba";
    String tabla="<h2 align=\"center\"><font><img src=\"img/diversos/ver_usuarios.png\" class=\"centerimg\" alt=\"No se pudo mostrar la imagen\"><strong>Base de datos de usuarios</strong></font></h2>"
                        +"<table align=\"center\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">"
                        +"<tr>"
                        +"<td><b>Nombre</b></td>"
                        +"<td><b>Contraseña</b></td>"
                        +"<td><b>Intentos</b></td>"
                        +"<td><b>Bloqueado</b></td>"
                        +"<td><b>Admin</b></td>"
                        +"</tr>";
    
    
    //Metodo conectar
    private void conectar(){
          try {   
            Class.forName("com.mysql.jdbc.Driver");  
            con = DriverManager.getConnection(url, "root", "");    
            if (con != null) {                       
                mensaje = "Conexión a base de datos funcionando";
            }
        }
        catch (SQLException e) 
        {
            mensaje = e.getMessage();
        } catch (ClassNotFoundException e) 
        {

        }
    }
         
        //Metodo para generar la tabla con los usuarios
        private void ver(){
        try {   
            Class.forName("com.mysql.jdbc.Driver");     
            con = DriverManager.getConnection(url, "root", "");   
            if (con != null) {                         
                mensaje = "Conexión a base de datos funcionando";
                consulta="SELECT * FROM usuarios";
                sentencia=con.createStatement();
                ResultSet rs = sentencia.executeQuery(consulta);

                while (rs.next()) {
                    tabla+="<tr><td>"+rs.getString("nombre")+"</td>"
                            +"<td>"+rs.getString("clave")+"</td>"
                            +"<td>"+rs.getInt("intentos")+"</td>"
                            +"<td>"+rs.getInt("bloqueado")+"</td>"
                            +"<td>"+rs.getInt("admin")+"</td></tr>";
                }
               
                con.close();

            }
            else
                mensaje="Conexion no lograda";
            
        }
        catch (SQLException e) 
        {
            mensaje = e.getMessage();
        } catch (ClassNotFoundException e)
        {

        }
    }

        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //Impresion de la tabla de usuarios
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Actualización de datos</title>");    
                        out.println("<style>");
                        out.println(".button {");
                        out.println("border: none;");
                        out.println("color: snow;");
                        out.println("padding: 15px 32px;");
                        out.println("text-align: center;");
                        out.println("text-decoration: none;");
                        out.println("font-size: 18px;");
                        out.println("cursor: pointer;");
                        out.println("display: block;");
                        out.println("margin-left: auto;");
                        out.println(";margin-right: auto;");

                        out.println("}");

                        out.println(".button1{");
                        out.println("background-color:red;");
                        out.println("height:40px;");
                        out.println("width: 70px;");
                        out.println("}");   

                        out.println(".button2{");
                        out.println("background-color:blue;");
                        out.println("height:60px;");
                        out.println("width: 70px;");
                        out.println("}");   

                        out.println(".centerimg {");
                        out.println("display: block;");
                        out.println("margin-left: auto;");
                        out.println("margin-right: auto;");
                        out.println("width: 7.5%;");
                        out.println("height:7.5%");
                        out.println("}");
                        out.println("</style>");     
            out.println("</head>");
            out.println("<body style=\"background-color:peachpuff;\">");
            out.println("<h1>Tabla de usuarios</h1>");
            //Con este metodo se muestra el contenido
            ver();
            out.println(tabla+"</table>");
            tabla="<h2 align=\"center\"><font><img src=\"img/diversos/ver_usuarios.png\" class=\"centerimg\" alt=\"No se pudo mostrar la imagen\"><strong>Base de datos de usuarios</strong></font></h2>"
                        +"<table align=\"center\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">"
                        +"<tr>"
                        +"<td><b>Nombre</b></td>"
                        +"<td><b>Contraseña</b></td>"
                        +"<td><b>Intentos</b></td>"
                        +"<td><b>Bloqueado</b></td>"
                        +"<td><b>Admin</b></td>"
                        +"</tr>";
            out.println("<br>");
            out.println("<br>");
            out.println("<a href=\"Menu.html\" class=\"button button1\" >Regresar al menu</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
