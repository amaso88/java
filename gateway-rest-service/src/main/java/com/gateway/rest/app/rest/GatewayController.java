package com.gateway.rest.app.rest;

import com.gateway.rest.app.domain.Gateway;
import com.gateway.rest.app.domain.PeripheralDevice;
import com.gateway.rest.app.service.GatewayService;
import com.gateway.rest.app.service.PeripheralDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Api(value="/v1",description="Gateway Manager",produces ="application/json")
@RequestMapping("/v1")
public class GatewayController {

    private static final Logger _log = LoggerFactory.getLogger(GatewayController.class);
    
    @Autowired
    @Qualifier("gatewayService")
    private GatewayService gatewayService;

    @Autowired
    @Qualifier("peripheralDeviceService")
    private PeripheralDeviceService peripheralDeviceService;

    /*
    * Get Gateway By Id
    * */
    @ApiOperation(value="get gateway",response=Gateway.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Gateway Details Retrieved",response=Gateway.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })

    @CrossOrigin
    @GetMapping("/gateway/{gatewayId}")
    public ResponseEntity<Gateway> getGateway(@PathVariable("gatewayId") long gatewayId){
        Gateway gatewayStored = new Gateway();
        try {
            gatewayStored = gatewayService.getGateway(gatewayId);
            return new ResponseEntity<Gateway>(gatewayStored, HttpStatus.OK);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
        return new ResponseEntity<Gateway>(gatewayStored, HttpStatus.BAD_REQUEST);
    }

    /*
    * Get Pageable Gateway List
    * */
    @ApiOperation(value="get pageable gateway list",response=Collection.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Gateway list retrieved",response=Collection.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @GetMapping("/gateway")
    public Page<Gateway> getAllGateway(Pageable pageable) {
        return gatewayService.findAll(pageable);
    }

    /*
    * Get all devices related with a specific Gateway
    * */
    @ApiOperation(value="get all devices related with a specific gateway",response=Collection.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Devices list retrieved",response=Collection.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @GetMapping("/gateway/devices/{gatewayId}")
    public Page<PeripheralDevice> getAllPeripheralDeviceByGatewayId(@PathVariable("gatewayId") Long gatewayId,
                                                                 Pageable pageable) {
        return peripheralDeviceService.findByGatewayId(gatewayId, pageable);
    }

    /*
    * Add a new Gateway
    *
    * */
    @ApiOperation(value="add a new gateway",response=Gateway.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Gateway Details retrieved",response=Gateway.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @PostMapping("/gateway")
    public ResponseEntity<Gateway> addGateway(@RequestBody @Valid Gateway gateway){
        Gateway gatewayStored = new Gateway();
        try {
            gatewayStored = gatewayService.add(gateway);
            new ResponseEntity<Gateway>(gatewayStored, HttpStatus.OK);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
        return new ResponseEntity<Gateway>(gatewayStored, HttpStatus.BAD_REQUEST);
    }


    /*
     * Update a Gateway
     *
     * */
    @ApiOperation(value="uppdate a gateway",response=Gateway.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Gateway Details retrieved",response=Gateway.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @PutMapping("/gateway/{gatewayId}")
    public ResponseEntity<Gateway> updateGateway(@PathVariable("gatewayId") Long gatewayId, @Valid @RequestBody Gateway gatewayRequest) {
        Gateway gatewayStored = new Gateway();
        try {
            gatewayStored =  gatewayService.getGateway(gatewayId);
            gatewayStored.setIpaddress(gatewayRequest.getIpaddress());
            gatewayStored.setName(gatewayRequest.getName());
            gatewayStored.setSerial(gatewayRequest.getSerial());

        }catch (Exception e){
            _log.error(e.getMessage());
        }
        gatewayStored = gatewayService.update(gatewayStored);

        return new ResponseEntity<Gateway>(gatewayStored, HttpStatus.OK);
    }

    /*
    * Delete a Gateway
    * */
    @ApiOperation(value="delete gateway")
    @ApiResponses(value={
            @ApiResponse(code=200,message="Operation OK"),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @DeleteMapping("/gateway/{gatewayId}")
    public void deleteGateway(@PathVariable("gatewayId") Long gatewayId) {
        try {
            peripheralDeviceService.findByGatewayId(gatewayId).forEach(device->{
                peripheralDeviceService.delete(device.getId());
            });
            gatewayService.delete(gatewayId);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
    }

}
