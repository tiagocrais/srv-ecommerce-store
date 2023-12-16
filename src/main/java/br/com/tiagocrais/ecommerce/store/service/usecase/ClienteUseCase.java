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
        ResponseEntity<?> response = clienteRepositoryImpl.inserirClienteEndereco(
                dadosClienteRequest.getNome(),
                dadosClienteRequest.getCpfCnpj(),
                dadosClienteRequest.getEmail(),
                dadosClienteRequest.getTelefone(),
                dadosClienteRequest.getDataNascimento(),
                dadosClienteRequest.getGenero(),
                dadosClienteRequest.getSenha(),
                dadosClienteRequest.getRua(),
                dadosClienteRequest.getNumero(),
                dadosClienteRequest.getComplemento(),
                dadosClienteRequest.getBairro(),
                dadosClienteRequest.getCidade(),
                dadosClienteRequest.getUf(),
                dadosClienteRequest.getCep()
        );

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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuário ou email não cadastrados");
    }

    public boolean verificarExistenciaCpfCnpjEmail(String cpfCnpjOrEmail) {
        return clienteQueryRepository.countByCpfCnpjOrEmail(cpfCnpjOrEmail) > 0;
    }
}
