package ru.seregamoskal.rdpexec.repositories;

import org.springframework.stereotype.Repository;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.util.List;

/**
 * @author Dmitriy
 */
@Repository
public interface ServerInfoRepository extends AbstractRepository<ServerInfo>{
    List<ServerInfo> findByName(String serverName);

    List<ServerInfo> findByLoginInfoIsNotNull();
}
