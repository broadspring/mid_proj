package com.hg.pj.member;

public interface MemberMapper {

	int join(Member m);

	Member getMemberByID(Member m);

	int update(Member m);

	int bye(Member m);

	int getMemberNum(Member m);

}
