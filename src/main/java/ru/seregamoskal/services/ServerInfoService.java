package ru.seregamoskal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.seregamoskal.domain.ServerInfo;
import ru.seregamoskal.repositories.ServerInfoRepository;

/**
 * @author Dmitriy
 */
@Service
public class ServerInfoService {

    private ServerInfoRepository serverInfoRepository;

    @Autowired
    public void setServerInfoRepository(ServerInfoRepository serverInfoRepository) {
        this.serverInfoRepository = serverInfoRepository;
    }

    public Page<ServerInfo> findAll(Pageable pageable) {
        return serverInfoRepository.findAll(pageable);
    }

    public ServerInfo findByName(String serverName) {
        return serverInfoRepository.findByName(serverName);
    }
}
