<%--
- welcome.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<div class="jumbotron">
	<h1><acme:message code="master.welcome.title"/></h1>
	<acme:message code="master.welcome.text"/>
</div>

<jstl:if test="${banner != null }">
	
	<div style="width:90%; margin: auto; display: flex; flex-wrap: wrap; justify-content: center; align-items: center; gap: 10px;">

		<a href="${banner.link}" style="flex: 0 1 500px; position: relative;">
			<img src="${banner.imageLink}" alt="${banner.slogan}" style="width:100%"/>
			<div style="position: absolute; bottom: 0; background-color: grey; color: white; padding: 2px; font-size: 12px;">
					<acme:message code="master.welcome.advertisement"/>
			</div>
		</a>

		<div style="flex: 0 1 470px; font-weight: 600;">
			<acme:print value="${banner.slogan}" />
		</div>

	</div>
</jstl:if>
