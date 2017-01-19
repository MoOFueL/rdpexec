package ru.seregamoskal.rdpexec.services;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.seregamoskal.rdpexec.domain.LoginInfo;
import ru.seregamoskal.rdpexec.domain.Operation;
import ru.seregamoskal.rdpexec.domain.ServerInfo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class RDPService {

    private ServerInfoService serverInfoService;
    private LoginInfoService loginInfoService;

    private static final int MINUS_ONE = -1;
    public static final String DOUBLE_AMPERSAND = "&&";

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

    /**
     *
     *
     * @param commandsArgumentsMap - мапа, где ключом является команда к исполнению,
     *                            а значением - список аргументов этой команды
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(Map<String, List<String>> commandsArgumentsMap) throws IOException, InterruptedException {

        final StringBuilder commandBuilder = new StringBuilder();
        for (Map.Entry<String, List<String>> stringListEntry : commandsArgumentsMap.entrySet()) {
            final String command = stringListEntry.getKey();
            final List<String> arguments = stringListEntry.getValue();
            commandBuilder.append(command);
            arguments.add(DOUBLE_AMPERSAND);
            arguments.forEach(commandBuilder::append);
        }
        final String command = commandBuilder.toString();
        final Process process = new ProcessBuilder(command).start();
        return makeResult(command, process);
    }

    private Operation makeResult(String command, Process process) throws IOException, InterruptedException {
        final Operation result = new Operation();
        final StringWriter resultWriter = new StringWriter();
        final StringWriter errorsWriter = new StringWriter();
        final boolean wentOk = isWentOk(process);
        if (wentOk) {
            IOUtils.copy(process.getInputStream(), resultWriter, UTF_8.name());
            result.setDate(new Date());
            final String operationResult = resultWriter.toString();
            result.setResult(operationResult);
            result.setText(command);
            result.setWentOk(true);
            result.setText(command);
        } else {
            IOUtils.copy(process.getErrorStream(), errorsWriter, UTF_8.name());
            IOUtils.copy(process.getInputStream(), resultWriter, UTF_8.name());
            result.setDate(new Date());
            result.setText(command);
            result.setWentOk(false);
            result.setErrors(errorsWriter.toString());
            result.setResult(resultWriter.toString());
        }
        final int exitValue = process.waitFor();
        result.setExitValue(exitValue);
        return result;
    }

    private boolean isWentOk(Process process) throws IOException {
        return process.getErrorStream().read() == MINUS_ONE;
    }
}
