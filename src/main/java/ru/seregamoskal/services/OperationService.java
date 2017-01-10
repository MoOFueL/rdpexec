package ru.seregamoskal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.domain.Operation;
import ru.seregamoskal.repositories.OperationRepository;

/**
 * Created by Дмитрий on 10.01.2017.
 */
@Service
public class OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public void setOperationRepository(OperationRepository operationRepository) {
        Assert.notNull(operationRepository);
        this.operationRepository = operationRepository;
    }

    public Page<Operation> findAll(Pageable pageable) {
        return operationRepository.findAll(pageable);
    }
}
