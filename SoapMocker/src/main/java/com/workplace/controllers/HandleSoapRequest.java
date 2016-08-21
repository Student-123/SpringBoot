package com.workplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Santhosh on 8/21/2016.
 */
@RestController
@RequestMapping("/")
public class HandleSoapRequest {
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(){
        return "Hello";
    }
}
