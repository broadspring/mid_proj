function connectMenuSummonEvent() {
	$("#siteTitleArea").mouseenter(function() {
		$("#siteTitleArea").css("top", "0px");
		$("#siteLoginArea").css("top", "105px");
	});
	$("#siteTitleArea").mouseleave(function() {
		$("#siteTitleArea").css("top", "-40px");
		$("#siteLoginArea").css("top", "65px");
	});
}

function connectAddrSearchEvent() {
	$("#addrSearchBtn").click(function() {
		new daum.Postcode({
			oncomplete : function(data) {
				$("#jm_addr3Input").val(data.zonecode);
				$("#jm_addr1Input").val(data.roadAddress);
			}
		}).open();
	});
}


function connectJoinIdInputEvent() {
	$("#jm_idInput").keyup(function() {
		var m_id = $(this).val();
		$.ajax({
			url : "member.get",
			data : {
				"m_id" : m_id
			},
			success : function(data) {
				console.log(data);
//				if (data.member.length == 1) {
				if (data == 1) {
					$("#jm_idInput").css("color", "#F44336");
				} else {
					$("#jm_idInput").css("color", "white");
				}
			}
		});
	});
}

function connectSNSWriteFormSummonEvent() {
	var snsWriteFormVisible = false;

	$("#snsWriteFormSummoner").click(function() {
		if (snsWriteFormVisible) {
			$("#snsWriteArea").css("bottom", "-150px");
		} else {
			$("#snsWriteArea").css("bottom", "10px");
		}
		snsWriteFormVisible = !snsWriteFormVisible;
	});
}


function connectSNSSearchFormSummonEvent() {
	var snsSearchFormVisible = false;

	$("#snsSearchFormSummoner").click(function() {
		if (snsSearchFormVisible) {
			$("#snsSearchArea").css("left", "-239px");
		} else {
			$("#snsSearchArea").css("left", "20px");
		}
		snsSearchFormVisible = !snsSearchFormVisible;
	});
}


function connectDRUploadFormSummonEvent() {
	var drUploadFormVisible = false;

	$("#drUploadFormSummoner").click(function() {
		if (drUploadFormVisible) {
			$("#drUploadArea").css("bottom", "-70px");
		} else {
			$("#drUploadArea").css("bottom", "10px");
		}
		drUploadFormVisible = !drUploadFormVisible;
	});
}

function connectCommunityMemberSummonEvent() {
	var communityMemberVisible = false;

	$("#communityMemberSummoner").click(function() {
		if (communityMemberVisible) {
			$("#communityMemberArea").css("bottom", "-500px");
		} else {
			$("#communityMemberArea").css("bottom", "10px");
		}
		communityMemberVisible = !communityMemberVisible;
	});
}


function connectShowWeatherEvent() {
	$(document).contextmenu(function() {
		return false;
	});

	$("html").mousedown(function(e) {
		if (e.button == 2) {
			var url = "http://api.openweathermap.org/data/2.5/weather?q=seoul&appid=e7b1a57cd2158c8d195bfb24b7597bad&units=metric&lang=kr";
			$.getJSON(url, function(data){
				var icon = "https://openweathermap.org/img/wn/"+data.weather[0].icon+".png";
				$("#weatherImg").attr("src", icon);
				$("#weatherDesc").text(data.weather[0].description);
				$("#weatherTemp").text(data.main.temp + "℃(" + data.main.humidity + "%)");
				
				$("#weatherArea").css("opacity", "1");
				$("#weatherArea").css("top", (e.pageY - 30) + "px");
				$("#weatherArea").css("left", (e.pageX - 70) + "px");
				
				setTimeout(function(){
					$("#weatherArea").css("opacity", "0");
				}, 2000);
			});
		}
	});
}

function showAqicn() {
	_aqiFeed({
		container : "city-aqi-container",
		display : "<span style='%style;font-size:10pt;z-index:3;padding:5px 5px;font-weight:800;font-family:'a';'>공기 : %aqi(%impact)</span>",
		city : "seoul",
		lang : "kr"
	});
}




function connectSkribblEvent() {
	var socket = io.connect("http://localhost:8090");
	var paper = document.getElementById("paper");
	var pen = paper.getContext("2d");
	var draw = false;
	var c = "#FFFFFF";
	pen.lineWidth = 3;

	var startX = 0;
	var startY = 0;
	var endX = 0;
	var endY = 0;
	var paperX = $("#paper").offset().left + 8;
	var paperY = $("#paper").offset().top + 8;
	$("#brushColor").css("top", (paperY + 5) + "px");
	$("#brushColor").css("left", (paperX + 5) + "px");

	$(window).resize(function() {
		paperX = $("#paper").offset().left + 8;
		paperY = $("#paper").offset().top + 8;
		$("#brushColor").css("top", (paperY + 5) + "px");
		$("#brushColor").css("left", (paperX + 5) + "px");
	});

	$("#paper").mousedown(function(e) {
		draw = true;
		startX = e.pageX - paperX;
		startY = e.pageY - paperY;
	});
	$("#paper").mousemove(function(e) {
		endX = e.pageX - paperX;
		endY = e.pageY - paperY;
		if (draw) {
			socket.emit("myData", {
				c : c,
				sx : startX,
				sy : startY,
				ex : endX,
				ey : endY
			});
		}
		startX = endX;
		startY = endY;
	});
	$("#paper").mouseup(function(e) {
		draw = false;
	});
	$("#bc").keyup(function() {
		c = $(this).val();
		$(this).css("border", c + " solid 3px");
		$(this).css("color", c);
		$("#brushColor").css("color", c);
	});
	socket.on("yourData", function(data) {
		pen.strokeStyle = data.c;
		pen.beginPath();
		pen.moveTo(data.sx, data.sy);
		pen.lineTo(data.ex, data.ey);
		pen.stroke();
	});
}



$(function() {
	connectMenuSummonEvent();
	connectAddrSearchEvent();
	connectJoinIdInputEvent();
	connectSNSWriteFormSummonEvent();
	connectSNSSearchFormSummonEvent();
	connectDRUploadFormSummonEvent();
	connectCommunityMemberSummonEvent();
	connectShowWeatherEvent();
	showAqicn()
	connectSkribblEvent()
});