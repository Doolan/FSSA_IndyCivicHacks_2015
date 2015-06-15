
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
		date: document.getElementById("login__DOB").valueAsDate
	}
	var pkt = JSON.stringify(packet);

	try{
		$.ajax({
		type: "POST",
		url: "http://localhost:59810/Home/checkin",
		data: {stuff: pkt},
		success: function(){
			console.log('success');
			window.location = 'reasons2.html';
		}
	});	
	}catch(e){
		//don't care
	}
	window.location = 'reasons2.html';

}


// "{\"firstName\":\"steven\",\"zipcode\":\"60062\",\"ssn\":\"22222222\",\"date\":\"1995-07-26T00:00:00.000Z\"}"

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

