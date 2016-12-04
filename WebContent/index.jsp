<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE >
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">
    <meta content="email=no" name="format-detection"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
<head>
<script src="angular.min.js"></script>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="bootstrap-3.3.5-dist/js/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<title>Insert title here</title>
<script>
	var app = angular.module("myApp", []);
	app.config(function($httpProvider) {
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
		$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

		// Override $http service's default transformRequest
		$httpProvider.defaults.transformRequest = [function(data) {
			/**
			 * The workhorse; converts an object to x-www-form-urlencoded serialization.
			 * @param {Object} obj
			 * @return {String}
			 */
			var param = function(obj) {
				var query = '';
				var name, value, fullSubName, subName, subValue, innerObj, i;

				for (name in obj) {
					value = obj[name];

					if (value instanceof Array) {
						for (i = 0; i < value.length; ++i) {
							subValue = value[i];
							fullSubName = name + '[' + i + ']';
							innerObj = {};
							innerObj[fullSubName] = subValue;
							query += param(innerObj) + '&';
						}
					} else if (value instanceof Object) {
						for (subName in value) {
							subValue = value[subName];
							fullSubName = name + '[' + subName + ']';
							innerObj = {};
							innerObj[fullSubName] = subValue;
							query += param(innerObj) + '&';
						}
					} else if (value !== undefined && value !== null) {
						query += encodeURIComponent(name) + '='
								+ encodeURIComponent(value) + '&';
					}
				}

				return query.length ? query.substr(0, query.length - 1) : query;
			};

			return angular.isObject(data) && String(data) !== '[object File]'
					? param(data)
					: data;
		}];
	} );
	app.controller("myCtrl", function($scope, $http,$interval) {
		$http.get("SendMsgAction").success(function(response) {
			var ss = response;
			$scope.data = $.parseJSON(eval(response))
			//{'account':'123123','name':'王铁涛'}
			//$.parseJSON("''"+response+"'");
			//console.log($scope.data);//{'account':'123123','name':'王铁涛'}

		});
		$scope.currentObject = function(currentObject){
			$scope.currentChat = currentObject;
		}
		$scope.formatDate = function(str){
			console.log(str)
			var d = new Date(str);
			//console.log(d)
			return d;
		}
		$scope.send = function(item) {
			item =  $scope.currentChat;
			console.log(item)
			/* $.ajax({
				type : "post",
				url : "SendMsgAction",
				data : {
					msg : item.msg,
					account : item.account
				},
				dataType : "text",
				success : function(data) {
					$scope.recorad =  $.parseJSON(data);
					console.log( $.parseJSON(data));
				//	$("#divShow").html(data);
				}
			}); */
			 $http({  
			    method:'post',  
			    url:'SendMsgAction',  
			    data:{"msg":item.msg,"account":item.account}  
			}).success(function(data){  
				$scope.recorad =  data;
				
			    console.log(data);  
			})   
		}
		  $interval(function(){
			if( $scope.currentChat){
				$http({  
				    method:'post',  
				    url:'SendMsgAction',  
				    data:{"account":$scope.currentChat.account}  
				}).success(function(data){  
					$scope.recorad =  data;
					
				    console.log(data);  
				})   
			}
		},1000);  
	});
</script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
	<form class="form-inline" style="display:none">
		<div ng-repeat="item in data  track by $index">
			<div class="form-group">
				<label for="exampleInputName2">Name</label> <input type="text"
					class="form-control" ng-disabled="true" value="{{item.name}}"
					placeholder="">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail2">msg</label> <input type="text"
					class="form-control" ng-model="item.msg" placeholder="发送消息">
			</div>
			<button type="submit" class="btn btn-default" ng-click="send(item )">Send</button>
		</div>
	</form>
	<div class="panel panel-default">
		<div class="panel-heading" style="    text-align: center;">在线客服</div>
		<div class="panel-body">
			<dl class="col-md-2">
				<dt>在线用户</dt>
				<dd style="margin-left: 20;">
					<ol>
					<li  ng-repeat="item in data  track by $index" style="    cursor: pointer;"><a ng-click="currentObject(item)" ><span>{{item.name}}</span>
					<span class="glyphicon glyphicon-envelope" style="    margin-left: 10px;"> <span style="color:red;">未读消息 1 </span></span></a></li>
					</ol>
				</dd>
			</dl>
			<div class="col-md-6">
				<form role="form">
					<div class="form-group">
						<label for="exampleInputEmail1">聊天记录：{{currentChat.name}}</label>
    <div class=" row"  ng-repeat="item in recorad "  style="    height: 29px; padding: 0 0px;    line-height: 29px;">
    <div style="    clear: both;">  
     <div ng-if="item.guest"  class="pull-left"><span>{{formatDate( item.date) | date:'yyyy-MM-dd HH:mm:ss'}} </span> <span style="    margin-left: 20px;">{{item.guest }}</span></div>
        <div ng-if="item.me" class="pull-right" ><span>{{formatDate( item.date) | date:'yyyy-MM-dd HH:mm:ss'}}</span> <span style="    margin-left: 20px;">{{item.me }}</span> </div> 
        </div>
    </div>
						<textarea class="form-control" rows="3" ng-model="currentChat.msg"></textarea>
					</div>
					<button type="submit" class="btn btn-default pull-right" ng-click="send()">发送</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>