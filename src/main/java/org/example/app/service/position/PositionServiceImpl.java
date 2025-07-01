package org.example.app.service.position;

import org.example.app.dto.PositionDtoRequest;
import org.example.app.entity.Position;
import org.example.app.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service("positionService")
public class PositionServiceImpl implements PositionService {

    private final PositionRepository repository;

    public PositionServiceImpl(
            @Qualifier("positionRepository") PositionRepository repository
    ){
        this.repository = repository;
    }



    @Override
    public Position save(PositionDtoRequest request) {
        Objects.requireNonNull(request, "Parameter [request] must not be null");

        Position position= new Position();
        position.setNameOfPosition(request.positionName());
        return repository.save(position);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        var position = repository.findById(id);

        if(position.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Position> readAll() {
        List<Position> list = repository.findAll();

        if(list.isEmpty()){
            return Collections.emptyList();
        }
        return list;
    }
}
