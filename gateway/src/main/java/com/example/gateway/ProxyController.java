package com.example.gateway;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.user}")
    private String userService;

    @Value("${services.order}")
    private String orderService;

    @Value("${services.catalog}")
    private String catalogService;

    @RequestMapping("/api/users/**")
    public ResponseEntity<byte[]> proxyUsers(HttpServletRequest request,
                                             HttpMethod method,
                                             @RequestBody(required = false) byte[] body) {
        return forward(request, method, body, userService);
    }

    @RequestMapping("/api/orders/**")
    public ResponseEntity<byte[]> proxyOrders(HttpServletRequest request,
                                              HttpMethod method,
                                              @RequestBody(required = false) byte[] body) {
        return forward(request, method, body, orderService);
    }

    @RequestMapping("/api/catalog/**")
    public ResponseEntity<byte[]> proxyCatalog(HttpServletRequest request,
                                               HttpMethod method,
                                               @RequestBody(required = false) byte[] body) {
        return forward(request, method, body, catalogService);
    }

    private ResponseEntity<byte[]> forward(HttpServletRequest request,
                                           HttpMethod method,
                                           byte[] body,
                                           String targetBaseUrl) {

//        String targetUrl = targetBaseUrl +
//                request.getRequestURI().replaceFirst("/api", "");

        String targetUrl = targetBaseUrl + request.getRequestURI();


        HttpHeaders headers = new HttpHeaders();
//        Collections.list(request.getHeaderNames())
//                .forEach(h -> headers.add(h, request.getHeader(h)));
//        Collections.list(request.getHeaderNames())
//                .forEach(h -> {
//                    if (!h.equalsIgnoreCase("host") &&
//                            !h.equalsIgnoreCase("content-length") &&
//                            !h.equalsIgnoreCase("transfer-encoding") &&
//                            !h.equalsIgnoreCase("connection")) {
//                        headers.add(h, request.getHeader(h));
//                    }
//                });

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                targetUrl,
                method,
                entity,
                byte[].class
        );
    }
}

