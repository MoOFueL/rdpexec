package ru.seregamoskal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seregamoskal.domain.Operation;
import ru.seregamoskal.services.OperationService;

/**
 * Created by Дмитрий on 10.01.2017.
 */
@RestController
@RequestMapping(params = "/operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OperationController {

    private OperationService operationService;

    @Autowired
    public void setOperationService(OperationService operationService) {
        Assert.notNull(operationService);
        this.operationService = operationService;
    }

    @GetMapping
    public Page<Operation> findAll(Pageable pageable) {
        return operationService.findAll(pageable);
    }
}
