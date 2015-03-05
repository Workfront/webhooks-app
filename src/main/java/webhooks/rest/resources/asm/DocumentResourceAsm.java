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
package webhooks.rest.resources.asm;

import org.springframework.hateoas.mvc.*;
import webhooks.core.models.entities.*;
import webhooks.rest.mvc.*;
import webhooks.rest.resources.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 */
public class DocumentResourceAsm extends ResourceAssemblerSupport<Document, DocumentResource> {
	public DocumentResourceAsm() {
		super(DocumentController.class, DocumentResource.class);
	}

	@Override
	public DocumentResource toResource(Document document) {
		DocumentResource res = new DocumentResource();
		res.setTitle(document.getName());
		res.setDocId(document.getId());
		res.setMimeType(document.getMimeType());

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(document.getLastModified());
		res.setModifiedDate(cal);
		res.setSize(document.getSize());
		res.setKind(document.getKind());

		if (Document.TYPE_FILE.equalsIgnoreCase(document.getKind())){
			res.setDownloadLink(linkTo(methodOn(DocumentController.class).getDocument(document.getId())).toUri().toString());
			res.setViewLink(linkTo(methodOn(DocumentController.class).viewDocument(document.getId())).toUri().toString());
		}

        res.add(linkTo(methodOn(DocumentController.class).getMetadata(document.getId())).withSelfRel());

		return res;
	}
}
