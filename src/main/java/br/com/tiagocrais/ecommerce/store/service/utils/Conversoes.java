package br.com.tiagocrais.ecommerce.store.service.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Conversoes {

    public java.sql.Date converteParaDate(LocalDate date) {
        java.sql.Date sqldate = java.sql.Date.valueOf(date);
        return sqldate;
    }
}
