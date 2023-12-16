package br.com.tiagocrais.ecommerce.store.service.utils;

import br.com.tiagocrais.ecommerce.store.service.usecase.ClienteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidaTipoUsuario {

    private static final Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    public boolean isEmail (String tipoUsuario) {

        if (tipoUsuario.contains("@")) {
            return true;
        }
        return false;
    }
}
