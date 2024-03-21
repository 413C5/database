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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;        
import java.sql.Statement;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "InsertarUsuario", urlPatterns = {"/InsertarUsuario"})
public class InsertarUsuario extends HttpServlet {
    
    Connection con=null;
    String mensaje="Hola";
    String consulta=" ";
    Statement sentencia;
    private final String url = "jdbc:mysql://localhost/bdprueba";
    PreparedStatement psPrepararSentencia;

    
    String nombre="";
    String clave="";
    int intentos=0;
    int bloqueado=0;
    int admin=0;
    
    @Override
    public void init() throws ServletException{}
    
    //Metodo recuperar datos
    private void consulta(String n) {
        boolean resultado=false;
        try {
            mensaje = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
            if (con != null) {
                consulta = "select * from usuarios where nombre=?";
                PreparedStatement ps=con.prepareStatement(consulta);
                ps.setString(1,n);
                ResultSet rs=ps.executeQuery();
                
                if(rs.getRow()!=0)
                    resultado=true;
                
                while(rs.next()){ 
                    nombre=rs.getString("nombre");
                    clave=rs.getString("clave");
                    intentos=rs.getInt("intentos");
                    bloqueado= rs.getInt("bloqueado");
                    admin= rs.getInt("admin");
                    mensaje="hola";
                }   
                con.close();
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        } catch (ClassNotFoundException e) {
        }
    }
    
    //Metodo para conectar a la base de datos
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
    
    //Metodo para insertar usuarios
    private void insertar(String nombre, String clave){
        try{
            PreparedStatement ps=con.prepareStatement("Insert into usuarios values (?,?,?,?,?)");
            ps.setString(1,nombre);
            ps.setString(2,clave);
            ps.setInt(3,0);
            ps.setInt(4,0);
            ps.setInt(5,0);
            int row=ps.executeUpdate();
            if(row!=0)
                mensaje="El siguiente usuario ha sido insertado correctamente: ";
            else
                mensaje="No ocurrio nada";
        }catch (SQLException ex){
            Logger.getLogger(InsertarUsuario.class.getName()).log(Level.SEVERE,null,ex);
        }      
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //Recuperación datos del html
            String user=request.getParameter("usuario");
            String password=request.getParameter("contrasena");
            RequestDispatcher rd;
            


          //Para evitar errores al no colocar nada
          if(request.getParameter("usuario").isEmpty())
              response.sendRedirect("Insertar_V.html");
            
          //Recuperacion datos
            consulta(user);
            
            //Conexion a base de datos
            conectar();
           
            if(user.equals(nombre))
                response.sendRedirect("Insertar_UE.html");
            
            else{
            insertar(user,password);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Insertar un usuario</title>");    
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
            out.println("<body style=\"background-color:cornsilk;\">");
            out.println("<h1>Insertar un usuario</h1>");
            if(mensaje.equals("El siguiente usuario ha sido insertado correctamente: ")){
                out.println("<br>");
                out.println("<b>"+mensaje+"</b>");
                out.println(user);
                out.println("<br>");
                out.println("<b>Su contraseña es: </b>"+password);
            }
            //
            else
                out.println(mensaje);
            out.println("<br>");
            out.println("<br>");
             out.println("<a href=\"Menu.html\" class=\"button button1\" >Regresar al menu</a>");
            out.println("<br>");
            out.println("<a href=\"Insertar.html\" class=\"button button2\" >Insertar otro usuario</a>");
            out.println("</body>");
            out.println("</html>");
        }
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
