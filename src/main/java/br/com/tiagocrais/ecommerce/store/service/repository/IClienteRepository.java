package br.com.tiagocrais.ecommerce.store.service.repository;

import java.time.LocalDate;

public interface IClienteRepository {

    void inserirClienteEndereco(
            String nome,
            String cpfCnpj,
            String email,
            String telefone,
            LocalDate dataNascimento,
            String genero,
            String senha,
            String rua,
            Integer numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String cep
    );
}
