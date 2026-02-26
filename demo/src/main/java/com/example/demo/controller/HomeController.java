package com.example.demo.controller;


import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final MemberService memberService;
    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }



    @GetMapping("/")
    public String home()
    {
        return "index";
    }
    @GetMapping("/members")
    public String list(Model model)
    {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
    @GetMapping("/join")
    public String createForm()
    {
        return "members/createMemberForm";
    }

    @PostMapping("/join")
    public String join(@RequestParam String name,
                       Model model)
    {
        Member member = new Member(name);

        try{
            memberService.join(member);
            return "redirect:/";
        }catch (IllegalStateException e)
        {
            model.addAttribute("errorMessage",e.getMessage());
            return "members/createMemberForm";
        }
    }

    @GetMapping("/transfer")
    public String transferForm()
    {
        return "/transfer/transferForm";
    }
    @PostMapping("/transfer")
    public String transfer(@RequestParam("fromName") String fromName,
                           @RequestParam("toName") String toName,
                           @RequestParam("toAccount") String toAccount,
                           @RequestParam("amount") int amount,
                           Model model)
    {
        try{
            memberService.transfer(fromName,toName,toAccount,amount);
            return "redirect:/members";
        }catch (IllegalStateException e)
        {
            model.addAttribute("errorMessage",e.getMessage());
            return "transfer/transferForm";
        }

    }
}
