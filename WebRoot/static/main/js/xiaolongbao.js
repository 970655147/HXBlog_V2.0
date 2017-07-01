
/*页面载入后*/
$(document).ready(function() {
    $('#xiaolongbao').dblclick(function() {
        $("html,body").animate({scrollTop: $("#topEle").offset().top}, 1000);
    })

	var oImg = document.getElementById('xiaolongbao');
    /**
	 * 初始化 xiaolongbao 的位置
     */
    var xiaolongbaoPos = sessionStorage.getItem(xiaolongbaoPosKey)
	if((xiaolongbaoPos !== null) && (xiaolongbaoPos !== undefined)) {
        xiaolongbaoPos = JSON.parse(xiaolongbaoPos)
		oImg.style.left = xiaolongbaoPos.offLeft
		oImg.style.top = xiaolongbaoPos.offTop
	}


	/*拖拽功能*/
	(function() {
		addEvent(
				oImg,
				'mousedown',
				function(ev) {
					var oEvent = prEvent(ev), oParent = oImg.parentNode, disX = oEvent.clientX
							- oImg.offsetLeft, disY = oEvent.clientY
							- oImg.offsetTop, startMove = function(ev) {
						if (oParent.setCapture) {
							oParent.setCapture();
						}
						var oEvent = ev || window.event, l = oEvent.clientX
								- disX, t = oEvent.clientY - disY;
						oImg.style.left = l + 'px';
						oImg.style.top = t + 'px';

						var xiaolongbaoPos = {}
                        xiaolongbaoPos.offLeft = oImg.style.left
                        xiaolongbaoPos.offTop = oImg.style.top
						sessionStorage.setItem(xiaolongbaoPosKey, JSON.stringify(xiaolongbaoPos))

						oParent.onselectstart = function() {
							return false;
						}
					}, endMove = function(ev) {
						if (oParent.releaseCapture) {
							oParent.releaseCapture();
						}
						oParent.onselectstart = null;
						removeEvent(oParent, 'mousemove', startMove);
						removeEvent(oParent, 'mouseup', endMove);
					};
					addEvent(oParent, 'mousemove', startMove);
					addEvent(oParent, 'mouseup', endMove);
					return false;
				});

	})();
});

/*绑定事件*/
function addEvent(obj, sType, fn) {
    if (obj.addEventListener) {
        obj.addEventListener(sType, fn, false);
    } else {
        obj.attachEvent('on' + sType, fn);
    }
};
function removeEvent(obj, sType, fn) {
    if (obj.removeEventListener) {
        obj.removeEventListener(sType, fn, false);
    } else {
        obj.detachEvent('on' + sType, fn);
    }
};
function prEvent(ev) {
    var oEvent = ev || window.event;
    if (oEvent.preventDefault) {
        oEvent.preventDefault();
    }
    return oEvent;
}
