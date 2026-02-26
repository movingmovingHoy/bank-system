package com.example.demo.repository;

import aQute.bnd.annotation.jpms.Open;
import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    public Optional<Member> findByAccount(String account);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
