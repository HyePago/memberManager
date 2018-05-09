package kr.hs.hejinkim.membermanager.spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	// 실제 DB를 연동한 것이 아니라, DB가 있다고 치고 짠 코드이다.

	private static long nextId = 0;

	private Map<String, Member> map = new HashMap<>();

	public Member selectByEmail(String email) {
		return map.get(email); // get함수: 있으면 value, 없으면 null이 나오는 함수
	}

	public void insert(Member member) {
		member.setId(++nextId);
		map.put(member.getEmail(), member);
	}

	public void update(Member member) {
		map.put(member.getEmail(), member);
	}

	public Collection<Member> selectAll() {
		return map.values();
	}

}
