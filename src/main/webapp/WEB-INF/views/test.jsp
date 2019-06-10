<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/require/require.js"/>"></script>
<script src="<c:url value="/resources/js/main.js"/>" ></script>

<!-- 주소 이동 위한 라이브러리 선언 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b41c7a1412b3f5a7b5483f019fc099d8&libraries=services,clusterer,drawing"></script>

<script>
require(["getData"] ,function(getData){
	
	getData.basicMap();
	
	$("#reset").click(function(){
		getData.reset();
	})
	
	//검색 내용 이볅하고 엔터 쳤을 때
	$("#value").on("keydown",function(event){
		
		if(event.keyCode == "13"){
			$("#search").trigger("click");
			this.blur(); //클릭 트리거 후 블러처리
		};
		
	});
	
	//검색 버튼 클릭했을 때
	$("#search").click(function(){
		
		var val = $("#value").val();
		
		if (val == ""){
			alert("회사명을 입력해 주세요.");
		} else {
			var compName = $("#value").val();
			getData.getBassInfoSearch(compName);
		};
		
	});
	
});
</script>

<style type="text/css">
	table {border: 1px solid black;border-collapse: collapse; margin-top: 20px; margin-left: 20px}
	th {border: 1px solid black; height:25px}
	td {border: 1px solid black; padding: 5px 1px 5px 1px; font-weight: normal; text-align: center;}
</style>

<title>test</title>
</head>
<body>

<div style="margin-left: 10px">

	<div style="margin-top: 50px"> 
		<label>회사명 : </label>
		<input type="text" id="value" style="height: 15px">
		<input type="button" id="search" value="검색">
		<input type="button" id="reset" value="초기화">
	</div>
	
	<div id="result" style="border: 1px solid black; width: 98.6%; margin-top: 10px;height: 300px;overflow: auto;">
		<table id="resultTable">
			<thead>
				<tr style="background-color: white">
					<th width="50px">번호</th>
					<th width="350px">사업장명</th>
					<th width="300px">사업장 주소</th>
					<th width="150px">상세 정보 보기</th>
					<th width="100px">위치 보기</th>
					<th width="100px" style="display: none" id="seq">식별 번호</th>  
				</tr>
			</thead>
		</table>
	</div>
	
	<div style="margin-top: 10px; height: 500px">
		<div id="map" style="width: 49%; height: 500px;border: 1px solid black;float: left;margin-right: 10px"></div>
		<div id="bizDetail" style="width: 49%; height: 500px;border: 1px solid black;float: left">
			<pre id="result4"></pre>
		</div>
	</div>
	
</div>

</body>
</html>


