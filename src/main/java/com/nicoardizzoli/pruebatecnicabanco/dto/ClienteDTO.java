package com.nicoardizzoli.pruebatecnicabanco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {
    private String clienteId;

    @NotBlank(message = "nombre requerido")
    private String nombre;

    @NotBlank(message = "apellido requerido")
    private String apellido;

    @NotBlank(message = "genero requerido")
    private String genero;

    @NotNull(message = "edad requerida")
    private Integer edad;

    @NotBlank(message = "identificacion requerido")
    private String identificacion;

    @NotBlank(message = "direccion requerido")
    private String direccion;

    @NotBlank(message = "telefono requerido")
    private String telefono;

    @NotBlank(message = "contraseña requerida")
    private String contrasena;

    @NotNull(message = "estado requerido")
    private Boolean estado;

}

