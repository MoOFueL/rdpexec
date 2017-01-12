package ru.seregamoskal.rdpExec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Dmitriy
 */
@NoRepositoryBean
public interface AbstractRepository<T> extends JpaRepository<T, Long> {
}
