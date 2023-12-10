package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends CrudRepository<DadosClienteDto, Integer> {

    @Procedure
    List<DadosClienteDto> consultarClienteEndereco(@Param("cpfCnpj") String cpfCnpj, @Param("email") String email);
}
