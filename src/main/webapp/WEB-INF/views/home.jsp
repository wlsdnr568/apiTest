<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/require/require.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>" ></script>

<script type="text/javascript">
require(["daum"] ,function(daum){
	
	daum.maps.load(function(){
		
		var container = document.getElementById('map');
		var options = {
			center: new daum.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

		var map = new daum.maps.Map(container, options);
		
	})
	
})
</script>

<script>
require(["getData", "xDomain"] ,function(getData, xDomain){
	
	//1.컨트롤러 방식
	getData.getNpsData1();
	
	//2.ajax 방식1
	getData.getNpsData2(11,11560,11560121,27,"M",0);
	
	//3.ajax 방식2
	getData.getNpsData3(11,11560,11560121,27,"M",0);
	
	//사업장 정보 조회
	getData.checkBizDetail();
	
	
})
</script>

<style type="text/css">
label{font-weight: bold;}
</style>

<title>Home</title>
</head>
<body>

<label>지도</label>
<div id="map" style="width:500px;height:500px;"></div>

<div style="margin-top: 50px;border: 1px solid black">
	<label>컨트롤러 방식</label>
	<div id="result1"></div>
</div>

<div style="margin-top: 50px;border: 1px solid black">
	<label>ajax 방식1</label>
	<div id="result2"></div>
</div>

<div style="margin-top: 50px;border: 1px solid black">
	<label>ajax 방식2</label>
	<div id="result3"></div>
</div>

<div style="margin-top: 50px;margin-bottom: 100px;border: 1px solid black">
	<label>사업장 정보 조회</label>
	<div id="result4"></div>
</div>

</body>
</html>
