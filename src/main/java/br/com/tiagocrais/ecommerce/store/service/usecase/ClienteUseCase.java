package br.com.tiagocrais.ecommerce.store.service.usecase;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.repository.ClienteRepositoryImpl;
import br.com.tiagocrais.ecommerce.store.service.repository.IClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteUseCase {

    @Qualifier("clienteRepositoryImpl")
    private final IClienteRepository iClienteRepository;

    @Autowired
    private final ClienteRepositoryImpl clienteRepositoryImpl;

    @Autowired
    public ClienteUseCase(@Qualifier("clienteRepositoryImpl") IClienteRepository iClienteRepository, ClienteRepositoryImpl clienteRepositoryImpl) {
        this.iClienteRepository = iClienteRepository;
        this.clienteRepositoryImpl = clienteRepositoryImpl;
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
        return response;
    }
}
