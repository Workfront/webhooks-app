package webhooks.core.services;

import webhooks.core.models.entities.Account;
import webhooks.core.services.util.AccountList;

public interface IAccountService {
    public Account findAccountById(Long id);
    public Account createAccount(Account data);
    public AccountList getAllAccounts();
    public Account findByAccountName(String name);
}
