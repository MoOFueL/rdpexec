package ru.seregamoskal.rdpExec.services;

import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class TelNetService {

    private static final int PORT = 3389;
    private static final Logger LOGGER = LoggerFactory.getLogger(TelNetService.class);

    /**
     * Метод ищет все доступные IP адреса серверов, доступных по RDP, из переданных подсетей
     *
     * @param subnets - список подсетей для поиска серверов, доступных по RDP
     * @return - список IP адресов серверов, доступных по RDP
     */
    public Set<String> findServersAvalaibleByRDPConnection(List<String> subnets) {

        Assert.notEmpty(subnets, "List of subnets must not be null or empty!");
        final Set<String> result = new HashSet<>();
        final Set<String> ipAddressSet = new HashSet<>();
        for (String subnet : subnets) {
            final SubnetUtils subnetUtils = new SubnetUtils(subnet);
            ipAddressSet.addAll(Arrays.asList(subnetUtils.getInfo().getAllAddresses()));
        }
        LOGGER.info("Starting an internet address poll");
        for (String ipAddress : ipAddressSet) {
            if (availableBySocket(ipAddress, PORT)) {
                result.add(ipAddress);
            }
        }
        LOGGER.info("Internet address poll finished");
        return result;
    }

    /**
     * Метод проверяет доступность переданного IP адреса по указанному порту
     *
     * @param ip   - IP адрес для проверки
     * @param port - порт
     * @return доступен или нет данный адрес по указанному порту
     */
    private boolean availableBySocket(String ip, int port) {
        boolean result;
        try (Socket ignored = new Socket(ip, port)) {
            result = true;
        } catch (IOException ignored) {
            LOGGER.warn("Server with IP address: {} and port {} is not available", ip, port);
            result = false;
        }
        return result;
    }
}
