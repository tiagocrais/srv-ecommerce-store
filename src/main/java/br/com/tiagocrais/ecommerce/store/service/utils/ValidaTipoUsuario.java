package br.com.tiagocrais.ecommerce.store.service.utils;

import org.springframework.stereotype.Service;

@Service
public class ValidaTipoUsuario {

    public boolean isEmail (String tipoUsuario) {

        if (tipoUsuario.contains("@")) {
            return true;
        }
        return false;
    }
}
