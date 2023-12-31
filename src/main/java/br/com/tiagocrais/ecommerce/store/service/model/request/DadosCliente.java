package br.com.tiagocrais.ecommerce.store.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DadosCliente {

    private String nome;

    private String cpfCnpj;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private String genero;

    private String senha;

    private String rua;

    private Integer numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;
}
