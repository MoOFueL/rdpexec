package ru.seregamoskal.rdpexec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.util.List;

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
     * IP адресу по RDP через перебор пар логин-пароль, полученных из метода {@link LoginInfoService#findAll()}.
     * При успешном подключении к серверу:
     * 1. Установить валидный {@link ru.seregamoskal.rdpexec.domain.LoginInfo} в объект ServerInfo,
     *      используя метод {@link ServerInfo#setLoginInfo(LoginInfo)}
     * 2. Выполнить метод {@link #executeOperation(String, List)} и записать результат в переменную.
     * 3. При удачном выполнении операции ({@link Operation#isWentOk()}) взять дату {@link Operation#getDate()} и
     *      засетить её в ServerInfo.
     * 4. Добавить саму операцию в список операций ServerInfo {@link ServerInfo#operations}.
     */

    //пиши здесь этот метод

    /**
     * Реализовать данный метод
     *
     * @param command   - команда для выполнения
     * @param arguments - аргументы команды
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(String command, List<String> arguments) {
        return null;
    }
}
