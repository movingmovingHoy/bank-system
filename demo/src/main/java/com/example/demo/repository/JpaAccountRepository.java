package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@Transactional
public class JpaAccountRepository implements MemberRepository{

    private final EntityManager em;

    public JpaAccountRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findByAccount(String account) {
//        List<Member> result = em.createQuery("select m from Member m where m.account = :account", Member.class);
        List<Member> result = em.createQuery("select m from Member m where m.account = :account", Member.class)
                .setParameter("account", account)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result=em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
