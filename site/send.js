var PATH="/"
var NEW_PATH;

function switch_send()
{
	switch (STATE)
	{
		case "connection":

			connection();
			break;
		case "list":
			list();
			break;
		case "mkdir":
			mkdir();
			break;
		case "store":
			store();
			break;
		case "retr":
			retr();
			break;
		case "cwd":
			cwd();
			break;
		case "cdup":
			cdup();
			break;
		case "rename":
			rename();
			break;
		case "deletefile":
			deletefile();
			break;
		case "deletedir":
			deletedir();
			break;
		case "deco":
			deco();
			break;
	}
}

function updateResult(data)
{
	console.log("that was complete !");
	console.log(data.status);
	var result = document.getElementById('result_text');
	var success = document.getElementById('success');
	var error = document.getElementById('error');
	var answer = JSON.parse(data.responseText).contenu;
	if( (data.status >= 200) && (data.status < 300) )
	{
		var result = document.getElementById('result_text');
		if( (data.status >= 200) && (data.status < 300) ){
			document.getElementById('result_text').style.display='block';
			document.getElementById('success').style.display='block';
			document.getElementById('success').innerHTML = "<strong>success</strong>";
			document.getElementById('error').style.display='none';
			if (answer == "Welcome to FTP/Passerelle"){

				update_connection_button();
			}
		}
	}
	if( (data.status >= 300) && (data.status < 500) ){
		console.log(data);
		document.getElementById('result_text').style.display='block';
		document.getElementById('error').style.display='block';
		document.getElementById('success').style.display='none';
		document.getElementById('error').innerHTML = "<strong>Error</strong>";
		console.log(data.responseText);
	}

	result.innerHTML = answer;

}

function deco(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/quit",

		complete : updateResult

	});
	update_deconnection_button();
}

function cwdUpdatePath(data)
{
	updateResult(data);
	if(!((data.status >= 200) && (data.status < 300)) )
		return;
	console.log("path worked");
	PATH=NEW_PATH;
	console.log("new path is " + PATH);
	var path     = document.getElementById('path');
	path.value = PATH;
}

function cdupUpdatePath(data)
{
	updateResult(data);
	if(!((data.status >= 200) && (data.status < 300)) )
		return;
	PATH=PATH.match(/.*(?=\/)/)[0];
	var path     = document.getElementById('path');
	path.value = "/" + PATH;
}

function connection(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/connect",

		complete : updateResult

	});
}

function list(){

	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/list",

		complete : updateResult

	});
};

function mkdir(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var dir      = document.getElementById('dir').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "POST",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + dir,

		complete : updateResult
	});
};

function store(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var file     = document.getElementById('file').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "POST",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + file + "/file",

		complete : updateResult
	});
};

function retr(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var file     = document.getElementById('file').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
//		contentType: "application/octet-stream",
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + file + "/file",

		complete : wrapRetr
	});
};

function wrapRetr(data)
{
	updateResult("{contenu:" + data + "}");

	if(!((data.status >= 200) && (data.status < 300)) )
		return;

	var link = document.createElement("a");
	console.log(data);
	link.href= "data:application/octet-stream;charset=utf-8," + encodeURIComponent(data.responseText);
	link.click();
	/*
    var t = document.createTextNode("This is a paragraph.");
	<a href="data:application/octet-stream;charset=utf-16le;base64,//5mAG8AbwAgAGIAYQByAAoA">text file</a>
	*/
}

function cwd(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var path     = document.getElementById('path').value;
	NEW_PATH = path;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/" + path + "/cwd",

		complete : cwdUpdatePath
	});
};

function cdup(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "GET",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/cdup",

		complete : cdupUpdatePath
	});
};

function rename(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var file     = document.getElementById('file').value;
	var newfile  = document.getElementById('newfile').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "PUT",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + file + "?newfile=" + newfile,

		complete : updateResult
	});
};


function deletefile(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var file     = document.getElementById('file').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "DELETE",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + file + "?type=file",

		complete : updateResult
	});
};

function deletedir(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var dir      = document.getElementById('dir').value;
	$.ajax({
		headers: {
			"username": username,
			"password": password
		},
		type: "DELETE",
		url: "http://localhost:8080/myapp/ftp/" + PATH + "/" + dir + "?type=dir",

		complete : updateResult
	});
};
