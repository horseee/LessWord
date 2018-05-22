<%@ tag import="java.util.Date" import="java.text.DateFormat"%>
<%@ variable name-given="longDate" %>
<%@ variable name-given="shortDate" %>
<%
    Date now = new Date(System.currentTimeMillis());
    DateFormat longFormat =
            DateFormat.getDateInstance(DateFormat.LONG);
    DateFormat shortFormat = 
            DateFormat.getDateInstance(DateFormat.SHORT);
    jspContext.setAttribute("longDate", longFormat.format(now));
    jspContext.setAttribute("shortDate", shortFormat.format(now));
%>
<jsp:doBody/>