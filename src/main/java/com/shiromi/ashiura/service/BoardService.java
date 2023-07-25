package com.shiromi.ashiura.service;

import com.shiromi.ashiura.domain.entity.DeclarationBoardEntity;
import com.shiromi.ashiura.domain.entity.UserEntity;
import com.shiromi.ashiura.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public DeclarationBoardEntity findByDeclaration(String declaration) {
        return boardRepository.findByDeclaration(declaration)
                .orElseThrow(IllegalAccessError::new);

    }
}
