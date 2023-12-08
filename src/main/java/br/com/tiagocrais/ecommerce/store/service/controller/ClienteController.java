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
        DadosClienteDto clienteCadastrado = clienteUseCase.cadastrarCliente(dadosClienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);
    }
}
