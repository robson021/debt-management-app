const POST = "post";
const GET = "get";
const DELETE = "delete";

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

function validateFields () {
  return $('#passwordField').val() != '' && ($('#passwordField').val() === $('#repasswordField').val());
}

function checkResponseAndRedirect (response) {
  if (response.status === 200) {
    window.location.href = '/my-debts';
  }
}

function loginUser () {
  let json = {
    "email": $('#emailField').val(),
    "password": $('#passwordField').val()
  };

  let promise = sendByAjax('/user/login/', POST, json);
  promise.always(function (data) {
    checkResponseAndRedirect(data);
  });
}

function register () {
  if (!validateFields()) return;
  let json = {
    "email": $('#emailField').val(),
    "password": $('#passwordField').val(),
    "name": $('#nameField').val(),
    "surname": $('#surnameField').val()
  };

  let promise = sendByAjax('/user/register/', POST, json);
  promise.always(function (data) {
    checkResponseAndRedirect(data);
  });
}

function cancelDebt (assetId) {
  let uri = '/cancel-debt/' + assetId + '/';
  let promise = sendByAjax(uri, DELETE, null);
  console.info('done');
}