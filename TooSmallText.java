public class TooSmallText extends Exception {
    public TooSmallText(int count) {
        super("Only found " + count + " words.");
    }
}
