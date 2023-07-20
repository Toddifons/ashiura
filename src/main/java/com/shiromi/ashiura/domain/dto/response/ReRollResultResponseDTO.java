package com.shiromi.ashiura.domain.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReRollResultResponseDTO {

    private String idx;
    private String phone;
    private String result;

    @Builder
    public ReRollResultResponseDTO(String idx, String phone, String result) {
        this.idx = idx;
        this.phone = phone;
        this.result = result;
    }
}
