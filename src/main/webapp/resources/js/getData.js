define(["getData", "daum"], function(getData, daum){
	
	//사업장 정보 조회 서비스
	function getBassInfoSearch(compName){
		
		var result;
		
		$.ajax({
			url: "getBassInfoSearch/"+compName,
			type: "get",
			dataType: "html",
			success: function(data){
				
				//String -> XML
				var xml = $.parseXML(data);
				
				//item태그 element 모음
				var item = xml.getElementsByTagName("item");
				
				var standard = 0;
				var array = new Array();
				var check = 0;
				
				for(var i=0; i<item.length; i++) {
					
					var bzowrRgstNo = item[i].getElementsByTagName("bzowrRgstNo")[0].innerHTML.substring(0,6); //사업자 등록번호
					
					if(i > 0){
						
						var bzowrRgstNoPrev = item[i-1].getElementsByTagName("bzowrRgstNo")[0].innerHTML.substring(0,6); //사업자 등록번호 비교 값
						
						if(bzowrRgstNo == bzowrRgstNoPrev){
							
							var dataCrtYm = item[i].getElementsByTagName("dataCrtYm")[0].innerHTML;
							
							if(dataCrtYm > standard){
								standard = dataCrtYm; //최대값 저장
							}
							
							//가장 최신 자료를 배열에 저장
							for(var j=0;j<item.length;j++){
								if(item[j].getElementsByTagName("dataCrtYm")[0].innerHTML == standard && item[j].getElementsByTagName("bzowrRgstNo")[0].innerHTML.substring(0,6) == bzowrRgstNo){
									array[check] = item[j];
								}
							}
							
						}else if(bzowrRgstNo != bzowrRgstNoPrev){ //사업자 등록번호가 바뀌면 구분용 변수 증가
							check++;
						}
						
					}
					
				}
				
				result = array;
			},
			error: function(x,o,e){

			}
		})
		.done(function(){
			
			$("#resultTable > tbody").empty();
			
			for(var i=0;i<result.length;i++){
				
				if(result[i] != undefined){
					
					var wkplnm = result[i].getElementsByTagName("wkplNm")[0].innerHTML; //사업장명
					var wkplroadnmdtladdr = result[i].getElementsByTagName("wkplRoadNmDtlAddr")[0].innerHTML; //사업장 주소
					var seq = result[i].getElementsByTagName("seq")[0].innerHTML; //식별번호
					
					$("#resultTable").append(
						"<tbody style='margin-top:25px'><tr>" +	
							"<td width='50px'>" + (i+1) + "</td>" +
							"<td width='350px'>" + wkplnm + "</td>" +
							"<td width='300px'>" + wkplroadnmdtladdr + "</td>" +
							"<td width='150px'>" + "<input type='button' id='showDetail"+(i+1)+"' value='보기'>" + "</th>" +
							"<td width='100px'>" + "<input type='button' id='showWkpl"+(i+1)+"' value='표시'>" + "</th>" +
							"<td width='100px' style='display:none'>" + seq + "</td>" +
						"</tr></tbody>"
					)
					
				}
				
			};
			
			//사업장 상세 정보 함수 호출(@param:배열의 길이) -> 버튼 갯수가 배열 길이 만큼 생성되어 있음.
			showDetail(result.length);
			
			//지도에 위치 표시 함수 호출
			showWkpl(result.length);
			
		});
		
	};
	
	//사업장 상세 정보 조회
	function getDetailInfoSearch(seq){
		
		$.ajax({
			url: "getDetailInfoSearch" + "/" + seq,
			type: "get",
			dataType: "html",
			success: function(data){
				$("#result4").text(data);
			},
			error: function(x,o,e){

			}
		});
		
	};
	
	//지도에 위치 표시
	function showPlace(wkplNm, wkplRoadNmDtlAddr) {
		
		var wkplRoadNmDtlAddr_substr = wkplRoadNmDtlAddr.substr(0,2);
		
		daum.maps.load(function(){
			
			var infowindow = new daum.maps.InfoWindow({zIndex:1});
			
			var container = document.getElementById('map');
			var options = {
				center: new daum.maps.LatLng(37.335887, 126.584063),
				level: 3
			};

			var map = new daum.maps.Map(container, options);
			
			// 장소 검색 객체를 생성합니다
			var ps = new daum.maps.services.Places(); 

			// 키워드로 장소를 검색합니다
			ps.keywordSearch(wkplNm + " " + wkplRoadNmDtlAddr_substr, placesSearchCB); 

			// 키워드 검색 완료 시 호출되는 콜백함수 입니다
			function placesSearchCB (data, status, pagination) {
				
			    if (status === daum.maps.services.Status.OK) {

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        var bounds = new daum.maps.LatLngBounds();

			        for (var i=0; i<data.length; i++) {
			            displayMarker(data[i]);    
			            bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
			        }       

			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			        map.setBounds(bounds);
			        
			    } else {
			    	
			    	ps.keywordSearch($("#value").val() + " " + wkplRoadNmDtlAddr, placesSearchCB_2); 
			    	
			    	function placesSearchCB_2 (data, status, pagination) {
						
					    if (status === daum.maps.services.Status.OK) {

					        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
					        // LatLngBounds 객체에 좌표를 추가합니다
					        var bounds = new daum.maps.LatLngBounds();

					        for (var i=0; i<data.length; i++) {
					            displayMarker(data[i]);    
					            bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
					        }       

					        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
					        map.setBounds(bounds);
					        
					    }else{
					    	alert("결과가 없습니다.")
					    	basicMap();
					    }
			    	}
			    	
			    }
			}

			// 지도에 마커를 표시하는 함수입니다
			function displayMarker(place) {
			    
			    // 마커를 생성하고 지도에 표시합니다
			    var marker = new daum.maps.Marker({
			        map: map,
			        position: new daum.maps.LatLng(place.y, place.x) 
			    });

			    // 마커에 클릭이벤트를 등록합니다
			    daum.maps.event.addListener(marker, 'click', function() {
			        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
			        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
			        infowindow.open(map, marker);
			    });
			}
			
		})
		
	}
	
	//상세 정보 보기 버튼 클릭시 상세 정보 표시
	function showDetail(length){
		
		$(function(){
			for(var i=1;i<length+1;i++){
				$("#showDetail" + i).click(function(){
					var seq = $(this)[0].parentNode.parentNode.children[5].innerHTML;
					getDetailInfoSearch(seq);
				})
			}
		})
		
	}
	
	//지도에 위치 표시
	function showWkpl(length){
		
		$(function(){
			for(var i=1;i<length+1;i++){
				$("#showWkpl" + i).click(function(){
					var wkplNm = $(this)[0].parentNode.parentNode.children[1].innerHTML;
					var wkplRoadNmDtlAddr = $(this)[0].parentNode.parentNode.children[2].innerHTML;
					showPlace(wkplNm, wkplRoadNmDtlAddr);
				})
			}
		})
		
	}
	
	function basicMap(){
		require(["daum"] ,function(daum){
			
			daum.maps.load(function(){
				
				var container = document.getElementById('map');
				var options = {
					center: new daum.maps.LatLng(37.335887, 126.584063),
					level: 12
				};

				var map = new daum.maps.Map(container, options);
				
			})
			
		})
	}
	
	function reset(){
		basicMap();
		$("#resultTable > tbody").empty();
		$("#result4").empty();
		$("#value").val("");
	}
	
	return {
		getBassInfoSearch: getBassInfoSearch,
		getDetailInfoSearch: getDetailInfoSearch,
		basicMap: basicMap,
		reset: reset
	};
	
})
