package com.purchase.admin.redis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SupplierCallTask
        extends RedisTask {

    private UUID taskUid;
    private UUID supplierUid;
    private String phone;
}
