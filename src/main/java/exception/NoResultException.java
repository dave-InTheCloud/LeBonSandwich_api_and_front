package exception;

public class NoResultException extends Exception {

    public NoResultException() {
        super("Aucun résultat retourné");
    }
    
}
