<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page errorPage="/errorpage.jsp" %>

<%
    if( session.getAttribute( "target" ) == null ) {
%>

<jsp:forward page='<%= ( session.getAttribute( "absolutePath" ) + "/" ) %>' />

<%
    }
%>

<html>
<head>
</head>
<body>

<% 
    if( session.getAttribute( "error" ) != null ) { 
        out.println( session.getAttribute( "error" ) );
        session.setAttribute( "error", null );
%>
<br /><br />
<a href="<%= session.getAttribute( "absolutePath" ) %>">Na glagne</a>

<%
    } else {
%>

<table border="1" width="80%" align="center">
<tr>
<td colspan="2">
<h1>HEADER</h1>
</td>
</tr>

<tr>
<td width="20%">

<b>menu</b><br /><br />

<a href="<%= session.getAttribute( "absolutePath" ) + "/?act=addcat" %>">AddCat</a><br />

<a href="<%= session.getAttribute( "absolutePath" ) + "/?act=addpic" %>">AddPic</a><br />

</td>
<td>

&nbsp;<%@include file="view.jsp" %>

</td>
</tr>

<tr>
<td colspan="2">
<h1>FOOTER</h1>
</td>
</tr>
</table>

<%
    }
%>

</body>
</html>