package com.gateway.rest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "peripheral_device")
public class PeripheralDevice implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
	private long id;

	@Column(name="uid", unique=true)
	private long uid;

	@NotEmpty
	@Column(name="vendor")
	private String vendor;

	@Column(name="created")
	private LocalDate created;

	@Pattern(regexp = "^(online|offline)$", message = "please, must be enter: online or offline value")
    @Column(name = "status")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "gateway_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Gateway gateway;
	
	public PeripheralDevice() {}

	public PeripheralDevice(long id, long uid, String vendor, LocalDate created, String status) {
		super();
		this.id = id;
		this.uid = uid;
		this.vendor = vendor;
		this.created = created;
		this.status = status;
	}

	public PeripheralDevice(long uid, String vendor, LocalDate created, String status) {
		super();
		this.uid = uid;
		this.vendor = vendor;
		this.created = created;
		this.status = status;
	}

	public PeripheralDevice(long uid, String vendor, LocalDate created, String status, Gateway gateway) {
		this.uid = uid;
		this.vendor = vendor;
		this.created = created;
		this.status = status;
		this.gateway = gateway;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Gateway getGateway() {
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	@Override
    public String toString() {
        return getVendor();
    }
	
}
