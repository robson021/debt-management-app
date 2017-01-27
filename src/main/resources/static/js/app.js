const POST = "post";
const GET = "get";

function sendByAjax (url_, method_, data_) {
  let promise = $.ajax({
    type: method_,
    url: url_,
    data: JSON.stringify(data_),
    contentType: "application/json",
    dataType: "html or json"
  });
  promise.always(function (data) {
    console.info(data.status + ': ' + data.responseText);
  });
  return promise;
}

function loginUser () {
  let json = {
    "email": $('#emailField').val(),
    "password": $('#passwordField').val()
  };

  let promise = sendByAjax('/user/login/', POST, json);
  promise.always(function (data) {
    if (data.status === 200) {
      window.location.href = '/my-debts';
    }
  });
}