var goToQueueDetail = function (name) {
    var Packet = {
        queueName: name
    }

    $.ajax({
        url: 'Queue/SetDetails',
        type: 'POST',
        data: { queueName: name },
        success: function (data) {
            window.location = 'Queue/Details';
        },
        error: function (request, status, error) {
            console.log("failed post");
        }
    });
}

var returnQueueDetail = function (name) {
    window.location = 'Details';
}

var goToPersonDetail = function (id) {
    $.ajax({
        url: 'ViewPerson',
        type: 'POST',
        //contentType: 'application/json; charset=utf-8',
        data: { PersonID: id },//JSON.stringify(Packet),
        success: function (data) {
            window.location = 'Customer';
        },
        error: function (request, status, error) {
            console.log("failed post");
        }
    });
}
var removePerson = function (id) {
    $.ajax({
        url: 'RemovePerson',
        type: 'POST',
        //contentType: 'application/json; charset=utf-8',
        data: { PersonID: id},//JSON.stringify(Packet),
        success: function (data) {
            var idstring = '#' + id;
            $(idstring).remove();
            //window.location = 'Queue/Details';
        },
        error: function (request, status, error) {
            console.log("failed post");
        }
    });
}