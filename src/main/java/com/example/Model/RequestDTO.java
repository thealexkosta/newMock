package com.example.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RequestDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;

}
