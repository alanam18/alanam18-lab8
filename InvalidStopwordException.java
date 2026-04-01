public class InvalidStopwordException extends Exception {
    public InvalidStopwordException(String stopword) {
        super("Couldn't find stopword: " + stopword);
    }
}
