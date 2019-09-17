package com.purchase.admin.model.out;

import com.purchase.admin.enumeration.SupplierStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuerySupplierResponse {

    private String supplierName;
    private String url;
    private String email;
    private String phone;
    private SupplierStatus status;
}
