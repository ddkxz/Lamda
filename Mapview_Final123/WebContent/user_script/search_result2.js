var app = angular.module('map_app', []); // angular module 명시 - 
/*angular module 이름을 명시한다. 
모듈의 이름을 html파일(search_result.js파일을 src한 파일에서만) 안에서 찾는다.
거기서부터 앵귤러코딩(search_result.js파일에 적힌)이 적용된다. 
[]표시는 알아두기만 해라. 인젝션할 directive를 적어두는 영역이다. 여기에 적어둔 directive는 html파일안에서 사용가능하다.
우리는 directive.js파일까지 만들어서 사용할 필요가 없다.*/

app.controller("mainCtrl", function($scope) {
/*	 모듈 안에서 다시 영역이 controller이름을 따라서 구분된다.
	 아래의 코딩들은 모두 해당 controller이름이 적힌 html파일영역안에서만 작동한다.
	 그러니, (search_result.js파일을 src한) html파일안에서 mainCtrl영역이 어디인지 확인해라.
	 그 영역을 js파일안에서 이용할 때는 $scope이라고 부른다.*/

    $scope.sendPost = function() {
    /*	scope영역, 즉 html파일안에 있는 mainCtrl영역에 sendPost라는 것을 찾아보아라. 
    	 찾아보면 이렇게 되어있다. ng-click="sendPost()" 
    	 html파일안에서 앵귤러적용을 위한 용어로 늘 ng-가 사용된다. 
    	 해당요소를 클릭하면, 앵귤러코딩안에 있는 sendPost 함수를 콜한다.*/
    	
    	//d3로 그린 차트의 초기화
    	d3.selectAll("rect").remove();
    	d3.selectAll("text").remove();
    	d3.selectAll("g").remove();
    	// 여기서부턴 jquery다.
    	$("#options").animate({ left:"-=330px" }); 
		$("#option_button").animate({ left:"-=310px"});
		$("#option_button").text("Show Options");
		
		$.ajax({	//이것도 jquery에서 사용하는 ajax다. 앵귤러 ajax는 따로 있는데, 지금은 이게 더 보기쉬운 코딩이다.
			url : "search_glass.jsp",
			type : "post",
			data :{ fromTime: $('#timepicker1').val(),	//이 데이터들을 jsp파일에 파라미터로 전송한다. post방식으로.(get방식과 post방식의 차이를 잠깐 살펴두라). 
	                 toTime: $('#timepicker2').val(),
	                 recipe: $('#recipeSelections > option:selected').val(),	// 선택된 옵션이 하나일 경우에는 이렇게 밸류값을 전송한다.
	                 eqp_id: $("#eqp_idSelections > option:selected").map(function(){ return this.value; }).toArray().join(","),	//선택옵션이 다중허용일 경우에는 ,를 붙여가면서 묶음으로 보낸다.
	                 defect_code: $("#defect_codeSelections > option:selected").map(function(){ return this.value; }).toArray().join(",") },
			dataType : "json",	// jsp파일에서 out.println한 것을 받아올 때, json형식으로 인식하라는 뜻.
			success : function(data) { //angular 코딩으로 table내의 리스트를 만든다. jsp가 정상작동하여 데이터가 리턴되었다면, 그것을 아래코딩으로 요리한다.
			
				$scope.glass_lists=data;
				/*여기는 다시 angular사용시작. 리턴받은 data를 glass_lists라는 앵귤러변수에 넣어라.
				그 변수를 html에서 찾아보면, ng-repeat="glass_list in glass_lists
				여러 개의 row로 되어있는 data속에서 하나의 row씩 꺼내어 반복적으로 테이블row를 만드는 코딩이다.*/

  				$scope.$apply();	//jquery코딩범위안에서 angular코딩을 했을 때는 이렇게 앵귤러를 apply시켜달라는 코딩을 넣어주어야 한다.
			}
		});			
    };
    
    $scope.sortColumn = 'glass_id'; //default 정렬기준 
	$scope.reverse = false; //default 오름차순
	/*html에서 sortColumn을 찾아보라. ng-repeat="glass_list in glass_lists | orderBy: sortColumn: reverse"
	glass_list를 반복적으로 tr로 만든 후에, glass_id를 기준으로 정렬하라는 뜻이다.
	그러나 html에서 여러개의 정렬기준을 사용자가 사용할 수 있도록 만들었다.
	예를 들면, ng-click="sortColumn='lot_id'; reverse=!reverse" 해당요소를 클릭(ng-click)하면 기준은 lot_id가 되고, 정렬순서는 오름-내림으로 클릭시마다 바뀐다.
	reverse=!reverse는 토글기능을 갖게하는 코딩.*/
	
    $scope.sendDefect = function(e, glass_key, panel_count) {
    	/* html에서 sendDefect함수가 걸려있는 tr을 찾아보라. ng-click="sendDefect(glass_list.glass_id, glass_list.panel_count)" 
    	 함수인자는 glass_id, panel_count이다. 클릭된 glass_id를 알아야 그 안에 속한 패널과 defect를 찾아낼 수 있다.
    	 panel_count를 알아야 jsp파일안에서 panel갯수만큼 panel포지션들을 출력시킬 수 있다.(물론 다른방법도 있다. 그러나 이것이 가장 편하다)
    	 tr을 클릭하면 아래내용이 실행된다.*/
    	
    	if ($(e.currentTarget).hasClass("gClass")){ // 기존에 선택안 한 tr	
    	   	
	    	$.ajax({
				url : "search_defect3.jsp",
				type : "post",
				data :{ glass_id: glass_key,
		                 defect_code: $("#key_defect_code").text(),
		                 recipe: $("#key_recipe").text(),
		                 panel_count: panel_count
				},
				dataType : "json", //success시 반환되는 데이터의 타입
				success : function(data) { //angular 코딩으로 table내의 리스트를 만든다.
				/*	반환된 data는 두가지 result를 담는다. 
					result1은 defect list 테이블에 표시할 칼럼들, result2는 패널포지션과 크기및 아이디. 아이디는 나중에 패널한가운데 id끝 세자리출력할 때 사용할 것이다.*/
					
					if ($scope.defect_lists==null || $scope.defect_lists=="") $scope.defect_lists=data.result1; //아직 defect 테이블에 아무데이터도 없을 때 
					else { //기존의 데이터가 defect list 테이블을 채우고 있을 경우.
						dataset = JSON.stringify(data.result1)+JSON.stringify($scope.defect_lists);		
						/*이미 defect테이블에 데이터가 있을 때는
						 기존테이터($scope.defect_lists) + 새 데이터(data.result1) 합치기.
						 stringify를 하는 이유는 아래의 replace작업을 하기 위해서다. json데이터는 replace안 되고, string만 된다.
						 replace를 하는 이유는 하나의 json으로 묶기 위해서다.
						 만일 이런 작업을 안 하고, 그냥 기존json과 새json을 합치면, []+[]가 되어서 두개의 json이 된다.
						 따라서 첫번째 json의 ]와 두번째 json의 [를 함께 ,로 바꾼다. 그러면 [ , ] 이렇게 하나의 json이 된다.
						 그래야 defect list에 기존과 합체된 데이터가 표시될 수 있고, 함께 정렬할 수 있다.*/
						$scope.defect_lists = JSON.parse(dataset.replace("][",","));
					}					
					var data1=$scope.defect_lists;
					//alert(JSON.stringify(data1));
	 				$scope.$apply(); 			
	 				d3panel(data1, data.result2);	 				
				}
			});	
	    	$(e.currentTarget).removeClass("gClass").addClass("gselectClass"); // click에 따른 색 class변화
	    	
    	}else { //이미 선택된 tr일 경우
    		$(e.currentTarget).removeClass("gselectClass").addClass("gClass");
    		$scope.defect_lists = $scope.defect_lists.filter( function(el){ //데이터 필터링
    			return el.p_glass_id != glass_key; //선택된 glass_key를 제외한 나머지로 데이터 재구성.
    		});
    		//여기서 d3도 remove해야한다.
    		d3remove(glass_key);
    	}    	
    };    
});

function d3panel(data1, data2){
	/* data1은 defect의 좌표를 갖고 있다. 그래서 점(circle)을 찍을 때 이용할 데이터다.
	 data2는 result2를 받아온 것으로서, 패널의 좌표와 크기를 갖고 있으므로, 패널사각형을 그릴 때 사용된다.*/
	d3.selectAll("div.tooltip").remove();
	d3.selectAll("g").remove();
	var div = d3.select("body").append("div")	
    .attr("class", "tooltip")				
    .style("opacity", 0.9);
	
	var xmin = Math.abs(d3.min(data2, function(d){return d.pnl_x_pos;}));
	var ymin = Math.abs(d3.min(data2, function(d){return d.pnl_y_pos;}));	
	var primaryX = Math.abs(d3.min(data1, function(d){return d.coord_x;}));
	var primaryY = Math.abs(d3.min(data1, function(d){return d.coord_y;}));
	 /*원 데이터의 포지션은 -값도 나온다.
	 그런데, 원장 glass의 포지션은 주어지지 않고, 그냥 크기만 주어져 있다.
	 그래서 원장의 포지션을 그냥 0부터, 길이만큼 준다.
	 그러면, 패널의 포지션도 0부터 시작하도록 옮겨주어야 한다.
	 그래서 패널포지션의 가장 작은 -값을 찾고(d3.min(data2, function(d){return d.pnl_x_pos;})) 
	 그 다음 그것의 절대값을 구한다. 나중에 패널포지션의 원래 데이터에다 이 절대값을 더하면, 가장 작았던 -값은 0이 된다.
	 나머지 포지션도 절대값을 더하면, 패널들의 모든 포지션이 + 영역에서 그려진다.  	*/

	var colorscale = d3.scale.category10();
	
	      var xscale = d3.scale.linear();
	      xscale.domain([0, 2630000]);	// 원장의 x크기
	      xscale.range([0, 718]); // Pixel space //화면의 x 픽셀크기는 적당한 크기로 맘대로 정한다.
	      
	      var yscale = d3.scale.linear();
	      yscale.domain([0, 2200000]);
	      yscale.range([0, 600]);	// 화면의 y픽셀 크기는 비례식으로 구한다. 원장x : 원장y = 픽셀x : 픽셀y
	      
	      var g = d3.select("#mapView > svg").selectAll("rect").data(data2).enter()
	      g.append("rect")
	        .attr("class","rect")
		  	.attr("x", function (d){return xscale(d.pnl_x_pos+xmin);})	//여기서 + xmin을 하는 이유는 모든 좌표를 0 이상으로 옮기기 위해서다.
		  	.attr("y", function (d){return yscale(d.pnl_y_pos+ymin);})
		  	.attr("width", function (d){return xscale(d.pnl_x_size);})
		  	.attr("height", function (d){return yscale(d.pnl_y_size);});
	      g.append("text")
		  	.attr("x", function (d){return xscale(d.pnl_x_pos+xmin+10000);})
		  	.attr("y", function (d){return yscale(d.pnl_y_pos+ymin+50000);})
		  	.text(function (d){return d.pnl_id.slice(11,14);}) //id 끝 세자리 출력
		  	.attr("fill", "gray");
	      
      d3.select("#mapView > svg").selectAll("rect").data(data2).exit().remove();	//앞에서 data.enter()를 해줬으면, 나중엔 exit()를 해주어야 한다.
	    
	  d3.select("#mapView > svg").selectAll("circle").data(data1).enter().append("circle")
	    .attr("id", function (d){return d.p_glass_id; })
	    .attr("cx", function (d){return xscale(d.coord_x+primaryX);})	//패널의 좌료를 + 이동시켰으므로, defect좌표도 + 이동한다.
	    .attr("cy", function (d){return yscale(d.coord_y+primaryY);})
	  	.attr("r", 2)
	    .attr("fill", function (d){return colorscale(d.defect_code);})
	    .on("mouseover", function(d) {		
            div.transition()		
                .duration(200)		
                .style("opacity", .9);		
            div	.html("CODE :<b>"+d.defect_code + "</b> | "  + "ID :<b>"+d.defect_id+"</b>")	
                .style("left", (d3.event.pageX+10) + "px")		
                .style("top", (d3.event.pageY - 28) + "px");
            d3.select(this).classed("circle",true);
            })					
        .on("mouseout", function(d) {		
            div.transition()		
                .duration(500)		
                .style("opacity", 0);
            d3.select(this).classed("circle",false);
        });
	    
	 d3.select("#mapView > svg").selectAll("circle").data(data1).exit().remove();	    
	    
}

function d3remove(glass_id){
//	var removed = "#"+glass_id;
//	$("#mapView > svg > circle").children(removed).remove();
	d3.selectAll("#id" + glass_id).remove();
}

app.controller("defectCtrl", function($scope) {	//defectCtrl영역 안에서만 작동하는 코딩.
    $scope.sortField = 'p_glass_id'; //default 정렬기준 
	$scope.reverse = false; //default 오름차순
	});

//아래는 jquery코딩이다.
$("td.sort").click(function(){
	$(this).children("i").toggleClass("icono-caretDownSquare"); // 정렬차순변동과 함께 아이콘도 바뀐다.
});	