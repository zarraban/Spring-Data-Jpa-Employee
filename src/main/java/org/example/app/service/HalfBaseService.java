package org.example.app.service;

import java.util.List;

public interface HalfBaseService <T1,T2>{
    T2 save(T1 request);

    boolean deleteById(Long id);

    List<T2> readAll();
}
