package com.dacosoft.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "PUBLIC", name = "CONNECTION_DETAIL")
public final class ConnectionDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;            // custom name of the database instance
    @Column(name = "HOSTNAME")
    private String hostname;        // hostname of the database
    @Column(name = "PORT")
    private int port;            // port where the database runs
    @Column(name = "DATABASENAME")
    private String databasename;    // name of the database
    @Column(name = "USERNAME")
    private String username;        // username for connecting to the database
    @Column(name = "PASSWORD")
    private String password;        // password for connecting to the database
    @Column(name = "DESCRIPTION")
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

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databaseName) {
        this.databasename = databaseName;
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
                databasename.equals(that.databasename) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hostname, port, databasename, username, password, description);
    }
}
