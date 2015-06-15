window.onload = function(){
	//$('input').click(setstatus());
}

var setstatus = function(e){

	console.log('hit');
	console.log($(e).val());

	try{
	 $.ajax({
        url: 'http://localhost:59810/Home/setReason',
        type: 'POST',
        //contentType: 'application/json; charset=utf-8',
        data: { reason: $(e).val() },//JSON.stringify(Packet),
        success: function (data) {
           // window.location = 'Customer';
        },
        error: function (request, status, error) {
            //console.log("failed post");
        }
    });
	}
	catch(e){
		//don't care
	}
	window.location = 'programs2.html';

}