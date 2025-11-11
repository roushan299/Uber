package com.roushan.uber.services;

public interface WriteService<T, S, ID> {

    T create(S request);
    T update(ID id,S request);
    void deleteById(ID id);


}
