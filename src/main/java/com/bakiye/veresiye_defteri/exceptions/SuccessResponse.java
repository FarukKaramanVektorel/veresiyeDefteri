package com.bakiye.veresiye_defteri.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
    private boolean success;
    private String message;

  
   
}

