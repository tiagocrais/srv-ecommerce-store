package br.com.tiagocrais.ecommerce.store.service.repository;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.ErrorResponseDto;
import br.com.tiagocrais.ecommerce.store.service.usecase.ClienteUseCase;
import br.com.tiagocrais.ecommerce.store.service.utils.Conversoes;
import br.com.tiagocrais.ecommerce.store.service.utils.ValidaTipoUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {

    @Autowired
    Conversoes conversao;

    @Autowired
    ValidaTipoUsuario validaTipoUsuario;

    private final EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    @Autowired
    public ClienteRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ResponseEntity<?> inserirClienteEndereco(DadosCliente dadosCliente) {
        try {
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

            query.setParameter("p_nome", dadosCliente.getNome());
            query.setParameter("p_cpf_cnpj", dadosCliente.getCpfCnpj());
            query.setParameter("p_email", dadosCliente.getEmail());
            query.setParameter("p_telefone", dadosCliente.getTelefone());
            query.setParameter("p_data_nascimento", conversao.converteParaDate(dadosCliente.getDataNascimento()), TemporalType.DATE);
            query.setParameter("p_genero", dadosCliente.getGenero());
            query.setParameter("p_senha", dadosCliente.getSenha());
            query.setParameter("p_rua", dadosCliente.getRua());
            query.setParameter("p_numero", dadosCliente.getNumero());
            query.setParameter("p_complemento", dadosCliente.getComplemento());
            query.setParameter("p_bairro", dadosCliente.getBairro());
            query.setParameter("p_cidade", dadosCliente.getCidade());
            query.setParameter("p_uf", dadosCliente.getUf());
            query.setParameter("p_cep", dadosCliente.getCep());

            query.execute();

            List<DadosClienteDto> resultadoConsulta = consultarClienteEndereco(
                    dadosCliente.getCpfCnpj(),
                    dadosCliente.getEmail());

            DadosClienteDto clienteInserido = resultadoConsulta.isEmpty() ? null : resultadoConsulta.get(0);

            if (clienteInserido != null) {
                return ResponseEntity.ok(clienteInserido);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado após a inserção");
            }

        } catch (Exception ex) {
            logger.error("Erro durante a execução da stored procedure: " + ex.getMessage());

            ErrorResponseDto errorResponseDto = new ErrorResponseDto("Erro ao processar a solicitação.");

            if (ex.getCause() != null && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) ex.getCause().getCause();
                logger.error("Exceção de violação de integridade: " + sqlEx.getMessage());

                errorResponseDto.setMessage("Usuário já cadastrado com o CPF/CNPJ informado.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
            } else {
                // Tratar outras exceções que não sejam de violação de integridade

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
            }
        }
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
    public ResponseEntity<?> validaLogin(String cpfCnpjOrEmail, String senha) {

        if (validaTipoUsuario.isEmail(cpfCnpjOrEmail)) {
            logger.info("Iniciando chamada da procedure valida_login_email para validar usuário" +
                    " e retornar os dados cadastrados");
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery(
                    "valida_login_email", DadosClienteDto.class);

            query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_senha", String.class, ParameterMode.IN);

            query.setParameter("p_email", cpfCnpjOrEmail);
            query.setParameter("p_senha", senha);

            List<DadosClienteDto> resultadoLogin = query.getResultList();
            DadosClienteDto clienteCadastrado = resultadoLogin.isEmpty() ? null : resultadoLogin.get(0);

            if (clienteCadastrado != null) {
                return ResponseEntity.ok(clienteCadastrado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha incorreta");
            }
        }
        logger.info("Iniciando chamada da procedure valida_login_cpf_cnpj para validar usuário" +
                " e retornar os dados cadastrados");
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(
                "valida_login_cpf_cnpj", DadosClienteDto.class);

        query.registerStoredProcedureParameter("p_cpf_cnpj", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_senha", String.class, ParameterMode.IN);

        query.setParameter("p_cpf_cnpj", cpfCnpjOrEmail);
        query.setParameter("p_senha", senha);

        List<DadosClienteDto> resultadoLogin = query.getResultList();
        DadosClienteDto clienteCadastrado = resultadoLogin.isEmpty() ? null : resultadoLogin.get(0);

        if (clienteCadastrado != null) {
            return ResponseEntity.ok(clienteCadastrado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha incorreta");
        }
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
