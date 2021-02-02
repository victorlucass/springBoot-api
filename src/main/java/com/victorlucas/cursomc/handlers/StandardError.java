package com.victorlucas.cursomc.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeStamp;
}
