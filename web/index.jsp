
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>My Test App</title>
  <script src="resources/jquery-3.3.1.js"></script>
  <script type="text/javascript">
    $(document).ready(function () {
      $("#btnParse").click(function () {

        var name = $("#text1").val();
        $.ajax({
          type: "post",
          data: {name:name},
          url: "parser",
          success: function (result) {
            $("#result").html(result);
              alert("Parse complete! ");
          },
          error: function (){
            alert("Parse failed! ");
          }
        });

      });
    });
  </script>

</head>

<body>

<form id="code" accept-charset="UTF-8">
<div id="div1" style="display: block; float: left" >
  <label>
<textarea id="text1" rows="20" cols="53">

<Envelope xmlns:urn="wsapi:Payment" xmlns:uts="wsapi:Utils">
<Body>
<urn:sendPayment>
  <token>001234</token>
  <cardNumber>811626834823422</cardNumber>
  <requestId>2255086658</requestId>
  <amount>100000.00</amount>
  <currency>RUB</currency>
  <uts:account type="source">009037269229</uts:account>
  <uts:account type="destination">088127269229</uts:account>
  <page>1</page>
  <field id="0" value="0800" />
  <field id="11" value="000001" />
  <field id="70" value="301" />
</urn:sendPayment>
</Body>
</Envelope>

</textarea>
  </label>
  <br>
  <input type="button" value="Parse" id="btnParse"><br><br>

</div>
  <div id="div2" style="display: block; float: left; margin-left: 20px">

    <label>
<textarea id="result" rows="20" cols="53">
</textarea>
    </label>
    <br>
    <input type="button" value="Send" id="btnSend"><br><br>
  </div>

</form>
</body>
</html>
