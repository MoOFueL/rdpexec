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
import java.util.Date;
import java.util.List;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class RDPService {

    private ServerInfoService serverInfoService;
    private LoginInfoService loginInfoService;

    private static final String SPACE = " ";

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
     * @param commandsAndArguments - список который содержит в себе команды
     *                             и их аргументы в правильном порядке
     * @return - {@link Operation} с данными об операции
     */
    private Operation executeOperation(List<String> commandsAndArguments) throws IOException, InterruptedException {

        final ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commandsAndArguments);
        final Process process = processBuilder.start();
        return makeResult(commandsAndArguments, process);
    }

    private Operation makeResult(List<String> commandsAndArguments, Process process) throws IOException, InterruptedException {

        final Operation result = new Operation();
        final String command = StringUtils.collectionToDelimitedString(commandsAndArguments, SPACE);
        final Charset cp866 = Charset.forName("CP866"); //у нас же винда :(
        final String operationResult = StreamUtils.copyToString(process.getInputStream(), cp866);
        final String operationErrors = StreamUtils.copyToString(process.getErrorStream(), cp866);
        final boolean wentOk = isWentOk(operationErrors);

        if (wentOk) {
            result.setDate(new Date());
            result.setResult(operationResult);
            result.setText(command);
            result.setWentOk(true);
            result.setText(command);
        } else {
            result.setDate(new Date());
            result.setText(command);
            result.setWentOk(false);
            result.setErrors(operationErrors);
            result.setResult(operationResult);
        }

        final int exitValue = process.waitFor();
        result.setExitValue(exitValue);
        return result;
    }

    private boolean isWentOk(String errors) throws IOException {
        return !StringUtils.hasText(errors);
    }
}
