package com.purchase.admin.model.out;

import com.purchase.admin.enumeration.SupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SupplierInfoResponse {

    private UUID supplierUid;
    private String name;
    private String url;
    private String email;
    private String phone;
    private SupplierStatus status;
    private List<SupplierAnswerResponse> answers;
}
