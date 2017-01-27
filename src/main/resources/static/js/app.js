const POST = "POST";
const GET = "GET";

function sendByAjax (url_, method_, data_, successCallback, errorCallback) {
  $.ajax({
    type: method_,
    url: url_,
    //data: JSON.stringify({ Markers: data_ }),
    data: JSON.stringify(data_),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: successCallback,
    failure: errorCallback
  });
}

function loginUser () {
  let json = {
    "email": $('#emailField').val(),
    "password": $('#passwordField').val()
  };

  sendByAjax('/login/', POST, json, function (success) {
    console.info('logged in');
    window.location.href = '/my-debts/';
  }, function (error) {
    alert('error');
    console.info(error);
  });
}