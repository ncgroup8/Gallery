<%@ page isErrorPage="true" %>

<html>
<head>
<title>Error!</title>
</head>
Received the exception:
<p>

<%= exception.getMessage() %>

<p>

<%= exception.printStackTrace()  %>

</body>
</html>
