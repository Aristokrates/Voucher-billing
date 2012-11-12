package org.pan.voucher.exception;

public class InvalidDataException extends RuntimeException {

    private static final long serialVersionUID = 8382188455381080244L;

    private String[] args;

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, String... args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

}
