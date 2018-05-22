package app10e.dao;

public class DAOException extends Exception {
    public DAOException() {
        
    }
    public DAOException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    private String message;
    
    public String toString() {
        return message;
    }
    
}
