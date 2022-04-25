package com.nicoardizzoli.pruebatecnicabanco.mapper;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CuentaMapper.class})
public interface MovimientoMapper {

    //IMPORTANTE!!!!!! USANDO SPRING HAY QUE USAR DI, Y NO ESTO ASI.
//    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    MovimientoDTO movimientoToDto(Movimiento movimiento);

    Movimiento dtoToMovimiento(MovimientoDTO movimientoDTO);
}
