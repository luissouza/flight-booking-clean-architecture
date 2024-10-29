package org.pt.flightbooking.adapters.controller;

import org.pt.flightbooking.adapters.builder.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BaseController {

    public long timeStamp;

    @Autowired
    protected ResponseBuilder responseDefault;

    public BaseController() {
        timeStamp = new Date().getTime();
    }
}
