package com.purchase.admin.model.out;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class QueryInfoResponse {

    private UUID queryUid;
    private String queryString;
    private String createdDate;
}
