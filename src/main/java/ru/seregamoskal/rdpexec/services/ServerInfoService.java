package ru.seregamoskal.rdpexec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.ServerInfo;
import ru.seregamoskal.rdpexec.repositories.ServerInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitriy
 */
@Service
public class ServerInfoService {

    private ServerInfoRepository serverInfoRepository;
    private TelNetService telNetService;

    @Autowired
    public void setServerInfoRepository(ServerInfoRepository serverInfoRepository) {
        Assert.notNull(serverInfoRepository);
        this.serverInfoRepository = serverInfoRepository;
    }

    @Autowired
    public void setTelNetService(TelNetService telNetService) {
        Assert.notNull(telNetService);
        this.telNetService = telNetService;
    }

    public Page<ServerInfo> findAll(Pageable pageable) {
        return serverInfoRepository.findAll(pageable);
    }

    public List<ServerInfo> findByName(String serverName) {
        return serverInfoRepository.findByName(serverName);
    }

    /**
     * Метод ищет в БД все объекты {@link ServerInfo} у которых заполнено поле адрес и поле working выставлено в true
     *
     * @return список объектов {@link ServerInfo}
     */
    public List<ServerInfo> findByAddressIsNotNullAndWorkingIsTrue() {
        return serverInfoRepository.findByAddressIsNotNullAndWorkingIsTrue();
    }

    /**
     * Метод для генерация объектов типа ServerInfo и сохранения их в бд
     *
     * @param subnets - список подсетей
     * @return - {@link List} объектов {@link ServerInfo}, с установленными адресами и признаком "работающий"
     */
    public List<ServerInfo> generateAndSaveServerInfoFromSubnets(List<String> subnets) {
        final Set<String> serversAvailableByRDPConnection = telNetService.findServersAvailableByRDPConnection(subnets);
        final List<ServerInfo> serverInfoList = new ArrayList<>(serversAvailableByRDPConnection.size());
        for (String server : serversAvailableByRDPConnection) {
            final ServerInfo serverInfo = new ServerInfo();
            serverInfo.setAddress(server);
            serverInfo.setWorking(true);
            serverInfoList.add(serverInfo);
        }
        return save(serverInfoList);
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
