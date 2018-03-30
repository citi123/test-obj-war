<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>success</title>

<script type="text/javascript">
	// window.location.href = 'http://localhost:2090/receive/pay_result';
</script>

</head>
<body>
	receive success
	
	<form action="http://localhost:2090/receive/pay_result" method="post">
	</form>	
	<script type="text/javascript">
		document.forms[0].submit();
	</script>
</body>
</html>