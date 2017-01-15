package ru.seregamoskal.rdpExec.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Дмитрий on 11.01.2017.
 */
@Service
public class TelNetService {
    private static boolean available(String ip, int port) {
        try (Socket ignored = new Socket(ip, port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }
}
