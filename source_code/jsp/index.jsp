<%@ page contentType="text/html; charset=UTF-8" %>

<%
    if( session.getAttribute( "target" ) == null ) {
%>

<jsp:forward page='<%= ( session.getAttribute( "absolutePath" ) + "/" + 
    ( ( request.getQueryString() != null && !request.getQueryString().equals( "" ) 
    )?( "?" + request.getQueryString() ):"" ) ) %>' />

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

<%
    String targId = "";
    if( session.getAttribute( "target" ).equals( "pic" ) ) {
        targId = "&id=" + String.valueOf( ( ( IGalleryCatalogue )( 
            ( List )session.getAttribute( "path" ) ).get( 0 ) ).getID() );
    } else {
        if( request.getParameter( "id" ) != null ) {
            targId = "&id=" + request.getParameter( "id" );
        }
    }
%>

<b>menu</b><br /><br />

<a href="<%= session.getAttribute( "absolutePath" ) + 
    "/?act=addcat" + targId %>">AddCat</a><br />

<a href="<%= session.getAttribute( "absolutePath" ) + "/?act=addpic" + targId %>">AddPic</a><br />

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
        session.setAttribute( "target", null );
    }
%>

</body>
</html>