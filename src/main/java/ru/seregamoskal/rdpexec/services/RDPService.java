package ru.seregamoskal.rdpexec.services;

import com.sun.org.apache.xalan.internal.xslt.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.util.*;

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
    //создаем метод, который принимает лист типа сервер инфо.
    public List<ServerInfo> makeRdpCalls(List<ServerInfo> serverList) {
        //Создаем карту, для добавления в неё данных аргументов для команды mstsc(rdp connect)
        final Map<String, List<String>> argumentsMap = new HashMap();
        //Создаем лист, для добавления его в карту для метода executeOperation
        final List<String> arguments = new LinkedList<>();
        //Создаем ссылку на объект(server) типа ServerInfo для обращения к методам класса ServerInfo
        for (ServerInfo server : serverList) {
            //Создаем ссылку на объект(argumentList) типа LoginInfo для обращения к методам класса LoginInfo, для получения данных лог/пас
            for (LoginInfo argumentList : loginInfo.findAll()) {
                //Добавляем в карту String(mstsc.exe) как ключ, и лист аргументов(логин+пароль)
                argumentsMap.put("mstsc.exe", arguments);
                //Добавляем в лист логин полученный из класса LoginInfo(из метода findALL)
                arguments.add(argumentList.getLogin());
                //Добавляем в лист пароль полученный из класса LoginInfo(из метода findALL)
                arguments.add(argumentList.getPassword());
                //Создаем ссылку на объект типа Operation для добавления в поле operation информацию после выполнения метода executeOperation
                final Operation operation = executeOperation(argumentsMap);
                //Проверяем, сработал ли operation(isWentOK проверят, успешный ли коннект до сервера)
                if (operation.isWentOk()) {
                    //Если да, добавляем в ServerInfo последнюю успешную операцию.
                    server.getOperations().add(operation);
                    //Добавляем в ServerInfo корректный логин и пароль.
                    server.setLoginInfo(argumentList);
                    //Добавляем дату последнего успешного доступа в ServerInfo
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
        final ProcessBuilder processBuilder = new ProcessBuilder();
        final List<String> commandsAndArgumentsList = transformToListOfStrings(commandsArgumentsMap);
        processBuilder.command(commandsAndArgumentsList);
        final Process process = processBuilder.start();

        return null;
    }

    private List<String> transformToListOfStrings(Map<String, List<String>> commandsArgumentsMap) {
        List<String> fromMapToList = new LinkedList<>();
        for (Map.Entry<String, List<String>> entry : commandsArgumentsMap.entrySet()) {
            fromMapToList.add(entry.getKey());
            fromMapToList.addAll(entry.getValue());
            fromMapToList.add("&&");
        }
        fromMapToList.remove(fromMapToList.size() - 1);
        return fromMapToList;
    }
}
