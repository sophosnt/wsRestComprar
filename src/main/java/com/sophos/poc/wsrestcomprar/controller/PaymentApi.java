package com.sophos.poc.wsrestcomprar.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sophos.poc.wsrestcomprar.model.PagarCompraReq;
import com.sophos.poc.wsrestcomprar.model.PagarCompraRes;

public interface PaymentApi {

    @RequestMapping(value = "/pay",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<PagarCompraRes> payPost(
    		@RequestHeader(value="X-RqUID", required=false) String xRqUID,
    		@RequestHeader(value="X-Channel", required=false) String xChannel,
    		@RequestHeader(value="X-IPAddr", required=false) String xIPAddr,
    		@RequestHeader(value="X-Session", required=false) String xSession,
    		@RequestHeader(value="X-haveToken", required=false) String xHaveToken,
    		@Valid @RequestBody PagarCompraReq postPayment);

}
