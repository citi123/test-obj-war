<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>choose pay method</title>
</head>
<body>
    用户名：${name}
    <br>
    商品 : ${product}
    <br>
    价格 : ${price}

    <br>

    <form action="/testForm/realPay" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="name" value="${name!''}">
        <input type="hidden" name="product" value="${product}">
        <input type="hidden" name="price" value="${price}">

        <input type="radio" value="alipay" name="payMethod" id="alipay"> <label for="alipay">支付宝</label>
        <input type="radio" value="wxpay" name="payMethod" id="wxpay"> <label for="wxpay">微信</label>
        <input type="submit" value="确认">
    </form>
</body>
</html>