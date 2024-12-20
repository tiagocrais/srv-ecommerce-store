package br.com.tiagocrais.ecommerce.store.service.usecase;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.repository.ClienteRepositoryImpl;
import br.com.tiagocrais.ecommerce.store.service.repository.IClienteQueryRepository;
import br.com.tiagocrais.ecommerce.store.service.repository.IClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteUseCase {

    @Qualifier("clienteRepositoryImpl")
    private final IClienteRepository iClienteRepository;

    @Autowired
    private final ClienteRepositoryImpl clienteRepositoryImpl;


    @Autowired
    private final IClienteQueryRepository clienteQueryRepository;

    @Autowired
    public ClienteUseCase(
            @Qualifier("clienteRepositoryImpl") IClienteRepository iClienteRepository,
            ClienteRepositoryImpl clienteRepositoryImpl,
            IClienteQueryRepository clienteQueryRepository) {
        this.iClienteRepository = iClienteRepository;
        this.clienteRepositoryImpl = clienteRepositoryImpl;
        this.clienteQueryRepository = clienteQueryRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    public ResponseEntity<?> cadastrarCliente(DadosCliente dadosClienteRequest) {

        logger.info("Iniciando comunicação com a camada de repository para cadastro do cliente");
        ResponseEntity<?> response = clienteRepositoryImpl.inserirClienteEndereco(dadosClienteRequest);

        logger.info("Retorno da base de dados com o response: {}", response);

        // Verifica se a resposta foi bem-sucedida e se tem um corpo
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof DadosClienteDto) {
            // Retorna o corpo da resposta encapsulado em um novo ResponseEntity
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } else {
            // Caso contrário, retorna a resposta original do use case
            return response;
        }
    }

    public ResponseEntity<?> validaLogin(String cpfCnpjOrEmail, String senha) {

        logger.info("Iniciando comunicação com a camada de repository para validar usuário");
        if (verificarExistenciaCpfCnpjEmail(cpfCnpjOrEmail)) {
            logger.info("Iniciando comunicação com a camada de repository para validação de senha e" +
                    " retorno com os dados do cliente");
            ResponseEntity<?> response = clienteRepositoryImpl.validaLogin(cpfCnpjOrEmail, senha);

            logger.info("Retorno com a validação do login: {}", response);
            return response;
        }
        logger.info("Cpf/cnpj ou email não cadastrados");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf/cnpj ou email não cadastrados");
    }

    public ResponseEntity<?> alteraSenha(String cpfCnpjOrEmail, String novaSenha) {

        if (!verificarExistenciaNovaSenha(cpfCnpjOrEmail, novaSenha)) {
            if (verificarExistenciaCpfCnpjEmail(cpfCnpjOrEmail)) {
                logger.info("Iniciando comunicação com a camada de repository para atualização da senha");
                if (atualizaSenha(cpfCnpjOrEmail, novaSenha)) {
                    logger.info("Senha alterada com sucesso");
                    return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
                }
                logger.info("A senha não foi alterada");
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("A senha não foi alterada");
            }
            logger.info("Cpf/cnpj ou email não cadastrados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf/cnpj ou email não cadastrados");
        }
        logger.info("Senha atual já cadastrada. Por favor insira uma nova");
        return ResponseEntity.status(HttpStatus.IM_USED).body("Senha atual já cadastrada. Por favor insira uma nova");
    }

    public boolean verificarExistenciaCpfCnpjEmail(String cpfCnpjOrEmail) {
        return clienteQueryRepository.countByCpfCnpjOrEmail(cpfCnpjOrEmail) > 0;
    }

    public boolean verificarExistenciaNovaSenha(String cpfCnpjOrEmail, String novaSenha) {
        return clienteQueryRepository.countByNovaSenhaExists(cpfCnpjOrEmail, novaSenha) > 0;
    }

    public boolean atualizaSenha(String cpfCnpjOrEmail, String novaSenha) {
        int quantidadeAtualizada = clienteQueryRepository.atualizarSenhaPorCpfCnpjOrEmail(
                cpfCnpjOrEmail, novaSenha);

        return quantidadeAtualizada > 0 ? true : false;
    }
}
