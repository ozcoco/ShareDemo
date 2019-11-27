package com.xdynamics.share;

public class ShareException extends RuntimeException {

    static final long serialVersionUID = 1;

    /**
     * Constructs a new ShareException.
     */
    public ShareException() {
        super();
    }

    /**
     * Constructs a new ShareException.
     *
     * @param message the detail message of this exception
     */
    public ShareException(String message) {
        super(message);
    }

    /**
     * Constructs a new ShareException.
     *
     * @param format the format string (see {@link java.util.Formatter#format})
     * @param args   the list of arguments passed to the formatter.
     */
    public ShareException(String format, Object... args) {
        this(String.format(format, args));
    }

    /**
     * Constructs a new ShareException.
     *
     * @param message   the detail message of this exception
     * @param throwable the cause of this exception
     */
    public ShareException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ShareException.
     *
     * @param throwable the cause of this exception
     */
    public ShareException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
