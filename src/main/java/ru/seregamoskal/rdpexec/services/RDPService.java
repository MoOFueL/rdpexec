package ru.seregamoskal.rdpexec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class RDPService {

    private ServerInfoService serverInfoService;
    private LoginInfoService loginInfo;

    @Autowired
    public void setServerInfoService(ServerInfoService serverInfoService) {
        Assert.notNull(serverInfoService);
        this.serverInfoService = serverInfoService;
    }

    @Autowired
    public void setLoginInfoService(LoginInfoService loginInfoService) {
        Assert.notNull(loginInfoService);
        this.loginInfo = loginInfoService;
    }

    /**
     * Необходимо реализовать метод, принимающий на вход список {@link ServerInfo} и его же возвращающий.
     * Для каждого ServerInfo необходимо осуществить доступ по указанному в поле {@link ServerInfo#address}
     * IP адресу по RDP через перебор пар логин-пароль, полученных из метода {@link LoginInfoService#findAll()}.
     * Подключиться к серверу и выполнить нужные действия, используя метод {@link #executeOperation(Map)}
     * и записать результат в переменную.
     * При успешном подключении к серверу:
     * 1. Установить валидный {@link ru.seregamoskal.rdpexec.domain.LoginInfo} в объект ServerInfo,
     * используя метод {@link ServerInfo#setLoginInfo(LoginInfo)}
     * 2. При удачном выполнении операции ({@link Operation#isWentOk()}) взять дату {@link Operation#getDate()} и
     * засетить её в ServerInfo.
     * 3. Добавить саму операцию в список операций ServerInfo {@link ServerInfo#operations}.
     */

    // TODO: 16.01.2017 реализовать описанный выше метод здесь
    public List<ServerInfo> makeRdpCalls(List<ServerInfo> serverList) {
        final Map<String, List<String>> argumentsMap = new HashMap();
        final List<String> arguments = new LinkedList<>();
        for (ServerInfo server : serverList) {
            for (LoginInfo argumentList : loginInfo.findAll()) {
                argumentsMap.put("mstsc.exe", arguments);
                arguments.add(argumentList.getLogin());
                arguments.add(argumentList.getPassword());
                final Operation operation = executeOperation(argumentsMap);
                if (operation.isWentOk()) {
                    server.getOperations().add(operation);
                    server.setLoginInfo(argumentList);
                    server.setDateOfLastAccess(operation.getDate());
                }
            }
        }

        return serverList;
    }

    /**
     * @param commandsArgumentsMap - мапа, где ключом является команда к исполнению,
     *                             а значением - список аргументов этой команды
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(Map<String, List<String>> commandsArgumentsMap) {
        // TODO: 16.01.2017 реализовать данный метод

        /**
         * Реализовать с использованием {@link ProcessBuilder}
         * Пример использования : </br>
         * <code>
         *      final ProcessBuilder processBuilder = new ProcessBuilder();
         *      final List<String> commandsAndArgumentsList = transformToListOfStrings(commandsAndArgumentsMap);
         *      processBuilder.command(commandsAndArgumentsList);
         *      final Process process = processBuilder.start();
         * </code>
         * Далее должна происходить работа с объектом процесса.
         */

        return null;
    }
}
