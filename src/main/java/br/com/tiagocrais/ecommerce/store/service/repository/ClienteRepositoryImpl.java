package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.usecase.ClienteUseCase;
import br.com.tiagocrais.ecommerce.store.service.utils.Conversoes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {

    @Autowired
    Conversoes conversao;

    private final EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    @Autowired
    public ClienteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DadosClienteDto inserirClienteEndereco(
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
        logger.info("Iniciando chamada da procedure inserir_cliente_endereco para cadastrar o cliente");
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("inserir_cliente_endereco");

        query.registerStoredProcedureParameter("p_nome", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cpf_cnpj", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefone", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_data_nascimento", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_genero", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_senha", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rua", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numero", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_complemento", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_bairro", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cidade", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_uf", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cep", String.class, ParameterMode.IN);

        query.setParameter("p_nome", nome);
        query.setParameter("p_cpf_cnpj", cpfCnpj);
        query.setParameter("p_email", email);
        query.setParameter("p_telefone", telefone);
        query.setParameter("p_data_nascimento", conversao.converteParaDate(dataNascimento), TemporalType.DATE);
        query.setParameter("p_genero", genero);
        query.setParameter("p_senha", senha);
        query.setParameter("p_rua", rua);
        query.setParameter("p_numero", numero);
        query.setParameter("p_complemento", complemento);
        query.setParameter("p_bairro", bairro);
        query.setParameter("p_cidade", cidade);
        query.setParameter("p_uf", uf);
        query.setParameter("p_cep", cep);

        query.execute();

        List<DadosClienteDto> resultadoConsulta = consultarClienteEndereco(cpfCnpj, email);
        return resultadoConsulta.isEmpty() ? null : resultadoConsulta.get(0);
    }

    public List<DadosClienteDto> consultarClienteEndereco(String cpfCnpj, String email) {

        logger.info("Iniciando chamada da procedure consultar_cliente_endereco para consultar os dados cadastrados");
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("consultar_cliente_endereco", DadosClienteDto.class);

        query.registerStoredProcedureParameter("p_cpf_cnpj", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);

        query.setParameter("p_cpf_cnpj", cpfCnpj);
        query.setParameter("p_email", email);

        return query.getResultList();
    }

    @Override
    public <S extends DadosClienteDto> S save(S entity) {
        throw new UnsupportedOperationException("Método save não é suportado nesta implementação.");
    }

    @Override
    public <S extends DadosClienteDto> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método saveAll não é suportado nesta implementação.");
    }

    @Override
    public Optional<DadosClienteDto> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<DadosClienteDto> findAll() {
        return null;
    }

    @Override
    public Iterable<DadosClienteDto> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(DadosClienteDto entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends DadosClienteDto> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
