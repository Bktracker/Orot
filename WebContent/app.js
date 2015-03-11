/**
 * 
 */
(function() {
	var app = angular.module('Orot', []);
//	this conroler allow login and manaegs sessions
	app.controller('LoginController',['$http',function($http){
		this.username="";
		this.password="";

		$http({

			method: 'GET',
			url: '/Orot/checkSession',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success( function(data) {
			if (data == "notFound") {
			} else {
				window.location.href="main.html";
			}	
		}); 

		this.login =function() {
			if (angular.isUndefined(this.username)  ||  angular.isUndefined(this.password) || this.password.trim =="" ) {
				$("#notFound").hide();
				$("#empty").show();
				return ;	
			}


			$http({
				method: 'POST',//acording to angular $http doc sends vie json by defautlt
				url: "/Orot/login",
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: {username: this.username, password: this.password},
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success( function(data) {
				if (data == "notFound") {
					$("#empty").hide();
					$("#notFound").show();
				} else {
					window.location.href="main.html";
				}
			});

		}; 
	}]);
	app.controller('RegisterController',['$http',function($http){
		this.username="";
		this.password="";
		this.nickname="";
		this.description="";
		this.picture="";
		this.email="";

		this.register =function() {
			if(angular.isUndefined(this.username)  ||  angular.isUndefined(this.password) || angular.isUndefined(this.nickname) || this.username.trim =="" || this.password.trim =="" || this.nickname.trim =="") {
				$("#danger").hide();
				$("#success").hide();
				$("#missing").show();
				return ;
			}

				$http({
					method: 'POST',
					url: "/Orot/register",
					transformRequest: function(obj) {
						var str = [];
						for(var p in obj)
							str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data: {username: this.username, password: this.password, nickname: this.nickname, description: this.description, email: this.email, picture:this.picture },
					headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				}).success( function(data) {
					if (data == "OK") 
					{
						localStorage.username=$("#txtUName").val().trim();
						localStorage.usernick=$("#txtUNick").val().trim();
						localStorage.userdesc=$("#txtSDescr").val().trim();

						$("#danger").hide();
						$("#missing").hide();
						$("#50limit").hide();
						$("#success").show();
						location.href="main.html";
					} else {
						$("#success").hide();
						$("#missing").hide();
						$("#50limit").hide();
						$("#danger").show();
					}
				});

		};
	}]);

//	this contorl get the user data and allows logout
	app.controller('NavBarController',['$http',function($http){
	//	this.activeusername=localStorage.username;
	//	this.activeuserpic=localStorage.userpic;
	//	this.activeusernick=localStorage.usernick;
	//	this.activeuserdesc=localStorage.userdesc;
		this.activeuserpic="images/defaultPic.png";
		
		this.logout =function() {

			$http({

				method: 'GET',
				url: '/Orot/logout',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success( function(data) {
				$location.path('index.html')
			});

			localStorage.username="";
			localStorage.userpic="";
			localStorage.usernick="";
			localStorage.userdesc="";
		};

		$http({

			method: 'GET',
			url: '/Orot/checkSession',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success( function(data) {
			if (data == "notFound") {
				window.location.href="index.html";
			}
		}); 

	}]);
	
	app.controller('MainController',['$http','$scope',function($http,$scope){
		this.projects={};
		$http({
			method: 'GET',
			url: '/Orot/project',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success( function(data) {
			$scope.projects = data;	
		}); 
	}]);
})();