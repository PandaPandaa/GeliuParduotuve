$(document).ready(function () {

    $('.cardHoverBtn').click(function(){
        var src = $(this).prev().attr('src');
        $("#img01").attr("src",src);
        $('.ui.modal.tiny').modal('show');
    });

    $('.ui .item').on('click', function () {
        var id = $(this).parent().attr('id');
        $('.ui .' + id + '.item').removeClass('active');
        $('.' + id + '.segment').removeClass('active');
        var currentTab = $(this).attr("data-tab");
        $("[data-tab='" + currentTab + "']").addClass('active');
        $(this).addClass('active');
    });


});

document.addEventListener('DOMContentLoaded', function (event) {// Get the modal

    if (sessionStorage.surveydone) {
        for (i = 0; i < sessionStorage.surveydone.length; i++) {
            if (document.getElementById("surveyId")) {
                if (sessionStorage.surveydone.charAt(i) == document.getElementById("surveyId").innerHTML) {
                    document.getElementById("survey").remove();
                    document.getElementById("surveyStatus").innerHTML = "No new survey at the moment";
                    break;
                }
            }
        }

    }

    //change size of news panel on resize of window
    window.onresize = function(){
        var panel= document.getElementsByClassName("ui-scrollpanel");
        if(panel){
            if(panel[0]){
                panel[0].style.width="inherit";
            }
        }
    };


    // Get the modal
    var bookCoverModal = document.getElementById("myModal");
// Get the image and insert it inside the modal - use its "alt" text as a caption
    var img1 = document.getElementById("img01");

    var bookCards = document.getElementsByClassName('homepageImg');
    var index;
    for (index = 0; index < bookCards.length; index++) {
        bookCards[index].onclick = function () {


        }
    }

    var imgBtn = document.getElementsByClassName('cardHoverBtn');
    for (index = 0; index < imgBtn.length; index++) {
        imgBtn[index].onclick = function () {
            // Get the modal
            var bookCoverModal = document.getElementById("myModal");
// Get the image and insert it inside the modal - use its "alt" text as a caption
            // var img1 = document.getElementById("img01");
            //bookCoverModal.style.display = "block";
            //img1.src = this.parentElement.getElementsByTagName('img')[0].src;

        }
    }
// Get the <span> element that closes the modal
    var close = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
    close.onclick = function () {
        bookCoverModal.style.display = "none";
    }

});




function viewAd(url) {
    window.open(url);
}

function checkIfLoggedIn() {
    var userCookies = document.cookie.split(';');
    for (var i = 0; i < userCookies.length; i++) {
        console.log(userCookies[i]);
    }
    return true;
}

function signOut() {
    document.cookie = 'BOOK_STORE_LOGIN=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}


function showResults(surveyId) {
    if (sessionStorage.surveydone) {
        sessionStorage.surveydone = sessionStorage.surveydone + surveyId + ",";
    } else {
        sessionStorage.surveydone = "" + surveyId + ",";
    }
    document.getElementById("survey").remove();
    document.getElementById("results").setAttribute("class", "visible");
}

function showMore() {

    var dots = document.getElementById("dots");
    var moreText = document.getElementById("more");
    var btnText = document.getElementById("moreBtn");

    if (dots.style.display === "none") {
        dots.style.display = "inline";
        btnText.innerHTML = "Read more";
        moreText.style.display = "none";
    } else {
        dots.style.display = "none";
        btnText.innerHTML = "Read less";
        moreText.style.display = "inline";
    }
}