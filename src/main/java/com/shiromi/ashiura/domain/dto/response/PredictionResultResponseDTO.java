package com.shiromi.ashiura.domain.dto.response;


import lombok.*;


@NoArgsConstructor
@Getter
@Setter
public class PredictionResultResponseDTO {

    private Long idx;
    private String declaration;
    private String progress;
    private String voiceResult;
    private String mfccResult;

    @Builder
    public PredictionResultResponseDTO(Long idx, String declaration, String progress, String voiceResult, String mfccResult) {
        this.idx = idx;
        this.declaration = declaration;
        this.progress = progress;
        this.voiceResult = voiceResult;
        this.mfccResult = mfccResult;
    }
}
