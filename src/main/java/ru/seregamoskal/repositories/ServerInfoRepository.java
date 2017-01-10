package ru.seregamoskal.repositories;

import org.springframework.stereotype.Repository;
import ru.seregamoskal.domain.ServerInfo;

/**
 * @author Dmitriy
 */
@Repository
public interface ServerInfoRepository extends AbstractRepository<ServerInfo>{
    ServerInfo findByName(String serverName);
}
