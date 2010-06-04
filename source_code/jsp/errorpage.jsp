<%@ page isErrorPage="true" import="java.io.*" %>

<html>
    <head>
        <title>Exceptional Even Occurred!</title>
    </head>
    <body>

        <font color="red">
            <%= exception.toString() %>
            <br /><br />
        </font>
        
        <font color="red">
            <b>Stack trace below:</b>
            <br />
        </font>

        <%

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            out.print("<pre>");
            out.print(sw);
            out.print("</pre>");
            sw.close();
            pw.close();

        %>

    </body>
</html>