document.addEventListener("DOMContentLoaded", function(){

    document.title = "it is working fuckyeah"
    document.cookie = "how=dude";
    alert (document.cookie);

    function setCookie(key, value, expiredays) {

        let todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays); // 현재 시각 + 일 단위로 쿠키 만료 날짜 변경
        //todayDate.setTime(todayDate.getTime() + (expiredays * 24 * 60 * 60 * 1000)); // 밀리세컨드 단위로 쿠키 만료 날짜 변경
        document.cookie = key + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
    }

});