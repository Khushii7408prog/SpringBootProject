console.log("this is script file")

const toggleSidebar = () => {
        if ($(".sidebar").is(":visible")) {
            // Close sidebar
            $(".sidebar").css("display", "none");
            $(".content").css("margin-left", "0%");
        } else {
            // Open sidebar
            $(".sidebar").css("display", "block");
            $(".content").css("margin-left", "20%");
        }
    };