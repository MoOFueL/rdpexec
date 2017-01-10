package ru.seregamoskal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.seregamoskal.domain.ServerInfo;
import ru.seregamoskal.services.ServerInfoService;

/**
 * @author Dmitriy
 */
@RestController
@RequestMapping(path = "/servers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ServerInfoController {

    private ServerInfoService serverInfoService;

    @Autowired
    public void setServerInfoService(ServerInfoService serverInfoService) {
        this.serverInfoService = serverInfoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<ServerInfo> getList(Pageable pageable) {
        return serverInfoService.findAll(pageable);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public ServerInfo getByName(@PathVariable("name") String serverName) {
        return serverInfoService.findByName(serverName);
    }
}
