package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteQueryRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.email = :cpfCnpjOrEmail OR c.cpfCnpj = :cpfCnpjOrEmail")
    long countByCpfCnpjOrEmail(String cpfCnpjOrEmail);
}
