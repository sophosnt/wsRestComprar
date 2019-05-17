package com.sophos.poc.wsrestcomprar.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophos.poc.wsrestcomprar.model.PagarCompraReq;
import com.sophos.poc.wsrestcomprar.model.PagarCompraRes;
import com.sophos.poc.wsrestcomprar.model.StatusType;
import com.sophos.poc.wsrestcomprar.util.BuildISOMessage;
import com.sophos.poc.wsrestcomprar.util.ParseISOMessage;

@Service
public class PaymentService {
	@Autowired
	private BuildISOMessage isoBuilder;
	@Autowired
	private ParseISOMessage parseIsoMessage;
	@Autowired
	private SwitchClientSocket switchClient;
	private final String balanceTrn = "0200";
	private static final Logger logger = LogManager.getLogger(PaymentService.class);
	
	
	public PagarCompraRes payBill(@Valid PagarCompraReq postPayment) {
		PagarCompraRes response = new PagarCompraRes();
		StatusType status = new StatusType();
		response.setId_trn(postPayment.getId_trn());
		response.setDate(postPayment.getDate());
		Date serverDate = Calendar.getInstance().getTime();
		response.setServer_date(serverDate);		
		isoBuilder.getInstance();
		HashMap<Integer, String> campos = new  HashMap<>();
		campos.put(3, "00"+postPayment.getAccount_type()+postPayment.getAccount_type_to());
		campos.put(7, getFormatDate("MMddHHmmss", postPayment.getDate()));
		campos.put(11, getRandomNumberString());
		campos.put(102, postPayment.getAccount_id().toString());
		campos.put(103, postPayment.getAccount_id_to().toString());
		campos.put(104, "PAGO EN ESTABLECIMIENTO");
		String isoMessage = isoBuilder.createIsoMessagge(campos, balanceTrn);
		logger.info("ISO-Message:" +isoMessage);
		if(isoMessage!=null) {
			try {
				String rs = switchClient.callSwitch(isoMessage);
				parseIsoMessage.getInstance();
				HashMap<Integer, String> rsFields = parseIsoMessage.getMapValues(rs);
				response.setAuth_code(rsFields.get(38));
				status.setStatus_code(rsFields.get(39));
				status.setAdditional_status_code(rsFields.get(11));
				status.setAdditional_status_desc(rsFields.get(105));	
				if(status.getStatus_code().equals("00")) {
					status.setStatus_desc("Transaccion Exitosa");
					status.setStatus_info("Info");
					response.setAccount_id(postPayment.getAccount_id());
					response.setAccount_type(postPayment.getAccount_type());
					response.setAccount_id_to(postPayment.getAccount_id_to());
					response.setAccount_type_to(postPayment.getAccount_type_to());
					response.setAmmount(postPayment.getAmmount());
					response.setCurrency(postPayment.getCurrency());
				}else {
					status.setStatus_info("Warn");
					status.setStatus_desc(rsFields.get(44));
				}							
				response.setStatus(status);					
				return response;
			}catch (ISOException e) {
				logger.error("Error al interpretar respuesta del Switch:");
			}catch (UnknownHostException ex) {
				logger.error("Error UnknownHostException el Host no es valido o accesible");
			}catch (IOException e) {
				logger.error("Error al intentar establecer la comunicacion con el Switch");
			} 
		}else {
			status.setStatus_code("999");
			status.setStatus_desc("Se presento un error al construir el mensaje ISO");
			status.setStatus_info("Error");
			status.setAdditional_status_code("999");
			status.setAdditional_status_desc("Validar los datos enviados");
			response.setStatus(status);
			return response;
		}	
		return null;
	}
	
	
	private String getRandomNumberString() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
	
	private String getFormatDate(String formato, Date value) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		format.format(value);
		return format.format(value);
	}
	
	@SuppressWarnings("unused")
	private String padFormatString(String campo, int length, String sentido) {
		if(sentido.equals("R")) {
			return String.format("%-" + length + "s", campo); 
		}else {
			return String.format("%" + length + "s", campo);
		}		
	}
}
