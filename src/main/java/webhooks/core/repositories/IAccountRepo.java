package webhooks.core.repositories;

import webhooks.core.models.entities.Account;

import java.util.List;

public interface IAccountRepo {
    public List<Account> getAllAccounts();
    public Account findAccountById(Long id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data);
}
