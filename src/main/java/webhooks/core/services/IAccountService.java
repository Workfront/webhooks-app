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
package webhooks.core.services;

import webhooks.core.models.entities.Account;
import webhooks.core.services.util.AccountList;

public interface IAccountService {
    public Account findAccountById(Long id);
    public Account createAccount(Account data);
    public AccountList getAllAccounts();
    public Account findByAccountName(String name);
}
