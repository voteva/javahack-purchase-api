package com.purchase.admin.model.out;

import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.enumeration.Engine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class SearchResponse {

    private UUID queryUid;
    private String queryString;
    private List<Engine> engines;
    private List<SupplierInfoResponse> suppliers;
    private List<ContactType> contactTypes;
}
