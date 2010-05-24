<%@ page import="java.util.*" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<%! final int ELEMENTS_IN_ROW = 5; %>
<%! final int SHOWPIC_COLS = 2; %>
<%! final int SHOWPIC_ROWS = 2; %>

<% 
    boolean isShowCat = true; 
    if( session.getAttribute( "target" ).equals( "pic" ) ) {
        isShowCat = false;
    } 
%>

<table>
<tr>
<td colspan="<%= isShowCat?ELEMENTS_IN_ROW:SHOWPIC_COLS %>">

<%
    List path = ( List )session.getAttribute( "path" );
    Iterator it = null;
    IGalleryCatalogue cat = null;
    IGalleryPicture pic = null;
    if( path == null ) {
        out.println( "<b>Wrong path!</b>" );
    } else {
        it = path.iterator();
        StringBuffer sb1 = new StringBuffer( "" );
        String str2 = "";
        String str = null;
        while( it.hasNext() ) {
            cat = ( IGalleryCatalogue )it.next();
            sb1.setLength( 0 );
            sb1.append("<a href=\"").append( session.
                getAttribute( "absolutePath" ) ).append("/?act=showcat&id=").
                append( cat.getID() ).append( "\">" ).append( cat.getName() ).
                append( "</a> >> " );
            str = sb1.toString();
            str2 = str + str2;
        }
        out.println( str2 );
        if( !isShowCat ) {
            pic = ( IGalleryPicture )session.getAttribute( "pic" );
            out.println( pic.getName() );
        }
    }
%>

</td>
</tr>

<%
    if( isShowCat ) {
%>

<% 
        int c = 0;
        List cats = ( List )session.getAttribute( "cats" );
        List pics = ( List )session.getAttribute( "pics" );
        if( cats == null && pics == null ) {
%>

<tr>
<td colspan="<%= ELEMENTS_IN_ROW %>">
Directory is empty!
</td>
</tr>

<%
        } else {
            if( cats != null ) {
                it = cats.iterator();
                while ( it.hasNext() ) { 
                    cat = ( IGalleryCatalogue )it.next();
                    if( (c % ELEMENTS_IN_ROW) == 0) {
%>

<tr>

<%
                    }
%>

<td>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=showcat&id=<%= cat.getID() %>">
<img src="img/cat.png" /><br />

<%= cat.getName() %>
</a>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=editcat&id=<%= cat.getID() %>">Edit</a>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=delcat&id=<%= cat.getID() %>">Del</a>

</td>

<% 
                    if( (c % ELEMENTS_IN_ROW) == (ELEMENTS_IN_ROW - 1) ) {
%>

</tr>

<%
                    }
                    c++;
                }
            }
            if( pics != null ) {
                it = pics.iterator();
                while ( it.hasNext() ) { 
                    pic = ( IGalleryPicture )it.next();
                    if( (c % ELEMENTS_IN_ROW) == 0) {
%>

<tr>

<%
                    }
%>

<td>

<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=showpic&id=<%= pic.getID() %>">
<img src="img/pic.png" /><br />

<%= pic.getName() %>
</a>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=editpic&id=<%= pic.getID() %>">Edit</a>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=delpic&id=<%= pic.getID() %>">Del</a>

</td>

<% 
                    if( (c % ELEMENTS_IN_ROW) == (ELEMENTS_IN_ROW - 1) ) {
%>

</tr>

<%
                    }
                    c++;
                }
            }
            if( (c % ELEMENTS_IN_ROW) != 0)
            {
                while ( (c % ELEMENTS_IN_ROW) !=0 )
                {
%>

<td></td>

<%
                    c++;
                }
%>

</tr>

<%
            }
        }
    } else {
%>

<tr>
<td rowspan="<%= SHOWPIC_ROWS %>">
<img src="<%= pic.getURL() %>" />
</td>
<td><b>Name:</b> 
<%= pic.getName() %>
</td>
</tr>
<tr>
<td><b>Description:</b> 
<%= pic.getDescription() %>
</td>
</tr>

<%
    }
%>

</table>