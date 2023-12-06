package br.com.tiagocrais.ecommerce.store.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExampleController
 *
 * @author tiagocrais
 *
 */

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello World!");
    }
}
