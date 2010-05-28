<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<%! final int ELEMENTS_IN_ROW = 5; %>
<%! final int SHOWPIC_COLS = 2; %>
<%! final int SHOWPIC_ROWS = 2; %>

<%
    boolean isSelect = false;
    if( session.getAttribute( "cut" ) != null ) {
        if( session.getAttribute( "cut" ).equals( "y" ) ) {
            isSelect = true;
        }
    }
    if( ( session.getAttribute( "target" ) == null ) && !isSelect ) {
%>

<jsp:forward page='<%= session.getAttribute( "absolutePath" ) + "/?act=showcat&id=1&cut=y" %>' />

<%
    }
%>


<% 
    if( isSelect ) {
%>

<html>
<head>
    <script type="text/javascript">
        function send() {
			var rad = window.document.forms['selform'].selcat;
			var ind = -1;
			for( i = 0; i < rad.length; i++ ) {
				if( rad[i].checked ) {
					ind = i;
					break;
				}
			}
			if( ind == -1) {
				return false;
			}
            returnVal = rad[ind].value ;
            window.top.hidePopWin(true);
		}
        function addhere() {
			returnVal = '<%= ( ( IGalleryCatalogue )( ( List )session.
                getAttribute( "path" ) ).get( 0 ) ).getID() %>' + "|" + '<%= 
                ( ( IGalleryCatalogue )( ( List )session.getAttribute( "path" ) ).
                get( 0 ) ).getName() %>';
            window.top.hidePopWin(true);
		}
	</script>
</head>
<body>

<%
    } 
%>

<% 
    boolean isShowCat = true; 
    if( session.getAttribute( "target" ).equals( "pic" ) && !isSelect ) {
        isShowCat = false;
    }
%>

<table>
<tr>
<td colspan="<%= isSelect?1:(isShowCat?ELEMENTS_IN_ROW:SHOWPIC_COLS) %>">

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
                append( cat.getID() ).append( isSelect?"&cut=y":"" ).
                append( "\">" ).append( cat.getName() ).append( "</a> >> " );
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
        if( ( cats == null ) && ( ( pics == null) || isSelect ) ) {
%>

<tr>
<td colspan="<%= ELEMENTS_IN_ROW %>">
Directory is empty!
<br /><br />

<%
            if( isSelect ) {
%>

<input type="button" value="Add here" onclick="addhere();"><br />

<%
            }    
%>

</td>
</tr>

<%
        } else {
            if( isSelect ) {
%>

<tr>
<td>
<form name="selform" action="#">
<input type="radio" name="selcat" 
value="<%= ( ( IGalleryCatalogue )path.get( 0 ) ).getID() %>|<%= 
    ( ( IGalleryCatalogue )path.get( 0 ) ).getName() %>" />
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=showcat&id=<%= 
    ( ( IGalleryCatalogue )path.get( 0 ) ).getID() %>&cut=y">
<img src="img/smallcat.png" />
<%= ( ( IGalleryCatalogue )path.get( 0 ) ).getName() %></a><br />
<hr />


<%
            }
            if( cats != null ) {
                it = cats.iterator();
                while ( it.hasNext() ) { 
                    cat = ( IGalleryCatalogue )it.next();
                    if( !isSelect ) {
                        if( (c % ELEMENTS_IN_ROW) == 0 ) {
%>

<tr>

<%
                        }
%>

<td>

<% 
                    } else {
%>

<input type="radio" name="selcat" value="<%= cat.getID() %>|<%= cat.getName() %>" />

<%
                    }
%>

<a href="<%= session.getAttribute( "absolutePath" ) 
    %>/?act=showcat&id=<%= cat.getID() 
    %><%= isSelect?"&cut=y":"" %>">
<img src="img/<%= isSelect?"small":"" %>cat.png" />

<%
                    if( !isSelect ) {
%>

<br />

<%
                    }    
%>    

<%= cat.getName() %>
</a><br />

<%
                    if( !isSelect ) {
%>

<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=editcat&id=<%= cat.getID() %>">Edit</a>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=delcat&id=<%= cat.getID() %>">Del</a>
</td>

<%
                     }
                    if( !isSelect && (c % ELEMENTS_IN_ROW) == (ELEMENTS_IN_ROW - 1) ) {
%>

</tr>

<%
                    }
                    c++;
                }
            }
            if( isSelect ) {
%>

<br /><br />
<input type="button" value="Send" onclick="send();"><br />
</form>
<td>
</tr>

<%
            }
            if( !isSelect && pics != null ) {
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
<img src="img/pic.png" /><br /><br />

<%= pic.getName() %>
</a><br />
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
            if( ((c % ELEMENTS_IN_ROW) != 0) && !isSelect)
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
<img src="<%= session.getAttribute( "absolutePath" ) + "/files/" +
    ( new File( pic.getURL() ).getName() ) %>" />
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

<% 
    if( isSelect ) {
%>

</body>
</html>

<%
        session.setAttribute( "cut", null );
    } 
%>