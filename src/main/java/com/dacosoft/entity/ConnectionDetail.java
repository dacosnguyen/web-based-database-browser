package com.dacosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "connection_detail")
public final class ConnectionDetail {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;            // custom name of the database instance
    @Column(name = "hostname")
    private String hostname;        // hostname of the database
    @Column(name = "port")
    private int port;            // port where the database runs
    @Column(name = "databasename")
    private String databaseName;    // name of the database
    @Column(name = "username")
    private String username;        // username for connecting to the database
    @Column(name = "password")
    private String password;        // password for connecting to the database
    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionDetail that = (ConnectionDetail) o;
        return port == that.port &&
                id.equals(that.id) &&
                name.equals(that.name) &&
                hostname.equals(that.hostname) &&
                databaseName.equals(that.databaseName) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hostname, port, databaseName, username, password, description);
    }
}
