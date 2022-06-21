<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New Request</title>
</head>
<style>
    .error {
        color: #ff0000;
    }

</style>
<body>
<div align="center">
    <h2>New Request</h2>
    <form:form action="save" method="post" modelAttribute="request">
        <table border="0" cellpadding="5">
            <tr>
                <td>personnelCode: </td>
                <td><form:input path="personnelCode" /></td>
                <td><form:errors path = "personnelCode" cssClass = "error" /></td>
            </tr>
            <tr>
                <td>cardPAN: </td>
                <td><form:input path="cardPAN" /></td>
            </tr>
            <tr>
                <td>branchCode: </td>
                <td><form:input path="id.branchCode" /></td>
                <td><form:errors path = "id.branchCode" cssClass = "error" /></td>
            </tr>
            <tr>
                <td>ipAddress: </td>
                <td><form:input path="id.ipAddress" /></td>
                <td><form:errors path = "id.ipAddress" cssClass = "error" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>