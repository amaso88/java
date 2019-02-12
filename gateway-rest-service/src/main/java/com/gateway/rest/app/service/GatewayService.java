package com.gateway.rest.app.service;

import com.gateway.rest.app.domain.Gateway;
import com.gateway.rest.app.domain.PeripheralDevice;
import com.gateway.rest.app.repository.GatewayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("gatewayService")
public class GatewayService {

    @Autowired
    @Qualifier("gatewayRepository")
    private GatewayRepository gatewayRepository;

    public Gateway getGateway(long id){
        return gatewayRepository.getOne(id);
    }

    public Gateway add(Gateway gateway){
        return gatewayRepository.save(gateway);
    }

    public Gateway update(Gateway gateway){
        Gateway gateway1 = null;
        Optional<Gateway> optional = gatewayRepository.findById(gateway.getId());

        if (!optional.isPresent())
            return null;
        return gatewayRepository.save(gateway);
    }

    public void delete(long id){
        gatewayRepository.delete(gatewayRepository.getOne(id));
    }

    public Page<Gateway> findAll(Pageable pageable){
        return gatewayRepository.findAll(pageable);
    }
}
