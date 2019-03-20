var blist = document.getElementById("list");
var bmkdir = document.getElementById("mkdir");
var bstore = document.getElementById("store");
var bretr = document.getElementById("retr");
var bcwd = document.getElementById("cwd");
var bcdup = document.getElementById("cdup");
var brename = document.getElementById("rename");
var bdeletefile = document.getElementById("deletefile");
var bdeletedirectory = document.getElementById("deletedirectory");

var el_username = document.getElementById("username_wrapper");
var el_password = document.getElementById("password_wrapper");
var el_path     = document.getElementById("path_wrapper");
var el_file     = document.getElementById("file_wrapper");
var el_newfile  = document.getElementById("newfile_wrapper");
var el_dir      = document.getElementById("dir_wrapper");

var button_connection = document.getElementById("button_connection");
var button_deconnection = document.getElementById("button_deconnection");
var button_list = document.getElementById("button_list");
var button_mkdir = document.getElementById("button_mkdir");
var button_store = document.getElementById("button_store");
var button_retr = document.getElementById("button_retr");
var button_cwd = document.getElementById("button_cwd");
var button_cdup = document.getElementById("button_cdup");
var button_rename = document.getElementById("button_rename");
var button_deletefile = document.getElementById("button_deletefile");
var button_deletedir = document.getElementById("button_deletedir");

document.getElementById('result_text').style.display='none';
document.getElementById('success').style.display='none';
document.getElementById('error').style.display='none';

update_connection();

function update_connection()
{
	button_deconnection.style.display = 'none';
	button_list.style.display = 'none';
	button_mkdir.style.display = 'none';
	button_store.style.display = 'none';
	button_retr.style.display = 'none';
	button_cwd.style.display = 'none';
	button_cdup.style.display = 'none';
	button_rename.style.display = 'none';
	button_deletefile.style.display = 'none';
	button_deletedir.style.display = 'none';
	el_username.style.display = 'block';
	el_password.style.display = 'block';
	el_path   .style.display = 'none';
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';
	STATE="connection";
}



function update_deconnection()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'none';
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE = "deco";
}


function update_list()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display  = 'block';
	el_file   .style.display  = 'none';
	el_newfile.style.display  = 'none';
	el_dir    .style.display  = 'none';

	STATE="list";
}

function update_mkdir()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'block';

	STATE="mkdir";
}

function update_store()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'block';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE="store";
}

function update_retr()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'block';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE="retr";
}

function update_cwd()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .removeAttribute("disabled");
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE="cwd";
}

function update_cdup()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE="cdup";
}

function update_rename()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'block';
	el_newfile.style.display = 'block';
	el_dir    .style.display = 'none';

	STATE="rename";
}

function update_deletefile()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'block';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'none';

	STATE="deletefile";
}

function update_deletedir()
{
	el_username.style.display = 'none';
	el_password.style.display = 'none';
	el_path   .style.display = 'block';
	el_path   .setAttribute("disabled","disabled");
	el_file   .style.display = 'none';
	el_newfile.style.display = 'none';
	el_dir    .style.display = 'block';

	STATE="deletedir";
}


function update_connection_button(){
	button_connection.style.display = 'none';
	button_deconnection.style.display = 'block';
	el_username.value = "valeur";
	button_list.style.display = 'block';
	button_mkdir.style.display = 'block';
	button_store.style.display = 'block';
	button_retr.style.display = 'block';
	button_cwd.style.display = 'block';
	button_cdup.style.display = 'block';
	button_rename.style.display = 'block';
	button_deletefile.style.display = 'block';
	button_deletedir.style.display = 'block';
	update_list();
}

function update_deconnection_button(){
	button_connection.style.display = 'block';
	button_deconnection.style.display = 'none';
	button_list.style.display = 'none';
	button_mkdir.style.display = 'none';
	button_store.style.display = 'none';
	button_retr.style.display = 'none';
	button_cwd.style.display = 'none';
	button_cdup.style.display = 'none';
	button_rename.style.display = 'none';
	button_deletefile.style.display = 'none';
	button_deletedir.style.display = 'none';
	update_connection();


}
