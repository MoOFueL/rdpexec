package ru.seregamoskal.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.seregamoskal.domain.LoginInfo;

/**
 * @author Dmitriy
 */
@Repository
@Transactional
public interface LoginInfoRepository extends AbstractRepository<LoginInfo> {

    @Modifying
    @Query(value = "DELETE FROM login_info", nativeQuery = true)
    void deleteAllNative();
}
