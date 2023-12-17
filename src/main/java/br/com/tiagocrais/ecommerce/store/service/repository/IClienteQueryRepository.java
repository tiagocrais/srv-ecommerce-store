package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IClienteQueryRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.email = :cpfCnpjOrEmail OR c.cpfCnpj = :cpfCnpjOrEmail")
    long countByCpfCnpjOrEmail(String cpfCnpjOrEmail);

    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.email = :cpfCnpjOrEmail OR c.cpfCnpj = :cpfCnpjOrEmail" +
            " AND c.senha = :novaSenha")
    long countByNovaSenhaExists(String cpfCnpjOrEmail, String novaSenha);

    @Query("UPDATE Cliente c SET c.senha = :novaSenha WHERE c.email = :cpfCnpjOrEmail OR c.cpfCnpj = :cpfCnpjOrEmail")
    @Modifying
    @Transactional
    int atualizarSenhaPorCpfCnpjOrEmail(
            @Param("cpfCnpjOrEmail") String cpfCnpjOrEmail,
            @Param("novaSenha") String novaSenha);
}
