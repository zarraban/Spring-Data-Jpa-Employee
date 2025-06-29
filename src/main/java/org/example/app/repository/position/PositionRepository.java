package org.example.app.repository.position;

import org.example.app.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("positionRepository")
public interface PositionRepository extends JpaRepository<Position,Long> {

}
