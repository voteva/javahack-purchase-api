package com.purchase.admin.model.in;

import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.enumeration.Engine;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchRequest {

    @NotBlank
    private String queryString;

    @NotNull
    private String message;

    @NotNull
    private List<Engine> engines;

    @NotNull
    private List<ContactType> contactTypes;
}
