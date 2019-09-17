package com.purchase.admin.model.out;

import com.purchase.admin.enumeration.ContactType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SupplierAnswerResponse {

    private ContactType contactType;
    private String message;
}
