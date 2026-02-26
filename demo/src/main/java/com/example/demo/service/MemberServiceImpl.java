package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void transfer(String fromName,String toName,String toAccount,int amount)
    {
        Member sender = memberRepository.findByName(fromName).
                orElseThrow(()->new IllegalStateException("보내는 사람이 없습니다"));

        Member recipient = findOneByAccount(toAccount).
                orElseThrow(()->new IllegalStateException("존재하지않는 계좌번호입니다"));

        if(!recipient.getName().equals(toName))
        {
            throw new IllegalStateException("계좌번호와 예금주의 이름이 일치하지 않습니다");
        }

//        if(sender.getBalance()<amount)
//        {
//            throw new IllegalStateException("금액이 부족합니다");
//        }
        sender.withdraw(amount);
        recipient.deposit(amount);
    }


    @Override
    public Long join(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent( m ->{
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
        memberRepository.save(member);
        return member.getId();
    }

    public  Optional<Member> findOneByAccount(String account)
    {
        return memberRepository.findByAccount(account);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
