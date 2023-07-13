package com.shiromi.ashiura.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class FileDomain {

    private String fileName;

    @Builder

    public FileDomain(String fileName) {
        this.fileName = fileName;
    }
}
