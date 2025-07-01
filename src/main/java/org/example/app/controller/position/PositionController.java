package org.example.app.controller.position;


import org.example.app.dto.PositionDtoRequest;
import org.example.app.dto.error.AppError;
import org.example.app.entity.Position;
import org.example.app.service.position.PositionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/positions")
public class PositionController {

    private final PositionService service;

    public PositionController(
            @Qualifier("positionService") PositionService service
    ){
        this.service=service;
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions(){
        try{
            List<Position> list = service.readAll();

            if(list.isEmpty()){
                return formatResponse("List of positions is empty!",
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(list,HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addPosition(@RequestBody PositionDtoRequest request){
        try{
            Position position = service.save(request);

            if(position == null){
                return formatResponse("Couldn't add new position",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(position,HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable("id")Long id){
        try{
            boolean isDeleted = service.deleteById(id);

            if(isDeleted){
                return formatResponse("Successfully deleted!",
                        HttpStatus.OK);
            }else {
                return formatResponse("Not seccessfully deleted!",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            }


        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<?> formatResponse(String message, HttpStatus status){
        return new ResponseEntity<>(new HashMap<String, String>() {
            {
                put("message", message);
                put("statusCode", String.valueOf(status.value()));
            }
        }, status);
    }
}
