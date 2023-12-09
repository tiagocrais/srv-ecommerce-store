package br.com.tiagocrais.ecommerce.store.service.model.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DadosClienteDto {

    private String nome;

    private String cpfCnpj;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private String genero;

    private String rua;

    private Integer numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    @Id
    private Integer id;
}
