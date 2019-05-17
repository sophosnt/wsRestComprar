package com.sophos.poc.wsrestcomprar.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophos.poc.wsrestcomprar.model.PagarCompraReq;
import com.sophos.poc.wsrestcomprar.model.PagarCompraRes;
import com.sophos.poc.wsrestcomprar.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentApiController implements PaymentApi {

    private static final Logger log = LoggerFactory.getLogger(PaymentApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private PaymentService service;

    @org.springframework.beans.factory.annotation.Autowired
    public PaymentApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<PagarCompraRes> payPost(
    		@RequestHeader(value="X-RqUID", required=false) String xRqUID,
    		@RequestHeader(value="X-Channel", required=false) String xChannel,
    		@RequestHeader(value="X-IPAddr", required=false) String xIPAddr,
    		@RequestHeader(value="X-Session", required=false) String xSession,
    		@RequestHeader(value="X-haveToken", required=false) String xHaveToken,
    		@Valid @RequestBody PagarCompraReq postPayment) {
        
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	PagarCompraRes response = service.payBill(postPayment);
            	if(response!=null) {
            		return new ResponseEntity<PagarCompraRes>(response, HttpStatus.OK);
            	}
                return new ResponseEntity<PagarCompraRes>(objectMapper.readValue("{  \"date\" : \"2000-01-23T04:56:07.000+00:00\",  \"account_type\" : \"account_type\",  \"account_id_to\" : 6,  \"account_id\" : 0,  \"id_trn\" : \"id_trn\",  \"account_type_to\" : \"account_type_to\",  \"currency\" : \"currency\",  \"server_date\" : \"2000-01-23T04:56:07.000+00:00\",  \"ammount\" : \"ammount\",  \"auth_code\" : \"auth_code\",  \"status\" : {    \"status_code\" : \"status_code\",    \"status_desc\" : \"status_desc\",    \"status_info\" : \"status_info\",    \"additional_status_code\" : \"additional_status_code\",    \"additional_status_desc\" : \"additional_status_desc\"  }}", PagarCompraRes.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<PagarCompraRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<PagarCompraRes>(HttpStatus.NOT_IMPLEMENTED);
    }

}
