
window.onload = function(){
	$('#login').on('submit',function(e){
		checkin()
		e.preventDefault()
	});
}

function checkin(){
	console.log('hit');
	var packet ={
		"name": $("#login__username").value,
		"zipcode": $("#login__zipcode").value ,
		"DOB": $("#login__DOB").value 
	}

	$.ajax({
		type: "POST",
		url: "http://81f85025.ngrok.io/",
		data: packet,
		success: function(){
			console.log('success');
		}
	});
	window.location = "reasons2.html"
}

function checkin_sp(){
	console.log('hit');
	var packet ={
		"name": $("#login__username").value,
		"zipcode": $("#login__zipcode").value ,
		"DOB": $("#login__DOB").value 
	}

	$.ajax({
		type: "POST",
		url: "http://81f85025.ngrok.io/",
		data: packet,
		success: function(){
			console.log('success');
		}
	});
	window.location = "reasons2_sp.html"
}

