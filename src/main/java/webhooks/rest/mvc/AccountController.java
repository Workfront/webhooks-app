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
package webhooks.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webhooks.config.*;
import webhooks.core.models.entities.Account;
import webhooks.core.services.IAccountService;
import webhooks.core.services.exceptions.AccountExistsException;
import webhooks.core.services.util.AccountList;
import webhooks.rest.exceptions.ConflictException;
import webhooks.rest.resources.AccountListResource;
import webhooks.rest.resources.AccountResource;
import webhooks.rest.resources.asm.AccountListResourceAsm;
import webhooks.rest.resources.asm.AccountResourceAsm;

import javax.servlet.http.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/rest/accounts")
public class AccountController {
    private IAccountService accountService;

	@Autowired
	private HttpServletRequest request;

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

	public Boolean authenticate() {
		if (!ConfigBean.getInstance().isAuthenticationEnabled()) {
			return true;
		}

		String userName = request.getHeader("username");
		String APIKey = request.getHeader("apiKey");
		if (userName == null || APIKey == null || userName.trim().length() == 0 || APIKey.trim().length() == 0) {
			return false;
		}

		boolean validate = false;
		Account account =  accountService.findByAccountName(userName);
		if (account != null) {
			// Check if the register key is the same
			// Use configuration data for valid key value
			if (ConfigBean.getInstance().isValidKey(APIKey)) {
			//if (account.getApiKey().equals(APIKey)) {
				validate = true;
			}
		}

		return validate;
	}


	/**
	 * Find an account based on the account name, if name is not provided, all available accounts will be returned
	 * @param name the name of the account.  Not required.
	 * @return account/accounts based on the name
	 */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AccountListResource> findAccounts(@RequestParam(value="name", required = false) String name) {
        AccountList list;
        if(name == null) {
            list = accountService.getAllAccounts();
        } else {
            Account account = accountService.findByAccountName(name);
            if(account == null) {
                list = new AccountList(new ArrayList<Account>());
            } else {
                list = new AccountList(Arrays.asList(account));
            }
        }
        AccountListResource res = new AccountListResourceAsm().toResource(list);
        return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
    }

	/**
	 * Create an account in the system
	 * @param targetAccount json data for the creating account -- {Id:"00001", name:"abc@test.com", password:"test", apiKey:"111111"}
	 * @return metadata for the created account
	 */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountResource> createAccount(
            @RequestBody AccountResource targetAccount
    ) {
        try {
            Account createdAccount = accountService.createAccount(targetAccount.toAccount());
            AccountResource res = new AccountResourceAsm().toResource(createdAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
        } catch(AccountExistsException exception) {
            throw new ConflictException(exception);
        }
    }

	/**
	 * Get an account base on the id
	 * @param accountId
	 * @return
	 */
    @RequestMapping( value="/{accountId}",
                method = RequestMethod.GET)
    public ResponseEntity<AccountResource> getAccount(
            @PathVariable Long accountId
    ) {
        Account account = accountService.findAccountById(accountId);
        if(account != null)
        {
            AccountResource res = new AccountResourceAsm().toResource(account);
            return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
        }
    }
}
