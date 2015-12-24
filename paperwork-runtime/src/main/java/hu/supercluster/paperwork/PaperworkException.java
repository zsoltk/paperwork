package hu.supercluster.paperwork;

public class PaperworkException extends RuntimeException {
    public PaperworkException() {
    }

    public PaperworkException(String detailMessage) {
        super(detailMessage);
    }

    public PaperworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public PaperworkException(Throwable throwable) {
        super(throwable);
    }

    public PaperworkException(String template, String filename) {
        super(String.format(template, filename));
    }

    public PaperworkException(String template, String filename, Throwable throwable) {
        super(String.format(template, filename), throwable);
    }
}
