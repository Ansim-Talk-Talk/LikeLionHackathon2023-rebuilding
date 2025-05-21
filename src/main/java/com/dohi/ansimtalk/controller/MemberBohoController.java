package com.dohi.ansimtalk.controller;

import com.dohi.ansimtalk.domain.Member;
import com.dohi.ansimtalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberBohoController {

    private final MemberService memberService;

    @ModelAttribute("MemberBohoForm")
    public MemberBohoForm getMemberBohoForm() {
        return new MemberBohoForm(); // 빈 생성
    }
    @ModelAttribute("MemberCodeForm")
    public MemberCodeForm getMemberCodeForm() {
        return new MemberCodeForm(); // 빈 생성
    }

    @GetMapping("/members/newboho")
    public String createForm(Model model) {
        model.addAttribute("MemberBohoForm", new MemberBohoForm());
        return "login/bohototal";
    }

    @PostMapping("/members/newboho")
    public String create(@Valid MemberBohoForm form, BindingResult result) {

            if (result.hasErrors()) { //에러가 났을경우 다시 돌아가~!
                return  "redirect:/members/bohoerror/"+"125";
            }

            Member member = new Member();
            member.setName(form.getName());
            member.setType(form.getType());
            member.setPhone(form.getPhone());
            Long m_id ;//= memberService.join(member)
        try {m_id= memberService.join(member);}
        catch (Exception e){
            return "redirect:/members/bohoerror/"+"888";
        }
            if (memberService.validateDuplicate(member)) {
                if (memberService.validateDuplicatecode(member)) {

                    return "redirect:/members/" + m_id + "/bohomain";  // 연결되어 이미 존재하는 사용자면 회원의 상세 페이지로 이동
                } else {

                    return "redirect:/members/bohocode/" + m_id;//연결 ㄴ
                }
            } else {
               // codeService.codeset(member);

                return "redirect:/members/bohocode/" + m_id; // 회원 가입 후 홈 페이지로 이동
            }
        }


    @GetMapping("/members/bohocode/{id}")
    public String showMembercodecheck(@PathVariable("id")  Long id, Model model) {

        //CodepeForm form = new CodepeForm();
        //form.setCode(code.getConnectionCode());
        Member member = memberService.findOne(id);

        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";

        }

        System.out.println("test5");
        model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "login/boho5"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }

    @PostMapping("/members/bohocode/{id}")
    public String createcode(@PathVariable Long id, @Valid MemberCodeForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "login/bohototal"; // 폼 검증 에러가 발생한 경우 해당 폼 페이지로 이동
        }

        System.out.println("test4");
        System.out.println(id);
        System.out.println(form.getCode());

        try {
            memberService.codecheck(form.getCode(), id);
        } catch (IllegalArgumentException e) {
            // 예외 처리 로직 -> error할까...
            return "redirect:/members/bohocode/" + id;
        }

        return "redirect:/members/" + id + "/bohomain";
    }





    @GetMapping("/members/{id}/bohomain")
    public String showMemberstartmain(@PathVariable("id")  Long id, Model model) {
        Member member = memberService.findOne(id);

        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";

        }

        model.addAttribute("member", member);
        return "login/boho6"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }
    @GetMapping("/members/{id}/bohomaintitle")
    public String showMembermaintitle(@PathVariable("id")  Long id, Model model) {
        Member member = memberService.findOne(id);
        // 해당 ID에 해당하는 멤버가 없을 경우 처리
        if (member == null) {
            String errortext="회원 정보가 없습니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }else if (!member.getType().equals("보호자")){
            // 해당 ID가 보호자가 아니고 피보호자 계정일경우
            String errortext="보호자계정이 아닙니다.";
            model.addAttribute("errortext", errortext);
            return "login/error";
        }

        model.addAttribute("member", member);
       // model.addAttribute("MemberCodeForm", new MemberCodeForm());
        return "bo-home/bo-home-main"; // 예를 들어, 멤버 상세 정보 페이지로 이동
    }











/////////////////////////////////////////////////////////
////////////////////// 코드 쓰레기통////////////////////////

/*
    @PostMapping("/members/bohocode/{mid}")
    public String createcode(@PathVariable Long mid, @Valid CodepeForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/members/bohocode/" + mid;
        }

        try {
            memberService.codecheck(form.getCode(), mid);
        } catch (SomeException e) { // 예외 처리를 적절한 예외 클래스로 대체하세요
            // 예외 처리 로직
            return "redirect:/members/bohocode/" + mid;
        }

        return "redirect:/members/" + mid + "/bohomain";
    }
    */
    /*

    @PostMapping("/members/bohocode/{id}")
    public String createcode(@PathVariable Long mid, @Valid CodepeForm form, BindingResult result) {

        memberService.codecheck(form.getCode(),mid);
        if (result.hasErrors()) {
            return "/members/bohocode/"+mid;
        }

            return "redirect:/members/" + mid+"/bohomain";

    }
    */
    /*

    /*

    @GetMapping("/member/{m_id}/main")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }
*/
/*
     @GetMapping("/membermain/{id}")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
      //  orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Member member = memberService.findByUsername(username);

        if (member != null && member.getPassword().equals(password)) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", member);
            return "redirect:/membermain/" + member.getId(); // 로그인 후 사용자 페이지로 이동
        } else {
            return "redirect:/login?error"; // 로그인 실패 시 에러 처리
        }
    }

    @GetMapping("/membermain/{id}")
    public String showMemberPage(@PathVariable Long id, HttpSession session, Model model) {
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        if (loggedInUser != null && loggedInUser.getId().equals(id)) {
            Member member = memberService.findMemberById(id);

            if (member == null) {
                return "error"; // 회원이 존재하지 않을 경우 에러 처리
            }

            model.addAttribute("member", member);
            return "members/memberPage"; // 회원 상세 정보 페이지로 이동
        } else {
            return "redirect:/access-denied"; // 접근 권한이 없는 경우 리다이렉트
        }
    }

    @RequestMapping("/login")
    public String login(HttpSession session) {
        // 로그인이 성공했을 경우 사용자 정보를 세션에 저장
        User user = new User("username", "password");
        session.setAttribute("loggedInUser", user);

        return "redirect:/home";
    }

*/
}
