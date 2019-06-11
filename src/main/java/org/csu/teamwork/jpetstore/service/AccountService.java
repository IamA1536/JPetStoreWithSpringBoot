package org.csu.teamwork.jpetstore.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csu.teamwork.jpetstore.domain.account.Account;
import org.csu.teamwork.jpetstore.persistence.Mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author A
 * Created by IamA#1536 on 2018/12/9 22:56
 */
@Service
@SessionAttributes({"account"})
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;


    public AccountService() {
    }

    public Account getAccount(String username) throws Exception {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(Account account) throws Exception {
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public Account getAccount(String username, String password) throws Exception {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);

        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) throws Exception {
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account) throws Exception {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);
        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }
}
