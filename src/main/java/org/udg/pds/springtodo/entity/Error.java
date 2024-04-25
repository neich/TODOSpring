package org.udg.pds.springtodo.entity;

import org.springframework.lang.NonNull;

public class Error {
    String path;
    @NonNull
    public String timestamp;
    @NonNull
    public Integer status;
    @NonNull
    public String error;
    @NonNull
    public String message;

    public Error(@NonNull String timestamp, @NonNull Integer status, @NonNull String error, @NonNull String message) {
        this.path = "null";
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
