package com.gateway.rest.app.service;

import com.gateway.rest.app.domain.PeripheralDevice;
import com.gateway.rest.app.repository.PeripheralDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("peripheralDeviceService")
public class PeripheralDeviceService {
    @Autowired
    @Qualifier("peripheralDeviceRepository")
    private PeripheralDeviceRepository peripheralDeviceRepository;

    public PeripheralDevice add(PeripheralDevice peripheralDevice){
        return peripheralDeviceRepository.save(peripheralDevice);
    }

    public PeripheralDevice update(PeripheralDevice peripheralDevice){
        return peripheralDeviceRepository.save(peripheralDevice);
    }

    public void delete(long id){
        peripheralDeviceRepository.delete(peripheralDeviceRepository.getOne(id));
    }

    public Page<PeripheralDevice> findAll(Pageable pageable){
        return peripheralDeviceRepository.findAll(pageable);
    }

    public PeripheralDevice getPeripheralDevice(long id) {
        return peripheralDeviceRepository.getOne(id);
    }

    public Page<PeripheralDevice> findByGatewayId(Long gatewayId, Pageable pageable) {
        return peripheralDeviceRepository.findByGatewayId(gatewayId,pageable);
    }

    public List<PeripheralDevice> findByGatewayId(Long gatewayId){
        return peripheralDeviceRepository.findByGatewayId(gatewayId);
    }
}
