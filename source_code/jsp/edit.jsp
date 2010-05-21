<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

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
        boolean isCat = true;
        boolean isEdit = true;
        if( session.getAttribute( "object" )  == null ) {
            isEdit = false;
        }
        if( session.getAttribute( "target" ).equals( "pic" ) ) {
            isCat = false;
        }
        String caption = null;
        String name = null;
        String label = null;
        String desc = null;
        int parent = -1;
        int id = -1;
        IGalleryCatalogue cat = null;
        IGalleryPicture pic = null;
        if( isCat ) {
            caption = "CatEdit";
            label = "Path";
            if( isEdit ) {
                cat = ( IGalleryCatalogue )session.getAttribute( "object" );
                name = cat.getName();
                desc = cat.getDescription();
                id = cat.getID();
                parent = cat.getParent();
            }
        } else {
            caption = "PicEdit";
            label = "Cat";
            if( isEdit ) {
                pic = ( IGalleryPicture )session.getAttribute( "object" );
                name = pic.getName();
                desc = pic.getDescription();
                id = pic.getID();
                parent = pic.getCatalogue();
            }
	    }
%>

<%= caption %>

<form method="post" action="<%= session.getAttribute( "absolutePath" ) %>/">
name: <input type="text" name="name" 
value="<%= isEdit?name:"" %>" /><br /><br />

<%= label %>

<input type="text" name="parent" value="<%= parent %>" readonly />
<input type="button" value="!" /><br /><br />

<% 
        if( !isCat && !isEdit ) { 
%>

Path
<input type="file" name="path" /><br /><br />

<% 
        } 
%>

<input type="hidden" name="mode" value="<%= isEdit?"edit":"add" %>" />
<input type="hidden" name="id" value="<%= id %>" />

Desc
<textarea name="desc"><%= isEdit?desc:"" %></textarea><br /><br />
<input type="submit" value="Ok" />
<input type="button" value="Cancel" />
</form>

<%
    }
%>

</body>
</html>