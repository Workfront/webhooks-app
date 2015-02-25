package webhooks.rest.resources.asm;

import org.springframework.hateoas.mvc.*;
import webhooks.core.services.util.*;
import webhooks.rest.mvc.*;
import webhooks.rest.resources.*;

import java.util.*;

/**
 */
public class DocumentListResourceAsm extends ResourceAssemblerSupport<DocumentList, DocumentListResource> {

	public DocumentListResourceAsm() {
		super(DocumentController.class, DocumentListResource.class);
	}

	@Override
	public DocumentListResource toResource(DocumentList documentList) {
		List<DocumentResource> resList = new DocumentResourceAsm().toResources(documentList.getDocuments());
		DocumentListResource finalRes = new DocumentListResource();
		finalRes.setDocuments(resList);

		return finalRes;
	}
}
