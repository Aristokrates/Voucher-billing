package org.pan.voucher.web;

/**
 * Various common errors used in code.
 */
public enum WebErrors {

    GENERAL("error.general"),
    BACKEND("error.backend"),
    FORBIDDEN("error.forbidden"),
    CONNECTION("error.connection"),
    INVALID_USERNAME("error.invalid_username"),
    INVALID_PASSWORD("error.invalid_password"),
    INVALID_PASSWORD_CONFIRM("error.general");

    /** The message. */
    private String message;

    /**
     * Instantiates a new errors.
     *
     * @param message the message
     */
    private WebErrors(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

}
