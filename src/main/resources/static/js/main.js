function load() {
    var className = "active-lang";
    var curLang = getCookieVal();
    var $ua = $("#ua");
    var $en = $("#en");

    console.log("in there");

    if (curLang == 'ua') {
        $en.removeClass(className);
    } else {
        $ua.removeClass(className);
    }

    $("#" + curLang).addClass("active-lang");

}

function changLang() {
    var className = "active-lang";
    var lang = event.target.alt;
    var $lang = $("#" + lang);

    console.log(lang);

    if ($lang.hasClass(className)) {
        return;
    }

    window.location.replace('?lang=' + lang);
}

function getCookieVal() {
    var startIndex = document.cookie.indexOf("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");
    var index = document.cookie.indexOf("=", startIndex);
    var endIndex = document.cookie.indexOf(";", index)

    return document.cookie.substring(index + 1, endIndex < 0 ? document.cookie.length : endIndex);
}