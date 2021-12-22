package com.kamila.food.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HostCheckController {

    @GetMapping("/hostcheck")
    public String checkHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() // IP do container
                + " - "
                + InetAddress.getLocalHost().getHostName();
    }

}
