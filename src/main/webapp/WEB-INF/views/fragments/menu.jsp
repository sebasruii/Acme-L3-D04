<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="79466642D: Benitez Ruis Diaz, Francisco Sebastian" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="15455746E: Restoy Barrero, Joaquin" action="https://accounts.spotify.com/es-ES/status"/>
			<acme:menu-suboption code="54172162D:  Perez Romero, Lucia" action="https://stackoverflow.com/"/>
			<acme:menu-suboption code="49398962E: Marquez Sierra, Maria" action="https://www.instagram.com/gravitydusty/?hl=es"/>
			<acme:menu-suboption code="49852235B: Jimenez Del Villar, Juan Antonio" action="https://twitter.com"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.all-courses" action="/any/course/list"/>			
			
		</acme:menu-option>	
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-bulletin" action="/administrator/bulletin/create"/>
			<acme:menu-separator/>

			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
 			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-offer" action="/administrator/offer/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-offer" action="/administrator/offer/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.configuration" action="/administrator/configuration/show"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
					<acme:menu-suboption code="master.menu.anonymous.all-courses" action="/any/course/list"/>
			<acme:menu-separator/>	
			<acme:menu-suboption code="authenticated.offer.list" action="/authenticated/offer/list"/>
			<acme:menu-separator/>	
			<acme:menu-suboption code="master.menu.authenticated.note.list" action="/authenticated/note/list"/>
			<acme:menu-suboption code="master.menu.authenticated.note.create" action="/authenticated/note/create"/>
			<acme:menu-separator/>	
			<acme:menu-suboption code="master.menu.authenticated.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.auditor.create" action="/authenticated/auditor/create"/>
			<acme:menu-suboption code="master.menu.authenticated.auditor.update" action="authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.audit.list" action="/authenticated/audit/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list" action="/auditor/audit/list"/>
			<acme:menu-suboption code="master.menu.auditor.create" action="/auditor/audit/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
          <acme:menu-suboption code="master.menu.lecturer.course.list" action="/lecturer/course/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.lecture.list" action="/lecturer/lecture/list-all"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.dashboard" action="/lecturer/lecturer-dashboard/show"/>
		</acme:menu-option>
    
    <acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.list" action="/assistant/tutorial/list"/>
			<acme:menu-suboption code="master.menu.assistant.create" action="/assistant/tutorial/create"/>
		</acme:menu-option>

    <acme:menu-option code="master.menu.company" access="hasRole('Company')">			
			<acme:menu-suboption code="master.menu.company.my-practica" action="/company/practicum/list"/>			
		</acme:menu-option>

		
		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
 			<acme:menu-suboption code="master.menu.student.studentEnrolmentList" action="/student/enrolment/list"/>		
 		</acme:menu-option>


		
		<acme:menu-option code="master.menu.anonymous.peep" action="/any/peep/list" access="isAnonymous()"/>

		<acme:menu-option code="authenticated.practicum.form.button.list" action="/authenticated/practicum/list" access="hasRole('Company')"/>


	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.update-assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
			</acme:menu-option>
		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
		
	</acme:menu-right>
</acme:menu-bar>

