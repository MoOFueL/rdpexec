package ru.seregamoskal.rdpexec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.seregamoskal.rdpexec.domain.ServerInfo;
import ru.seregamoskal.rdpexec.services.ServerInfoService;

import java.util.List;

/**
 * @author Dmitriy
 */
@RestController
@RequestMapping(path = "/servers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ServerInfoController {

    private ServerInfoService serverInfoService;

    @Autowired
    public void setServerInfoService(ServerInfoService serverInfoService) {
        Assert.notNull(serverInfoService);
        this.serverInfoService = serverInfoService;
    }

    @GetMapping
    public Page<ServerInfo> getList(Pageable pageable) {
        return serverInfoService.findAll(pageable);
    }

    @GetMapping(path = "/{name}")
    public List<ServerInfo> getByName(@PathVariable("name") String serverName) {
        return serverInfoService.findByName(serverName);
    }
}
