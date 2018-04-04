<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>pay request</title>
</head>
<body>
    <form action="/testForm/pay" method="post" enctype="application/x-www-form-urlencoded">
        用户名称 : <input type="text" name="name" value="zhangsan"> <br>
        商品名称 : <input type="text" name="product" value="apple"> <br>
        价格 : <input type="text" name="price" value="23.53">   <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>