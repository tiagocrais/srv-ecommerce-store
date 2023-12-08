package br.com.tiagocrais.ecommerce.store.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.time.LocalDate;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {
    private final EntityManager entityManager;

    @Autowired
    public ClienteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void inserirClienteEndereco(
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
    ) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("inserir_cliente_endereco");

        query.registerStoredProcedureParameter("p_nome", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cpf_cnpj", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefone", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_data_nascimento", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_genero", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_senha", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rua", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numero", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_complemento", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_bairro", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cidade", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_uf", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cep", String.class, ParameterMode.IN);

        query.setParameter("p_nome", nome);
        query.setParameter("p_cpf_cnpj", cpfCnpj);
        query.setParameter("p_email", email);
        query.setParameter("p_telefone", telefone);
        query.setParameter("p_data_nascimento", dataNascimento);
        query.setParameter("p_genero", genero);
        query.setParameter("p_senha", senha);
        query.setParameter("p_rua", rua);
        query.setParameter("p_numero", nome);
        query.setParameter("p_complemento", nome);
        query.setParameter("p_bairro", nome);
        query.setParameter("p_cidade", nome);
        query.setParameter("p_uf", nome);
        query.setParameter("p_cep", nome);

        query.execute();
    }
}
