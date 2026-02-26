package com.example.demo.service;

import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(Member member);
    List<Member> findMembers();
    Optional<Member> findOne(Long memberId);
    public void transfer(String fromName,String toName,String toAccount,int amount);



}
