/**
 * @author  sen
 * @version 1.0.0
 * @desc    头像上传
 */

define(function (require, exports, module) {
    var $ = require('jquery');

    var UploadFile = function(){

    };

    UploadFile.prototype.render = function(){
        this.initForm();
        this.bindEvents();
    };

    UploadFile.prototype.bindEvents = function(){
        var self = this;

        $('#btn-submit').on('click',function(){

            var options = {
                    url: "/upload", 
                    dataType: 'json', 
                    contentType: "application/json;charset=utf-8", 
                    success: function( json ) { 
                        if (json.code == 200) {
                            $('#J-result').text('头像上传成功').show();
                        } else{
                            $('#J-result').text(json.data).show();
                        }
                    }
                };
            $('#form-upload').ajaxSubmit( options ); 

        });
        
    };

    UploadFile.prototype.initForm = function(bookInfo){
        $("#file-0").fileinput({
            uploadUrl: '/upload', // you must set a valid URL here else you will get an error
            allowedFileExtensions : ['jpg', 'png','gif'],
            overwriteInitial: false,
            maxFileSize: 1000,
            maxFilesNum: 1,
            allowedFileTypes: ['image'],
            slugCallback: function(filename) {
                return filename.replace('(', '_').replace(']', '_');
            }
        });
    };


    

    module.exports = UploadFile;
});