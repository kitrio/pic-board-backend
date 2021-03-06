package com.j.board.domain;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MemberVO {
	@NotEmpty
	@Size(min=2, max=12)
	private String memberId;

	@NotEmpty
	@Size(min=8)
	private String password;

	@NotEmpty
	@Size(max=12)
  	private String nickname;

	private Timestamp regDate;
	private Timestamp lastLogin;

	@NotEmpty
	private String authInfo;

	public MemberVO() {
		super();
	}

	public MemberVO(String memberId, String password, String nickname, Timestamp regDate, Timestamp lastLogin, String authInfo) {
		this.memberId = memberId;
		this.password = password;
		this.nickname = nickname;
		this.regDate = regDate;
		this.lastLogin = lastLogin;
		this.authInfo = authInfo;
	}

	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public String getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
}