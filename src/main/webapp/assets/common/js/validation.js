$(function(){
  $.each($('form[validate=true]'), function(i, form){
    $(form).submit(validate);
  });
  $.each($('input[formaction]'), function(i, input){
    $(input).click(function(){
      var form = $(input).parents('form')
      $(form).attr('action', $(input).attr('formaction'))
    });
  });
});

function validate(e){
  var form = $(e.target);
  
  if(form.data('validated') == true){
    return true;
  }

  $.post(form.attr('action') + '/validate', $(e.target).serialize(), function(data){
    // clear all error messages
    $('.error').text('');
    
    if($.isEmptyObject(data)){
      form.data('validated', true);
      form.submit();
    } else {
      form.data('validated', false);
      displayErrors(data);
    }
  }, 'json');
  return false;
}

function displayErrors(data){
  $.each(data, function(key, value){
    $('#error-' + key.split('.').join('_')).text(value);
  });
}