package com.nicoardizzoli.pruebatecnicabanco.mapper;

import com.nicoardizzoli.pruebatecnicabanco.dto.CuentaDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CuentaMapper {

    //IMPORTANTE!!!!!! USANDO SPRING HAY QUE USAR DI, Y NO ESTO ASI.
//    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaDTO cuentaToDto(Cuenta cuenta);

    Cuenta dtoToCuenta(CuentaDTO cuentaDTO);
}
