/**
 * Copyright 2014 AtTask, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
