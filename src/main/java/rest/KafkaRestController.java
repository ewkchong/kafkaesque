package rest;

import brokers.Broker;
import brokers.DefaultBroker;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import partitions.DefaultPartition;
import partitions.Partition;
import rest.errors.WrongServer;
import topics.Topic;


@RestController
public class KafkaRestController{
    Broker broker; //this is just for now until we have the main broker
    Partition partitionDriverData;
    Partition partitionRiderData;
    Partition partitionRiderRequestData;
    Partition partitionDriverRequestData;
    Partition partitionRiderAcceptsData;

    @Autowired
    public void setup(){
        this.broker = new DefaultBroker();
        partitionDriverData = new DefaultPartition(Topic.DRIVER_DATA);
        partitionRiderData = new DefaultPartition(Topic.RIDER_DATA);
        partitionRiderRequestData = new DefaultPartition(Topic.RIDER_REQUESTS_RIDE);
        partitionDriverRequestData = new DefaultPartition(Topic.DRIVER_REQUESTS_RIDE);
        partitionRiderAcceptsData = new DefaultPartition(Topic.RIDER_ACCEPTS_RIDE);
        //broker.addPartition(partitionDriverData);
        broker.addPartition(partitionRiderData);
        broker.addPartition(partitionRiderRequestData);
        broker.addPartition(partitionDriverRequestData);
        broker.addPartition(partitionRiderAcceptsData);

    }





    @GetMapping("/driverlocation")
    public RestInput GetDriverLocation(@RequestParam(value="id",defaultValue = "0")long id,
                                    @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                    @RequestParam(value="x",defaultValue = "0")double x,
                                    @RequestParam(value="y",defaultValue = "0")double y,
                                    @RequestParam(value="offset",defaultValue = "0")long offset){
        if(!broker.getPartitions().contains(partitionDriverData)){
            throw new WrongServer("check 192.168.1.0");

        }


        return new RestInput(id,city,x,y,offset);

    }

    @GetMapping("/riderlocation")
    public RestInput GetRiderData(@RequestParam(value="id",defaultValue = "0")long id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){
        return new RestInput(id,city,x,y,offset);


    }

    @GetMapping("/userrequests")
    public RestInput GetUserRequests(@RequestParam(value="id",defaultValue = "0")long id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){
        return new RestInput(id,city,x,y,offset);


    }

    @GetMapping("/driverrequests")
    public RestInput GetDriverRequests(@RequestParam(value="id",defaultValue = "0")long id,
                                    @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                    @RequestParam(value="x",defaultValue = "0")double x,
                                    @RequestParam(value="y",defaultValue = "0")double y,
                                    @RequestParam(value="offset",defaultValue = "0")long offset){

        return new RestInput(id,city,x,y,offset);

    }

    @GetMapping("/useraccepts")
    public RestInput GetUserAccepts(@RequestParam(value="id",defaultValue = "0")long id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){

        return new RestInput(id,city,x,y,offset);

    }

}
