/*
 * jQuery File Upload Plugin JS Example 6.7
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */

$jq(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $jq('#fileupload').fileupload();

    // Enable iframe cross-domain access via redirect option:
    $jq('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (window.location.hostname === 'blueimp.github.com') {
        // Demo settings:
        $jq('#fileupload').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            process: [
                {
                    action: 'load',
                    fileTypes: /^image\/(gif|jpeg|png)$/,
                    maxFileSize: 20000000 // 20MB
                },
                {
                    action: 'resize',
                    maxWidth: 1440,
                    maxHeight: 900
                },
                {
                    action: 'save'
                }
            ]
        });
        // Upload server status check for browsers with CORS support:
        if ($jq.support.cors) {
            $jq.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $jq('<span class="alert alert-error"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        /*$jq('#fileupload').each(function () {
            var that = this;
            $jq.getJSON(this.action, function (result) {
                if (result && result.length) {
                    $jq(that).fileupload('option', 'done')
                        .call(that, null, {result: result});
                }
            });
        });*/
    }
});

function handleImgError(srcElement){
	 srcElement.onerror = null;
	    srcElement.src="/bootstrap/img/banner.jpg";
	};