package ru.seregamoskal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.services.LoginInfoService;

import java.io.IOException;

/**
 * @author Dmitriy
 */
@RestController
@RequestMapping(path = "logins", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginInfoController {

    private LoginInfoService loginInfoService;

    @Autowired
    public void setLoginInfoService(LoginInfoService loginInfoService) {
        Assert.notNull(loginInfoService);
        this.loginInfoService = loginInfoService;
    }

    @RequestMapping(path = "/uploadCsv", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public void uploadFromCsv(MultipartFile multipartFile) throws IOException {
        loginInfoService.parseFromCsv(multipartFile);
    }
}
