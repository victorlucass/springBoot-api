package com.victorlucas.cursomc.services.validations;

import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.dto.ClienteNewDTO;
import com.victorlucas.cursomc.exceptions.handlers.auxiliar.FieldMessage;
import com.victorlucas.cursomc.services.validations.utils.BR;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        // inclua os testes aqui, inserindo erros na lista

        if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getNumber()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF é inválido."));
        }
        if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getNumber()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ é inválido."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
