package org.example.app.dto.error;

public class AppError {
    private final String message;
    private final int statusCode;
    private final static String ERROR_MESSAGE = "Error occurred: ";

    public AppError(
            String message,
            int statusCode
    ){
        this.message = ERROR_MESSAGE + "[ " + message + " ]";
        this.statusCode = statusCode;
    }


}
