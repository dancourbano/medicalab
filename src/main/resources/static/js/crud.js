 
function dropDown(id) {
    var dropDownTable = '<div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-sm">'
        + '<button type="button" onclick="showDetail(' + id + ')" class="btn btn-default"><i class="fa fa-pencil-square-o"></i></button>'
        + '<button type="button" onclick="deleteData(' + id + ')" class="btn btn-danger"><i class="fa fa-trash-o"></i></button>'
        + '</div></div>';

    return dropDownTable;
}
function saveFunction(saveURL,arrayData,idElement) {
	var TypeJson;
	 console.log("idElement "+idElement);
    if (idElement!= "0") {
        var id = parseInt(idElement);
        TypeJson='put';
    } else{
        var id = 0;
        TypeJson='post';
    }	
    sUrl = saveURL;
     
    
    $.ajax({
        url: sUrl,
        type: TypeJson,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(arrayData),
        beforeSend: function () {
            //showLoadingPage();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //closeLoadingPage();
            swal(jqXHR.statusText);
        },
        success: function (response) {

            //closeLoadingPage();
            if (response.status == "error") {
                mensajeSalida = "";
                $.each(response.result,
                    function (idx, elem) {
                        mensajeSalida = elem.defaultMessage + "\n" + mensajeSalida;
                    });
                swal("Advertencia", mensajeSalida, "warning");

            } else {
                resetForm();
                swal("Éxito",
                    "Se Guardó con éxito");
                showList();
                $('#modal_default').modal('hide');

            }
        }
    });
}

function showListAllFunctionGenerico(listURL,functionSetData) {

    $.ajax({
        url: listURL,
        dataType: 'json',
        type: 'GET',
        before: function () {

        },
        success: function (data) {
        	functionSetData(data)
        },
        error: function (jqXHR, textStatus, errorThrown) {
            swal("Error", jqXHR.statusText);
        }
    });
}

function showListAllFunction(listURL,arrayDatatable,dtTable) {

            $.ajax({
                url: listURL,
                dataType: 'json',
                type: 'GET',
                before: function () {

                },
                success: function (data) {
                	dtTable.clear().draw();
                    $.each(data,
                        function (idx, elem) {
                    	dtTable.row.add(arrayDatatable(elem)).draw();
                        });

                },
                error: function (jqXHR, textStatus, errorThrown) {
                    swal("Error", jqXHR.statusText);
                }
            });
        }

function showDetailFunction(getByURL,id, functionSetData) {

    url = getByURL + id;
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'GET',
        before: function () {

        },
        success: function (data) {
        	functionSetData(data);
            
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.statusText);
        }
    });
    $('#modal_default').modal();
}
function showDetailFunctionPost(getByURL,arrayData, functionSetData) {

    $.ajax({
        url: getByURL,
        dataType: 'json',
        type: 'POST',        
        contentType: 'application/json',
        data: JSON.stringify(arrayData),
        before: function () {

        },
        success: function (data) {
        	console.log("data"+data);
        	functionSetData(data);
            
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.statusText);
        }
    });
    $('#modal_default').modal();
}
function deleteElement(textElement,idCliente,urlElement,showListFunctions,resetFormFunction) {

    swal(
        {
            title: "Desea dar de baja "+textElement,
            text: "El estado será Inactivo "+textElement,
            type: "warning",
            buttons: true,
            dangerMode: true
        })
        .then((willDelete) => {
        	if (willDelete) {
            sUrl = urlElement+idCliente;

            $.ajax({
                url: sUrl,
                type: 'DELETE',

                beforeSend: function () {
                    //showLoadingPage();
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    swal("Error", jqXHR.statusText);
                },
                success: function (response) {

                    if (response.status == "error") {
                        setTimeout(function () {
                            swal("Error", "No se ha podido Eliminar");
                        }, 2000);
                    } else {
                         
                        
                        setTimeout(
                            function () {
                                swal("Éxito", response.message);
                            }, 2000);
                        showListFunctions();
                        resetFormFunction();
                        $('#modal_default').modal('hide');
                    }
                }
            });
        	}else {
        	    swal("Accion cancelada");
        	}
        });
}
function deleteElementDELETE(textElement,idCliente,urlElement,showListFunctions) {

    swal(
        {
            title: "Desea eliminar "+textElement,
            text: "Se borrarán los datos del "+textElement,
            type: "warning",
            showLoaderOnConfirm: true,
            showCancelButton: true,
            confirmButtonColor: "#37474F",
            confirmButtonText: "Eliminar",
            closeOnConfirm: false
        },
        function () {
            sUrl = urlElement+idCliente;

            $.ajax({
                url: sUrl,
                type: 'DELETE',

                beforeSend: function () {
                    //showLoadingPage();
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    swal("Error", jqXHR.statusText);
                },
                success: function (response) {

                    if (response.status == "error") {
                        setTimeout(function () {
                            swal("Error", "No se ha podido Eliminar");
                        }, 2000);
                    } else {
                         
                        
                        setTimeout(
                            function () {
                                swal("Éxito", response.message);
                            }, 2000);
                        showListFunctions();                        
                        $('#modal_default').modal('hide');
                    }
                }
            });
        });
}

function deleteElementBajaLogica(textElement,idCliente,urlElement,showListFunctions) {
	swal(
	        {
	            title: "Desea dar de baja "+textElement,
	            text: "El estado será Inactivo "+textElement,
	            type: "warning",
	            buttons: true,
	            dangerMode: true
	        })
	        .then((willDelete) => {
	        	if (willDelete) {
	        		
	                    sUrl = urlElement+idCliente;

	                    $.ajax({
	                        url: sUrl,
	                        type: 'POST',

	                        beforeSend: function () {
	                            //showLoadingPage();
	                        },
	                        error: function (jqXHR, textStatus,
	                                         errorThrown) {
	                            swal("Error", jqXHR.statusText);
	                        },
	                        success: function (response) {

	                            if (response.status == "error") {
	                                setTimeout(function () {
	                                    swal("Error", "No se ha podido Dar de Baja");
	                                }, 2000);
	                            } else {
	                                 
	                                
	                                setTimeout(
	                                    function () {
	                                        swal("Éxito", response.message);
	                                    }, 2000);
	                                showListFunctions();                        
	                                $('#modal_default').modal('hide');
	                            }
	                        }
	                    });
	                
	        	}else {
	        	    swal("Accion cancelada");
	        	}
	        });
        
}