package com.example.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class PingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return pong;
    }

    @RequestMapping(path = "/foo/{gender}/bar/{age}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String complexRequest(@RequestBody String body,
                                 @PathVariable("gender") String gender,
                                 @PathVariable("age") String age,
                                 @RequestParam("name") String name
    ) {
        System.out.println("Body: " + body + " - " + gender + "/" + age + "/" + name);
        return gender + "/" + age + "/" + name;
    }

    @GetMapping(value = {"/server-info"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequestInfo(@RequestHeader LinkedHashMap<String, String> httpHeaders, HttpServletRequest httpServletRequest) {
        httpHeaders.put("remoteHost", httpServletRequest.getRemoteHost());
        httpHeaders.put("localAddress", httpServletRequest.getLocalAddr());
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            httpHeaders.put("hostName", localHost.getHostName());
            httpHeaders.put("hostAddress", localHost.getHostAddress());
            httpHeaders.put("canonicalHostName", localHost.getCanonicalHostName());
            httpHeaders.put("serverLocalDateTime", LocalDateTime.now().toString());
            httpHeaders.put("serverZonedDateTime", ZonedDateTime.now().toString());
            httpHeaders.put("serverOffsetDateTime", OffsetDateTime.now().toString());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return httpHeaders;
    }

    @GetMapping("/exception")
    public void exception() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}
