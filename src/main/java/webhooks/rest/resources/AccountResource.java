package webhooks.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import webhooks.core.models.entities.Account;

public class AccountResource extends ResourceSupport {
	private String name;
	private String password;
	private String apiKey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Account toAccount() {
		Account account = new Account();
		account.setName(name);
		account.setPassword(password);
		account.setApiKey(apiKey);
		return account;
	}

	@JsonIgnore
	public String getApiKey() {
		return apiKey;
	}

	@JsonProperty
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
