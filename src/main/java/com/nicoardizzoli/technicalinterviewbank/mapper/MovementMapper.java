package com.nicoardizzoli.technicalinterviewbank.mapper;

import com.nicoardizzoli.technicalinterviewbank.dto.MovementDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Movement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface MovementMapper {

    MovementDTO movementToDto(Movement movement);

    Movement dtoToMovement(MovementDTO movementDTO);
}
