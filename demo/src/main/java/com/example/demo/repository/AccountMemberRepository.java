package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import org.hibernate.annotations.TenantId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional
@Repository
public class AccountMemberRepository implements MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }
    @Override
    public Optional<Member> findByAccount(String account)
    {
        return store.values().stream().
                filter(member -> member.getAccount().equals(account)).findAny();
    }


    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().
                filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore()
    {
        store.clear();
    }
}
