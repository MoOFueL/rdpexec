package ru.seregamoskal.rdpexec.services;

import org.apache.catalina.Server;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class RDPService {

    private ServerInfoService serverInfoService;
    private LoginInfoService loginInfoService;

    @Autowired
    public void setServerInfoService(ServerInfoService serverInfoService) {
        Assert.notNull(serverInfoService);
        this.serverInfoService = serverInfoService;
    }

    @Autowired
    public void setLoginInfoService(LoginInfoService loginInfoService) {
        Assert.notNull(loginInfoService);
        this.loginInfoService = loginInfoService;
    }

    /**
     * Необходимо реализовать метод, принимающий на вход список {@link ServerInfo} и его же возвращающий.
     * Для каждого ServerInfo необходимо осуществить доступ по указанному в поле {@link ServerInfo#address}
     *      IP адресу по RDP через перебор пар логин-пароль, полученных из метода {@link LoginInfoService#findAll()}.
     * Подключиться к серверу и выполнить нужные действия, используя метод {@link #executeOperation(Map)}
     *      и записать результат в переменную.
     * При успешном подключении к серверу:
     * 1. Установить валидный {@link ru.seregamoskal.rdpexec.domain.LoginInfo} в объект ServerInfo,
     *      используя метод {@link ServerInfo#setLoginInfo(LoginInfo)}
     * 2. При удачном выполнении операции ({@link Operation#isWentOk()}) взять дату {@link Operation#getDate()} и
     *      засетить её в ServerInfo.
     * 3. Добавить саму операцию в список операций ServerInfo {@link ServerInfo#operations}.
     */

    // TODO: 16.01.2017 реализовать описанный выше метод здесь
        public List<ServerInfo> TestConnection(List<ServerInfo> ServerList)
        {
            Map<String, List<String>> IpPlusLogoPas = new HashMap();
            for (ServerInfo Server: ServerList)
            {
                loginInfoService.findAll();
                Server.getAddress();
            }

            executeOperation(IpPlusLogoPas);
        }

    /**
     *
     *
     * @param commandsArgumentsMap - мапа, где ключом является команда к исполнению,
     *                            а значением - список аргументов этой команды
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(Map<String, List<String>> commandsArgumentsMap) {
        // TODO: 16.01.2017 реализовать данный метод
        return null;
    }
}
