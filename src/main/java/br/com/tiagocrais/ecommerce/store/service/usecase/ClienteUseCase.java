package br.com.tiagocrais.ecommerce.store.service.usecase;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.repository.IClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteUseCase {

    private final IClienteRepository iClienteRepository;

    @Autowired
    public ClienteUseCase(IClienteRepository iClienteRepository) {
        this.iClienteRepository = iClienteRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    public DadosClienteDto cadastrarCliente(DadosCliente dadosClienteRequest) {

        iClienteRepository.inserirClienteEndereco(
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
        return null;
    }
}
