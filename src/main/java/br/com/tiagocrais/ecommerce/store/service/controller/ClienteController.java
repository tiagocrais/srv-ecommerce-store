package br.com.tiagocrais.ecommerce.store.service.controller;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.model.response.dto.DadosClienteDto;
import br.com.tiagocrais.ecommerce.store.service.usecase.ClienteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dados/cliente")
public class ClienteController {

    @Autowired
    private ClienteUseCase clienteUseCase;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @PostMapping
    public ResponseEntity<DadosClienteDto> cadastrarCliente(@RequestBody DadosCliente dadosClienteRequest) {

        logger.info("Recebendo requisição contendo os dados do cliente: {}", dadosClienteRequest);
        ResponseEntity<?> clienteCadastrado = clienteUseCase.cadastrarCliente(dadosClienteRequest);
        //return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);

        // Verifica se a resposta foi bem-sucedida e se tem um corpo
        if (clienteCadastrado.getStatusCode().is2xxSuccessful() && clienteCadastrado.getBody() instanceof DadosClienteDto) {
            // Retorna o corpo da resposta encapsulado em um novo ResponseEntity
            return ResponseEntity.status(clienteCadastrado.getStatusCode()).body((DadosClienteDto) clienteCadastrado.getBody());
        } else {
            // Caso contrário, retorna uma resposta com erro ou vazia, dependendo do caso
            return ResponseEntity.status(clienteCadastrado.getStatusCode()).build();
        }
    }
}
