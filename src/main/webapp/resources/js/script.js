
bankSystemApp = null;
jQuery(document).ready(function ($j) {


    function BankSystemApp() {

        $j("select.auto-sender").on("change", function () {

            if ($j(this).val() == 0){
                document.location.search =  $.query.remove('showUserTransaction');
            }else{
                document.location.search =  $.query.set('showUserTransaction', $j(this).val());
            }

        });


    }

    bankSystemApp = new BankSystemApp();
});












