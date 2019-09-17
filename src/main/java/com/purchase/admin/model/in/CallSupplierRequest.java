package com.purchase.admin.model.in;

import com.purchase.admin.enumeration.ContactType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CallSupplierRequest {

    @NotNull
    private UUID supplierUid;

    @NotNull
    private List<ContactType> contactTypes;
}
