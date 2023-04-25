package com.philimonov.converter;

import com.philimonov.dao.ReportCategoryModel;
import com.philimonov.service.ReportCategoryDTO;

public class ReportCategoryModelToReportCategoryDtoConverter implements Converter<ReportCategoryModel, ReportCategoryDTO> {
    @Override
    public ReportCategoryDTO convert(ReportCategoryModel source) {
        ReportCategoryDTO reportCategoryDTO = new ReportCategoryDTO();
        reportCategoryDTO.setName(source.getName());
        reportCategoryDTO.setAmount(source.getAmount());
        return reportCategoryDTO;
    }
}
