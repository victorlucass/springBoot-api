package com.victorlucas.cursomc.exceptions.handlers.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;

    private Long timeStamp;
}
