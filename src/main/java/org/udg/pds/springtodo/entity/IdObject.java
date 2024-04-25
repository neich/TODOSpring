package org.udg.pds.springtodo.entity;

import org.springframework.lang.NonNull;

public class IdObject {

    @NonNull
    public Long id;

    public IdObject(@NonNull Long id) {
        this.id = id;
    }
}
