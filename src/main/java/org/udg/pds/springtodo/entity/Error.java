package org.udg.pds.springtodo.entity;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class Error {
    String path;
    @NonNull
    @Getter
    private String timestamp;
    @NonNull
    @Getter
    private Integer status;
    @NonNull
    @Getter
    private String error;
    @NonNull
    @Getter
    private String message;

}
