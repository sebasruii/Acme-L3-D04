<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="lecture.title" width="70%"/>
	<acme:list-column code="lecturer.lecture.list.label.estimatedLearningTime" path="lecture.estimatedLearningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureType" path="lecture.lectureType" width="10%"/>
</acme:list>


<jstl:if test="${_command == 'list'}">
	<acme:button code="lecturer.lectureCourse.form.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>
</jstl:if>	
	