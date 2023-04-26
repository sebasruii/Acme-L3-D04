<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
  <acme:list-column code="assistant.tutorialSession.list.label.title" path="title" width="70%"/>
  <acme:list-column code="assistant.tutorialSession.list.label.type" path="type" width="30%"/>
</acme:list>

<acme:button  code="assistant.tutorialSession.list.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>