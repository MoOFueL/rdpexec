package ru.seregamoskal.rdpExec.repositories;

import org.springframework.stereotype.Repository;
import ru.seregamoskal.rdpExec.domain.ServerInfo;

import java.util.List;

/**
 * @author Dmitriy
 */
@Repository
public interface ServerInfoRepository extends AbstractRepository<ServerInfo>{
    List<ServerInfo> findByName(String serverName);

    List<ServerInfo> findByLoginInfoIsNotNull();
}
