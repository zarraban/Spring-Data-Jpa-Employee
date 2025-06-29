package org.example.app.service;

import java.util.List;

public interface BaseService<T1,T2> {
    T2 save(T1 request);

    boolean deleteById(Long id);

    List<T2> readAll();

    T2 readById(Long id);

    T2 readLast();

    T2 updateById(Long id, T1 request);





}
