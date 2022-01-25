package org.udg.pds.springtodo.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Error {
    String path;
    @NonNull
    private String timestamp;
    @NonNull
    private Integer status;
    @NonNull
    private String error;
    @NonNull
    private String message;
}
