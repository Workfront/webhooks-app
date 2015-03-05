/**
 * Copyright 2015 Workfront, Inc.
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
package webhooks.config;

import org.w3c.dom.*;

import javax.servlet.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

/**
 *  load config file "webhooks-config.xml here
 */
public class WebhooksContextListener implements ServletContextListener {

	public static final String apiKey_Name = "api-key";
	public static final String path_Name = "path";
	public static final String auth_Name = "apply-authentication";

	private final String props = "/WEB-INF/webhooks-config.xml";

	@Override
	public void contextInitialized(ServletContextEvent sce) {


		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			ServletContext context = sce.getServletContext();
			Document doc = builder.parse(context.getResourceAsStream(props));
			getConfiguration (doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getConfiguration (Node n) {
		NodeList children = n.getChildNodes();
		if (children != null){
			for (int i = 0; i < children.getLength(); i++) {
				Node childNode = children.item(i);

				if (childNode.getNodeType() == Node.ELEMENT_NODE); {
					if (childNode.getNodeName().equals(apiKey_Name)) {
						ConfigBean.getInstance().addKey(childNode.getTextContent());
					} else if (childNode.getNodeName().equals(path_Name)) {
						ConfigBean.getInstance().addPath(childNode.getTextContent());
					} else if (childNode.getNodeName().equals(auth_Name)) {
						ConfigBean.getInstance().setAuthenticationFlag(Boolean.valueOf(childNode.getTextContent()));
					}
				}

				getConfiguration(childNode);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
