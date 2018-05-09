package kr.hs.hejinkim.membermanager.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.support.GenericXmlApplicationContext;

import kr.hs.hejinkim.membermanager.assembler.Assembler;
import kr.hs.hejinkim.membermanager.spring.AlreadyExistingMemberException;
import kr.hs.hejinkim.membermanager.spring.ChangePasswordService;
import kr.hs.hejinkim.membermanager.spring.IdPasswordNotMatchingException;
import kr.hs.hejinkim.membermanager.spring.MemberNotFoundException;
import kr.hs.hejinkim.membermanager.spring.MemberRegisterService;
import kr.hs.hejinkim.membermanager.spring.RegisterRequest;

public class MainForSpring {
	private static GenericXmlApplicationContext ctx; // static 메서드에서 일반 변수에 접근 할 수 없기 때문에, static으로 선언함.

	public static void main(String[] args) throws IOException {

		ctx = new GenericXmlApplicationContext("classpath:AppCtx.xml");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("명렁어를 입력하세요:");
			String command = reader.readLine();
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if (command.startsWith("new ")) {
				processNewCommand(command.split(" "));
				continue;
			} else if (command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
				continue;
			}
			printHelp();
		}
	}

	private static Assembler assembler = new Assembler();

	private static void processNewCommand(String[] arg) {
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		ChangePasswordService changePasswordService = ctx.getBean("changePwdSvc", ChangePasswordService.class);

		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);

		if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("등록했습니다.\n");
		} catch (AlreadyExistingMemberException e) {
			System.out.println("이미 존재하는 이메일입니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService changePwdSvc = assembler.getChangePasswordService();
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		} catch (IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println();
	}
}
