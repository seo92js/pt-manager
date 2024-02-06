package com.seojs.ptmanager.web;

import com.seojs.ptmanager.domain.member.Member;
import com.seojs.ptmanager.domain.trainer.Trainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember", required = false)Member member,
                       @SessionAttribute(name = "loginTrainer", required = false)Trainer trainer,
                       Model model) {

        // 로그인이 안되어 있으면
        if (member == null && trainer == null) {
            return "로그인 안된 화면";
        }

        if (member != null && trainer == null) {
            model.addAttribute("member", member);
        } else if (trainer != null && member == null) {
            model.addAttribute("trainer", trainer);
        }

        return "로그인 된 화면";
    }
}
