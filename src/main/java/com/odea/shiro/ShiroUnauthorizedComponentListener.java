/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.odea.shiro;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

public class ShiroUnauthorizedComponentListener implements
	IUnauthorizedComponentInstantiationListener
{
	private final Class<? extends Page> loginPage;
	private final Class<? extends Page> unauthorizedPage;
	private AnnotationsShiroAuthorizationStrategy annotationStrategy = null;

	public ShiroUnauthorizedComponentListener(final Class<? extends Page> loginPage,
		final Class<? extends Page> unauthorizedPage, final AnnotationsShiroAuthorizationStrategy s)
	{
		this.loginPage = loginPage;
		this.unauthorizedPage = unauthorizedPage;
	}

	@Override
	public void onUnauthorizedInstantiation(Component component) {
		System.out.println("-- Pase por onUnauthorizedInstantiation() - Page: " + component.getPage().getId() + " - Component ID: " + component.getId() );
	}

}
