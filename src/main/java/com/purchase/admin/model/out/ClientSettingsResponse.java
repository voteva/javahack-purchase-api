package com.purchase.admin.model.out;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientSettingsResponse {

    private String email;
    private String phone;
}
