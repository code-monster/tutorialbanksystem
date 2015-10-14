
bankSystemApp = null;
jQuery(document).ready(function ($j) {


    function BankSystemApp() {

        this.insertParam = function (key, value) {

            if (document.location.toString().indexOf("?") == -1 && document.location.toString().indexOf("&") == -1) {
                document.location.href = document.location + "?" + key + "=" + value;
                return;
            }


            key = encodeURI(key);
            value = encodeURI(value);

            var kvp = document.location.search.substr(1).split('&');

            var i = kvp.length;
            var x;
            while (i--) {
                x = kvp[i].split('=');

                if (x[0] == key) {
                    x[1] = value;
                    kvp[i] = x.join('=');
                    break;
                }
            }

            if (i < 0) {
                kvp[kvp.length] = [key, value].join('=');
            }

            //this will reload the page, it's likely better to store this until finished
            document.location.search = kvp.join('&');
        }


        $j("select.auto-sender").on("change", function () {

            if ($j(this).val() == 0){
                window.location.href =    location.protocol + '//' + location.host + location.pathname;
            }else{
                bankSystemApp.insertParam("showUserTransaction", $j(this).val());
            }

        });


    }

    bankSystemApp = new BankSystemApp();
});












