<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>show requests</title>
</head>
<body>
<div align="center">
    <h2>Requests</h2>

    <h3><a href="/new">New Request</a></h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>branchCode</th>
            <th>PersonnelCode</th>
            <th>CardPAN</th>
            <th>issuedDate</th>
        </tr>
        <c:forEach items="${requests}" var="request">
            <tr>
                <td>${request.id.branchCode}</td>
                <td>${request.personnelCode}</td>
                <td>${request.cardPAN}</td>
                <td>${request.issuedDate}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>