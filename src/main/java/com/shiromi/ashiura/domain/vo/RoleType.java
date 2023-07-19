package com.shiromi.ashiura.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public  enum RoleType {
	//권한 생성
	ROLE_NORMAL_USER("USER"),
	ROLE_ADMIN("ADMIN");
	private String value;
//	USER, ADMIN
}
