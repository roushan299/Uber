package com.roushan.uber.services;

public interface WriteService<T, S, ID> {

    T create(S entity);
    T update(ID id,S entity);
    void deleteById(ID id);


}
