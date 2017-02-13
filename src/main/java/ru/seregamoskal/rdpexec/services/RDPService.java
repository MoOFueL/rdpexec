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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
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
     * создаем метод, который принимает лист типа сервер инфо.
     * Создаем карту, для добавления в неё данных аргументов для команды mstsc(rdp connect)
     * Создаем лист, для добавления его в карту для метода executeOperation
     * Создаем ссылку на объект(server) типа ServerInfo для обращения к методам класса ServerInfo
     * Создаем ссылку на объект(argumentList) типа LoginInfo для обращения к методам класса LoginInfo, для получения данных лог/пас
     * Добавляем в карту String(mstsc.exe) как ключ, и лист аргументов(логин+пароль)
     * Добавляем в лист логин полученный из класса LoginInfo(из метода findALL)
     * Добавляем в лист пароль полученный из класса LoginInfo(из метода findALL)
     * Создаем ссылку на объект типа Operation для добавления в поле operation информацию после выполнения метода executeOperation
     * Проверяем, сработал ли operation(isWentOK проверят, успешный ли коннект до сервера)
     * Если да, добавляем в ServerInfo последнюю успешную операцию.
     * Добавляем в ServerInfo корректный логин и пароль.
     * Добавляем дату последнего успешного доступа в ServerInfo
     */
    public List<ServerInfo> makeRdpCalls(List<ServerInfo> serverList) throws IOException {
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
    private Operation executeOperation(Map<String, List<String>> commandsArgumentsMap) throws IOException {
        /**
         * Создаем объект класса типа ProcessBuilder
         * Создаем лист типа  стринг для того чтобы занести туда все значения из карты commandsArgumentsMap
         * Используем метод transofrmtolistofstrings
         * Вызываем метод "command" с помощью объекта processBuilder, метод будет выполнять команды.
         * Создаем переменную process типа Process и присваиваем ему вызов метода start из объекта processBuilder
         * Создаем переменную errorStream типа InputStream - для записи ошибок процесса(которые отдаются с помощью метода getInputStream();
         * Создаем переменную inputStream типа InputStream который отдает выполнение команды с помощью метода getInputStream();
         * Создаем 2 переменные типа стринг, для записи туда данных полученных из метода copyToString(класса StreamUtils)
         * Метод CopyToString служит для конвертации с помощью кодировки CP866 информации в читаемый вид(String)
         * Создаем объект типа Operation, Сэтим туда полученные данные из Process setErrors и setResult(которые в последствии перегнали в четаемый вид с помощью CopyToString)
         * Далее создаем переменную result типа String и присваиваем ей значение commandsandargumentsList с разделителем " " после каждого елемента
         * Далее полученный результат сетим в поле Text класса Operation объекта operation
         * Далее сетим полученный из process(exitValue(код зваершения) в поле ExitValue класса Operation объекта operation
         * Далее проверяем на наличие ошибок.
         */
        final ProcessBuilder processBuilder = new ProcessBuilder();
        final List<String> commandsAndArgumentsList = transformToListOfStrings(commandsArgumentsMap);
        processBuilder.command(commandsAndArgumentsList);
        final Process process = processBuilder.start();
        final InputStream errorStream = process.getErrorStream();
        final InputStream inputStream = process.getInputStream();
        final String inputStreamString = StreamUtils.copyToString(inputStream, Charset.forName("CP866"));
        final String errorStreamString = StreamUtils.copyToString(errorStream, Charset.forName("CP866"));
        Operation operation = new Operation();
        operation.setErrors(errorStreamString);
        operation.setResult(inputStreamString);
        String result = StringUtils.collectionToDelimitedString(commandsAndArgumentsList," ");
        operation.setText(result);
        operation.setExitValue(process.exitValue());
        if (!StringUtils.hasText(operation.getErrors())) {
            operation.setWentOk(true);

        } else {
            operation.setWentOk(false);
        }
        return operation;
    }

    /**
     * Создаем метод который на входе принимаеть карту из стринга и листа стрингов, а на выходе получаем лист стрингов.
     * @param commandsArgumentsMap
     * @return
     */
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
