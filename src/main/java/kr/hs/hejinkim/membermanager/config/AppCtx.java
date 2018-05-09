package kr.hs.hejinkim.membermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.hs.hejinkim.membermanager.spring.ChangePasswordService;
import kr.hs.hejinkim.membermanager.spring.MemberDao;
import kr.hs.hejinkim.membermanager.spring.MemberRegisterService;

@Configuration
public class AppCtx {

	@Bean
	MemberDao memberDao() {
		return new MemberDao();
	}

	@Bean
	MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao()); // memberDao객체를 여러 번 생성되는 것이 아닌 한 번 만 객체 생성되고 재 사용되는 것.
	}

	@Bean
	ChangePasswordService changePwdSvc() {
		return new ChangePasswordService(memberDao());
	}
}
