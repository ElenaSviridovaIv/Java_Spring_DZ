package com.example.newMock.Controller;


import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
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
import java.util.Random;
import java.math.RoundingMode;


@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )

    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            double maxLim;
            String rqUID = requestDTO.getRqUID();
            String curr = "";

            if (firstDigit == '8') {
                maxLim = 2000.00;
                maxLimit = new BigDecimal(maxLim);
                curr="US";

            } else if (firstDigit == '9') {
                maxLim = 1000.00;
                maxLimit = new BigDecimal(maxLim);
                curr="EU";
            } else {
                maxLim = 10000.00;
                maxLimit = new BigDecimal(maxLim);
                curr="RU";
            }
            Random random = new Random();
            double randomDouble = random.nextDouble() * maxLim;
            BigDecimal newBalance = new BigDecimal(randomDouble).setScale(2, RoundingMode.HALF_UP);


            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(curr);
            responseDTO.setBalance(newBalance);
            responseDTO.setMaxLimit(maxLimit);

            log.info("________________---RequestDTO---________________ " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("________________---ResponseDTO---________________" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        }
    }