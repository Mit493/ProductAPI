/**
 * 
 */
$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validateProductForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidProductIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "ProductAPI",
 type : type,
 data : $("#formProduct").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
  location.reload(true);
 onProductSaveComplete(response.responseText, status);

 }
 }); 
});
// UPDATE==========================================
$(document).on("click", ".btnEdit", function(event)
{
$("#hidProductIDSave").val($(this).data("productid"));
 $("#proCode").val($(this).closest("tr").find('td:eq(0)').text());
 $("#proName").val($(this).closest("tr").find('td:eq(1)').text());
 $("#proPrice").val($(this).closest("tr").find('td:eq(2)').text());
 $("#proQty").val($(this).closest("tr").find('td:eq(3)').text());
 $("#proDesc").val($(this).closest("tr").find('td:eq(4)').text());
});

$(document).on("click", ".btnDelete", function(event)
{
 $.ajax(
 {
 url : "ProductAPI",
 type : "DELETE",
 data : "proID=" + $(this).data("productid"),
 dataType : "text",
 complete : function(response, status)
 {

  location.reload(true);
 onProductDeleteComplete(response.responseText, status);

 }
 });
});

// CLIENT-MODEL================================================================
function validateProductForm()
{
// CODE
if ($("#proCode").val().trim() == "")
 {
 return "Add Product Code.";
 }
// NAME
if ($("#proName").val().trim() == "")
 {
 return "Add Product Name.";
 } 

// PRICE-------------------------------
if ($("#proPrice").val().trim() == "")
 {
 return "Add Product Price.";
 }
// is numerical value
var tmpPrice = $("#proPrice").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for Product Price.";
 }
// convert to decimal price
 $("#proPrice").val(parseFloat(tmpPrice).toFixed(2));
 //QUANTITY---------------------------
 if ($("#proQty").val().trim() == "")
 {
 return "Add Product Quantity.";
 } 
 
// DESCRIPTION------------------------
if ($("#proDesc").val().trim() == "")
 {
 return "Add Product Description.";
 }
return true;
}



function onProductSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divProductsGrid").html(resultSet.data);

 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 

 $("#hidProductIDSave").val("");
 $("#formProduct")[0].reset();
}

function onProductDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divProductsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}   
 