<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialSession.form.label.title"
		path="title" />
	<acme:input-select code="assistant.tutorialSession.form.label.type"
		path="type" choices="${types}" />
	<acme:input-textarea
		code="assistant.tutorialSession.form.label.summary"
		path="summary" />
	<acme:input-moment
		code="assistant.tutorialSession.form.label.startDate"
		path="startDate" />
	<acme:input-moment
		code="assistant.tutorialSession.form.label.finishDate" path="finishDate" />
	<acme:input-url
		code="assistant.tutorialSession.form.label.link"
		path="link" />
		<acme:input-textarea
		code="assistant.tutorialSession.form.label.draftMode"
		path="draftMode" readonly="true"/>

	<jstl:choose>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|publish') && draftMode == true}">
			<acme:submit code="assistant.tutorialSession.form.button.update"
				action="/assistant/tutorial-session/update" />
			<acme:submit code="assistant.tutorialSession.form.button.publish"
				action="/assistant/tutorial-session/publish" />
			<acme:submit code="assistant.tutorialSession.form.button.delete"
				action="/assistant/tutorial-session/delete" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorialSession.form.button.create"
				action="/assistant/tutorial-session/create?masterId=${masterId}" />
		</jstl:when>
	</jstl:choose>
</acme:form>
