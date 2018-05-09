package kr.hs.hejinkim.membermanager.assembler;

import kr.hs.hejinkim.membermanager.spring.ChangePasswordService;
import kr.hs.hejinkim.membermanager.spring.MemberDao;
import kr.hs.hejinkim.membermanager.spring.MemberRegisterService;

public class Assembler {
	private MemberDao memberDao;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;

	public Assembler() {
		memberDao = new MemberDao();
		regSvc = new MemberRegisterService(memberDao);
		pwdSvc = new ChangePasswordService(memberDao);
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public MemberRegisterService getMemberRegisterService() {
		return regSvc;
	}

	public ChangePasswordService getChangePasswordService() {
		return pwdSvc;
	}
}
