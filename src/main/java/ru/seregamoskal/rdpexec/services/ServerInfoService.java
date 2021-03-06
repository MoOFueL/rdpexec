package ru.seregamoskal.rdpexec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.ServerInfo;
import ru.seregamoskal.rdpexec.repositories.ServerInfoRepository;

import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class ServerInfoService {

    private ServerInfoRepository serverInfoRepository;

    @Autowired
    public void setServerInfoRepository(ServerInfoRepository serverInfoRepository) {
        Assert.notNull(serverInfoRepository);
        this.serverInfoRepository = serverInfoRepository;
    }

    public Page<ServerInfo> findAll(Pageable pageable) {
        return serverInfoRepository.findAll(pageable);
    }

    public List<ServerInfo> findByName(String serverName) {
        return serverInfoRepository.findByName(serverName);
    }

    /**
     * Метод для получения всех работающих и доступных серверов
     *
     * @return список работающих, доступных серверов
     */
    public List<ServerInfo> findWorkingServers() {
        return serverInfoRepository.findByLoginInfoIsNotNull();
    }

    public List<ServerInfo> save(Iterable<ServerInfo> iterable) {
        return serverInfoRepository.save(iterable);
    }

    public ServerInfo save(ServerInfo serverInfo) {
        return serverInfoRepository.save(serverInfo);
    }
}
