package com.victorlucas.cursomc.exceptions.handlers.auxiliar;

import com.victorlucas.cursomc.exceptions.handlers.auxiliar.FieldMessage;
import com.victorlucas.cursomc.exceptions.handlers.auxiliar.StandardError;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationError extends StandardError{
    public static final long serialVersionUID = 1L;
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp){
        super(status,msg,timeStamp);
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }

}
