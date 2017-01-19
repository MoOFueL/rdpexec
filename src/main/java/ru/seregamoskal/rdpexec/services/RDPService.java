package ru.seregamoskal.rdpexec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class RDPService {

    private ServerInfoService serverInfoService;
    private LoginInfoService loginInfoService;

    private static final String SPACE = " ";
    private static final String DOUBLE_AMPERSAND = "&&";

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
     * Подключиться к серверу и выполнить нужные действия, используя метод {@link #executeOperation(List)}
     * и записать результат в переменную.
     * При успешном подключении к серверу:
     * 1. Установить валидный {@link ru.seregamoskal.rdpexec.domain.LoginInfo} в объект ServerInfo,
     * используя метод {@link ServerInfo#setLoginInfo(LoginInfo)}
     * 2. При удачном выполнении операции ({@link Operation#isWentOk()}) взять дату {@link Operation#getDate()} и
     * засетить её в ServerInfo.
     * 3. Добавить саму операцию в список операций ServerInfo {@link ServerInfo#operations}.
     */
    // TODO: 16.01.2017 реализовать описанный выше метод здесь

    /**
     * @param commandsAndArgumentsMap - мапа, где ключом является команда к исполнению,
     *                                а значением - список аргументов этой команды
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(Map<String, List<String>> commandsAndArgumentsMap) throws IOException, InterruptedException {

        final ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> commandsAndArgumentsList = transformToListOfStrings(commandsAndArgumentsMap);
        processBuilder.command(commandsAndArgumentsList);
        final Process process = processBuilder.start();
        return makeResult(commandsAndArgumentsList, process);
    }

    private List<String> transformToListOfStrings(Map<String, List<String>> commandsAndArguments) {

        final List<String> result = new ArrayList<>();
        final Iterator<Map.Entry<String, List<String>>> iterator = commandsAndArguments.entrySet().iterator();

        while (iterator.hasNext()) {
            final Map.Entry<String, List<String>> entry = iterator.next();
            final String command = entry.getKey();
            final List<String> arguments = entry.getValue();
            result.add(command);
            result.addAll(arguments);
            if (iterator.hasNext()) {
                result.add(DOUBLE_AMPERSAND);
            }
        }
        return result;
    }

    private Operation makeResult(List<String> commandsAndArguments, Process process) throws IOException, InterruptedException {

        final Operation result = new Operation();
        final String command = StringUtils.collectionToDelimitedString(commandsAndArguments, SPACE);
        final Charset cp866 = Charset.forName("CP866"); //у нас же винда :(
        final String operationResult = StreamUtils.copyToString(process.getInputStream(), cp866);
        final String operationErrors = StreamUtils.copyToString(process.getErrorStream(), cp866);
        result.setDate(new Date());
        result.setText(command);
        result.setResult(operationResult);
        final int exitValue = process.waitFor();
        result.setExitValue(exitValue);

        final boolean wentOk = isWentOk(operationErrors);
        if (wentOk) {
            result.setWentOk(true);
        } else {
            result.setWentOk(false);
            result.setErrors(operationErrors);
        }

        return result;
    }

    private boolean isWentOk(String errors) throws IOException {
        return !StringUtils.hasText(errors);
    }
}
