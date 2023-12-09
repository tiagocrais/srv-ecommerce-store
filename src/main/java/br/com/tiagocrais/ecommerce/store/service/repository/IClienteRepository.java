package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IClienteRepository extends CrudRepository<DadosClienteDto, Integer> {

    DadosClienteDto inserirClienteEndereco(
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

    @Procedure
    List<DadosClienteDto> consultarClienteEndereco(@Param("cpfCnpj") String cpfCnpj, @Param("email") String email);
}
