package webhooks.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webhooks.core.models.entities.Account;
import webhooks.core.repositories.IAccountRepo;
import webhooks.core.services.IAccountService;
import webhooks.core.services.exceptions.AccountExistsException;
import webhooks.core.services.util.AccountList;

/**
 * Account service implementation
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepo accountRepo;

    @Override
    public Account findAccountById(Long id) {
        return accountRepo.findAccountById(id);
    }

    @Override
    public Account createAccount(Account data) {
        Account account = accountRepo.findAccountByName(data.getName());
        if(account != null)
        {
            throw new AccountExistsException();
        }
        return accountRepo.createAccount(data);
    }

    @Override
    public AccountList getAllAccounts() {
        return new AccountList(accountRepo.getAllAccounts());
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }
}
