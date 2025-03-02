package com.workerp.common_lib.dto.company_app_service.reponse;

import lombok.Data;


import java.util.List;

@Data
public class CompanyResponse {
    private String id;
    private String owner;
    private String name;
    private String domain;
    private Boolean active;
    private List<ModuleResponse> modules;
}
