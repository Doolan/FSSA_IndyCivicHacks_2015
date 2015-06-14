
window.onload = function(){
	$('#login').on('submit',function(e){
		checkin()
		e.preventDefault()
	});
}

var checkin = function(){
	console.log('hit');	
	var name = $('#login__username').val().split(' ');
	var packet ={
		firstName: name[0],
		lastName: name[1],
		zipcode: $('#login__zipcode').val(),
		ssn: $('#login__ssn').val(),
		dob: $('#login__DOB')
	}

	$.ajax({
		type: "POST",
		url: "http://localhost:59810/Home/checkin",
		data: {stuff: packet},
		success: function(){
			console.log('success');
		}
	});	
}

/**function checkin(){
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
}**/

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

