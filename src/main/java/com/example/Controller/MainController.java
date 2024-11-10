package com.example.Controller;

import com.example.Model.RequestDTO;
import com.example.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
public class MainController {

   private Logger log = LoggerFactory.getLogger(MainController.class);

   ObjectMapper mapper = new ObjectMapper();

   @PostMapping(
           //MARK: Адрес нашей заглушки
        value = "/info/postBalances",
        //MARK: Переменные для логирования
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
   )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
       try {
           String clientID = requestDTO.getClientId();
           char firstDigit = clientID.charAt(0);
           BigDecimal maxLimit;
           String RqUID = requestDTO.getRqUID();
           String currency = "";
           if (firstDigit == '8') {
               currency = "US";
               maxLimit = new BigDecimal(2000);
           } else if (firstDigit =='9') {
               currency = "EU";
               maxLimit = new BigDecimal(1000);
           } else {
               currency = "RUB";
               maxLimit = new BigDecimal(10000);
           }

           ResponseDTO responseDTO = new ResponseDTO();

           //MARK: Задаем поля нашего ответа
           responseDTO.setRqUID(RqUID);
           responseDTO.setClientId(clientID);
           responseDTO.setAccount(requestDTO.getAccount());
           responseDTO.setCurrency(currency);
           responseDTO.setBalance(new BigDecimal(7777));
           responseDTO.setMaxLimit(maxLimit);

            log.error("********** RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** ResponseDTO **********" +mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
       }catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
   }
}
