<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Card print request info</title>
</head>
<body>
<h2>Card print request info</h2>

<br>

branchCode is : ${branchCode}
ipAddress is : ${ipAddress}
personnelCode is : ${personnelCode}
cardPAN is : ${cardPAN }
issueDate is : ${issuedDate}


<h3><a href="/list-requests">back to List Of request</a></h3>
<h3><a href="/logout">close</a></h3>


</body>
</html>