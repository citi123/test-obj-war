<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form name="payForm" method="post" enctype="application/x-www-form-urlencoded" action="${action}">

        <#list params?keys as param>
            ${param} : <input name="${param}" value="${params[param]}">
            <br>
        </#list>

        <input type="submit" value="支付">

    </form>

    <script>
        document.forms.payForm.submit();

    </script>
</body>
</html>