package com.shiromi.ashiura.domain.entity;

import com.shiromi.ashiura.domain.dto.VoiceDataDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@Table(name = "declaration_board")
@Setter
@Getter
@Entity
public class DeclarationBoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    @Column(nullable = false, unique = true)
    private String declaration;

    @Column
    private String risk; //persent

    @Column(name="created_date", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDate createdDate;

    @Column(name="modified_date")
    private LocalDate modifiedDate;

    @Column
    private int up;

    @Column
    private int down;

    @Builder
    public DeclarationBoardEntity(Long id, String declaration, String content, LocalDate createdDate,
                                  LocalDate modifiedDate, int up, int down) {
        this.id = id;
        this.declaration = declaration;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.up = up;
        this.down = down;
    }
}

