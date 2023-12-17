package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends CrudRepository<DadosClienteDto, Integer> {

    @Procedure
    List<DadosClienteDto> consultarClienteEndereco(@Param("cpfCnpj") String cpfCnpj, @Param("email") String email);

    @Procedure
    ResponseEntity<?> validaLogin(
            @Param("cpfCnpjOrEmail") String cpfCnpjOrEmail,
            @Param("senha") String senha);
}
