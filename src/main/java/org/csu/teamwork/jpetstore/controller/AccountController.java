package org.csu.teamwork.jpetstore.controller;

import org.csu.teamwork.jpetstore.domain.account.Account;
import org.csu.teamwork.jpetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/6/11 9:14
 */
@Controller
@SessionAttributes({"account"})
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/viewLoginForm")
    public String loginForm() {
        return "account/login";
    }

    @PostMapping("/login")
    public String login(@Valid Account account, HttpSession session, Model model, BindingResult bindingResult) throws Exception {
//        查看错误信息
//        if (bindingResult.hasErrors()) {
//            return bindingResult.getFieldError().getDefaultMessage();
//        }
        account = accountService.getAccount(account);
        if (account != null) {
            session.setAttribute("account", account);
            model.addAttribute("account", account);
            return "catalog/main";
        } else {
            model.addAttribute("account", null);
            return "account/login";
        }
    }

    @GetMapping("/signOut")
    public String signOut(Model model, HttpSession session) {
        session.setAttribute("account", null);
        model.addAttribute("account", null);
        return "catalog/main";
    }

    @PostMapping("/register")
    public String register(@Valid Account account, Model model, HttpSession session) throws Exception {
        if (!(account.getUsername().equals("") || account.getPassword().equals(""))) {
            if (account.getPassword().equals(account.getRepeatedPassword())) {
                Account temp = accountService.getAccount(account.getUsername());
                // 若用户名可创建则注册用户
                if (temp == null) {
                    accountService.insertAccount(account);
                    model.addAttribute("account", account);
                    return "catalog/main";
                }
            }
        } else {
            List<String> languages = new ArrayList<>();
            languages.add("English");
            languages.add("中文");
            session.setAttribute("languages", languages);
            List<String> categories = new ArrayList<>();
            categories.add("FISH");
            categories.add("DOGS");
            categories.add("REPTILES");
            categories.add("CATS");
            categories.add("BIRDS");
            session.setAttribute("categories", categories);
        }
        return "account/register";
    }

    @GetMapping("/editAccount")
    public String editAccount(HttpSession session) {
        List<String> languages = new ArrayList<String>();
        languages.add("english");
        languages.add("japanese");
        session.setAttribute("languages", languages);

        List<String> categories = new ArrayList<>();
        categories.add("FISH");
        categories.add("DOGS");
        categories.add("REPTILES");
        categories.add("CATS");
        categories.add("BIRDS");
        session.setAttribute("categories", categories);

        return "account/editAccount";
    }


    //@Valid 结构体要用
    @PostMapping("/confirmEdit")
    public String confirmEdit(@Valid Account account, Model model, HttpSession session) throws Exception {
        if (!account.getPassword().equals("") && !account.getRepeatedPassword().equals("") && account.getPassword().equals(account.getRepeatedPassword())) {
            accountService.updateAccount(account);
            session.setAttribute("account", account);
            model.addAttribute("account", account);
            return "catalog/main";
        } else {
            return "account/editAccount";
        }
    }

    @GetMapping("/usernameIsExist")
    public void usernameIsExist(@RequestParam("username") String username, HttpServletResponse response) throws Exception {
        if (username.trim().equals("")) {
            return;
        }
        Account account = accountService.getAccount(username);

        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        out.println("<?xml version='1.0' encoding='" + "utf-8" + "' ?>");

        if (account != null) {
            out.println("<msg>Exist</msg>");
        } else {
            out.println("<msg>NotExist</msg>");
        }
        out.flush();
        out.close();
    }

}
