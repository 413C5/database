package newpackage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;


@WebServlet(name = "InicioSesion", urlPatterns = {"/InicioSesion"})
public class InicioSesion extends HttpServlet {
   
    private final String url="jdbc:mysql://localhost/bdprueba";
    PreparedStatement psPrepararSentencia;
    Connection con=null;
    String mensaje="Hola";
    String consulta="";
    
    String nombre="";
    String clave="";
    int intentos=0;
    int bloqueado=0;
    int admin=0;
    
    int intentosLocales=0;
    int prueba=0;
    int bloqueadoLocal=0;
    
    @Override
    public void init() throws ServletException{}
        
    //Metodo para recuperar datos
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
    
    //Metodo para aumentar intentos
    private void AumentarIntentos(){
        try{
            intentosLocales=intentos+1;
            Statement statement=con.createStatement();
            int actualizar= statement.executeUpdate("update Usuarios set intentos='" + intentosLocales + "' where nombre='" + nombre + "'");
            if (actualizar!=0)
                mensaje=" Usuario " +nombre +" actualizado correctamente";
            else
                 mensaje=" Usuario " +nombre +" no existe";
        
        }catch (SQLException ex){
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE,null,ex);
        }    
    }
    
    //Metodo para resetear intentos
    private void ResetearIntentos(){
        try{
            Statement statement=con.createStatement();
            int actualizar= statement.executeUpdate("update Usuarios set intentos=0 where nombre='" + nombre + "'");
            if (actualizar!=0)
                mensaje=" Usuario " +nombre +" actualizado correctamente";
            else
                 mensaje=" Usuario " +nombre +" no existe";
        }catch (SQLException ex){
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE,null,ex);
        }    
    }
        
    //Metodo para bloquear un usuario
    private void BloquearUsuario(){
        try{
            bloqueadoLocal=1;
            Statement statement=con.createStatement();
            int actualizar= statement.executeUpdate("update Usuarios set bloqueado='" + bloqueadoLocal + "', intentos=0 where nombre='" + nombre + "'");
            if (actualizar!=0)
                mensaje=" Usuario " +nombre +" bloqueado correctamente";
            else
                 mensaje=" Usuario " +nombre +" no existe";
        
        }catch (SQLException ex){
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE,null,ex);
        }    
    }
     
           
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //Recuperacion de datos del html
            String user=request.getParameter("usuario");
            String password=request.getParameter("contrasena");
            RequestDispatcher rd;
            
          //Para evitar error al no colocar nada
          if(request.getParameter("usuario").isEmpty())
              response.sendRedirect("index_DI.html");
            
            //Recuperación datos de usuario
            consulta(user);
            
            //Conexion a base de datos
            conectar();
            
            //Ingreso exitoso
            //Usuario y contraseña correctos
            if(user.equals(nombre) && password.equals(clave)){
                
                //Cuando se es administrador
                if(admin==1)
                    response.sendRedirect("Menu.html");
                
                //Cuando no se es administrador
                else{
                    
                    //Usuario no bloqueado.Ademas de ingresar, se resetean los intentos
                    if(bloqueado==0){
                        ResetearIntentos();
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Página de inicio</title>");
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

                        out.println(".button1 {");
                        out.println("background-color: red;");
                        out.println("}");

                        out.println(".button2{");
                        out.println("background-color:maroon;");
                        out.println("height:40px;");
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
                        out.println("<meta charset=\"UTF-8\">");
                        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        out.println("</head>");
                        out.println("<body style=\"background-color:peachpuff;\">");        
                        out.println("<h1>Inicio</h1>");
                        out.println("<img src=\"img/diversos/usuario_normal.png\" class=\"centerimg\" alt=\"No se pudo mostrar la imagen\">");
                        out.println("<b class=\"centerimg\">Bienvenido@:"+user+"</b>");
                        out.println("<br>");
                        out.println("<a href=\"index.html\" class=\"button button2\" >Cerrar sesión</a>");
            

                        out.println("</form>"); 
                        out.println("</body>");
                        out.println("</html>");
                    }
                    
                    //Usuario bloqueado
                    else
                        response.sendRedirect("index_UB.html");
                    
                }
            }
            
            //Ingreso no exitoso
            //Usuario correcto y contraseña incorrecta            
            else if(user.equals(nombre) && !password.equals(clave)){
                
                //Cuando se es administrador
                if(admin==1)
                    response.sendRedirect("index_DI.html");
                
                //Cuando no se es administrador
                else{
                    
                    //Si el usuario no esta bloqueado, pero sus intentos son menores a 3
                    if(intentos<2 && bloqueado==0){
                    AumentarIntentos();
                    response.sendRedirect("index_DI.html");
                    }
                    
                    //Cuando el usuario a ingresar alcanza el límite de intentos disponibles
                    else if(intentos<2 && bloqueado==1)
                        response.sendRedirect("index_UB.html");
                    
                    //Bloqueo del usuario
                    else{
                        AumentarIntentos();
                        BloquearUsuario();
                        response.sendRedirect("index_UB.html");
                    }                   
                }
            }
            
            //Datos incorrectos
            else
                response.sendRedirect("index_DI.html");
         
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
