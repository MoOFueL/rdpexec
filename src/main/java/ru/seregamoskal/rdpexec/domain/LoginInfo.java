package ru.seregamoskal.rdpexec.domain;

import javax.persistence.*;

/**
 * @author Dmitriy
 */
@Entity
@Table(name = "login_info")
public class LoginInfo {

    @Id
    @SequenceGenerator(name = "login_info_id_seq", sequenceName = "login_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_info_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * Логин учетной записи для входа
     */
    private String login;

    /**
     * Пароль учетной записи для входа
     */
    private String password;

    public LoginInfo() {
    }

    public LoginInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginInfo(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
