package com.example.demo.transfer;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("송금성공: A의 잔액은 감소하고,B의 잔액은 늘어남!")
    public void transfer_success()
    {
        //given
        Member sender = new Member("sender");

        Member recipient = new Member("recipient");

        memberService.join(sender);
        memberService.join(recipient);
        //when
        memberService.transfer("sender","recipient", recipient.getAccount(), 10000);
        //then
        Assertions.assertThat(sender.getBalance()).isEqualTo(90000);
        Assertions.assertThat(recipient.getBalance()).isEqualTo(110000);

    }

    @Test
    @DisplayName("송금실패: A잔액 부족")
    public void transfer_fail_NotEnoughBalance()
    {
        //given
        Member sender = new Member("sender");

        Member recipient = new Member("recipient");

        memberService.join(sender);
        memberService.join(recipient);
        //when

        IllegalStateException e  = org.junit.jupiter.api.Assertions.
                assertThrows(IllegalStateException.class,()->{
                    memberService.transfer("sender","recipient", recipient.getAccount(), 1000000);
                });

        //then
        Assertions.assertThat(e.getMessage()).isEqualTo("금액이 부족합니다");
        Assertions.assertThat(sender.getBalance()).isEqualTo(100000);
//        실패해서 변경이 안됐어야함!

    }
    @Test
    @DisplayName("송금실패:잘못된 계좌 번호")
    public void transfer_fail_WrongAccount()
    {
        //given
        Member sender = new Member("sender");

        Member recipient = new Member("recipient");

        memberService.join(sender);
        memberService.join(recipient);
        //when

        IllegalStateException e  = org.junit.jupiter.api.Assertions.
                assertThrows(IllegalStateException.class,()->{
                    memberService.transfer("sender",
                            "recipient", "--22-22-2", 1000000);
                });

        //then
        Assertions.assertThat(e.getMessage()).isEqualTo("존재하지않는 계좌번호입니다");
        Assertions.assertThat(sender.getBalance()).isEqualTo(100000);
//        실패해서 변경이 안됐어야함!

    }

    @Test
    @DisplayName("송금실패:계좌 번호와 이름이 일치하지 않을때")
    public void transfer_fail_not_valid_AccountHolder()
    {
        //given
        Member sender = new Member("sender");

        Member recipient = new Member("recipient");

        memberService.join(sender);
        memberService.join(recipient);
        //when

        IllegalStateException e  = org.junit.jupiter.api.Assertions.
                assertThrows(IllegalStateException.class,()->{
                    memberService.transfer("sender",
                            "Do you know HongDoctor??", recipient.getAccount(), 1000000);
                });

        //then
        Assertions.assertThat(e.getMessage()).isEqualTo("계좌번호와 예금주의 이름이 일치하지 않습니다");
        Assertions.assertThat(sender.getBalance()).isEqualTo(100000);
//        실패해서 변경이 안됐어야함!

    }


}
