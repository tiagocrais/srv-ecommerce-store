package br.com.tiagocrais.ecommerce.store.service.controller;

import br.com.tiagocrais.ecommerce.store.service.model.request.DadosCliente;
import br.com.tiagocrais.ecommerce.store.service.usecase.ClienteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store/dados/cliente")
public class ClienteController {

    @Autowired
    private ClienteUseCase clienteUseCase;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody DadosCliente dadosClienteRequest) {

        logger.info("Recebendo requisição contendo os dados do cliente: {}", dadosClienteRequest);
        ResponseEntity<?> clienteCadastrado = clienteUseCase.cadastrarCliente(dadosClienteRequest);

        return  clienteCadastrado;
    }

    @GetMapping("/login")
    public ResponseEntity<?> validaLogin(
            @RequestHeader(name = "cpfCnpjOrEmail") String cpfCnpjOrEmail,
            @RequestHeader(name = "senha") String senha) {

        logger.info("Recebendo requisição contendo os dados de login - user: {}, senha: {}",
                cpfCnpjOrEmail, senha);
        ResponseEntity<?> login = clienteUseCase.validaLogin(cpfCnpjOrEmail, senha);
        return login;
    }

    @PutMapping("/altera-senha")
    public ResponseEntity<?> alteraSenha(
            @RequestHeader(name = "cpfCnpjOrEmail") String cpfCnpjOrEmail,
            @RequestHeader(name = "novaSenha") String novaSenha) {

        logger.info("Recebendo requisição contendo os dados de login - user: {}, novaSenha: {}",
                cpfCnpjOrEmail, novaSenha);
        ResponseEntity<?> response = clienteUseCase.alteraSenha(cpfCnpjOrEmail, novaSenha);
        return response;
    }
}
