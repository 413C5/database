package database;

public class Usuarios{
    String nombre;
    String clave;
    int intentos;
    int bloqueado;
    int admin;

    public Usuarios(){
    }
    
    public Usuarios(String nombre, String clave, int intentos, int bloqueado, int admin) {
        this.nombre = nombre;
        this.clave = clave;
        this.intentos = intentos;
        this.bloqueado = bloqueado;
        this.admin = admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    
    
    @Override
    public String toString(){
        return nombre+" "+clave+" "+intentos+" "+bloqueado+" "+admin;
    }
     

}

