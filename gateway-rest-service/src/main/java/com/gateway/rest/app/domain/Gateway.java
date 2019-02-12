package com.gateway.rest.app.domain;

import com.gateway.rest.app.validators.Ipv4Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "gateway")
public class Gateway implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private long id;

    @NotEmpty
    @Column(name = "serial", unique = true)
    private String serial;

    @Pattern(regexp = "^[A-Za-z0-9 _-]+$", message = "please, must be enter a human-readable name")
    @Column(name = "name")
    private String name;

    @Ipv4Validator
    @Column(name = "ipaddress")
    private String ipaddress;

    public Gateway() {}

    public Gateway(long id, String serial, String name, String ipaddress) {
        super();
        this.id = id;
        this.serial = serial;
        this.name = name;
        this.ipaddress = ipaddress;
    }

    public Gateway(String serial, String name, String ipaddress) {
        super();
        this.serial = serial;
        this.name = name;
        this.ipaddress = ipaddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
