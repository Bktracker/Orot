/**
 * 
 */
(function() {
	var app = angular.module('Orot', []);
//	this conroler allow login and manaegs sessions
	app.controller('LoginController',['$http',function($http){
		this.email="";
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
			if (angular.isUndefined(this.email)  ||  angular.isUndefined(this.password) || this.password.trim =="" ) {
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
				data: {email: this.email, password: this.password},
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
		this.email="";
		this.password="";
		this.nickname="";
		this.description="";
		this.picture="";
		

		this.uploadFile = function(files) {
			alert("hi");
		    var fd = new FormData();
		    //Take the first selected file
		    fd.append("file", files[0]);

		    $http.post(uploadUrl, fd, {
		        withCredentials: true,
		        headers: {'Content-Type': undefined },
		        transformRequest: angular.identity
		    }).success(  ).error();

		};	    
		this.register =function() {
			if(angular.isUndefined(this.email)  ||  angular.isUndefined(this.password) || angular.isUndefined(this.nickname) || this.email.trim =="" || this.password.trim =="" || this.nickname.trim =="") {
				$("#danger").hide();
				$("#success").hide();
				$("#secure").hide();
				$("#missing").show();
				return ;
			}
		
			var SecurePattern=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,20}$/;  
			if(!this.password.match(SecurePattern))   {
				$("#secure").show();
				$("#danger").hide();
				$("#success").hide();
				$("#missing").hide();
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
					data: {email: this.email, password: this.password, nickname: this.nickname, description: this.description, picture:this.picture },
					headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				}).success( function(data) {
					if (data == "OK") 
					{
						localStorage.email=$("#txtEmail").val().trim();
						localStorage.usernick=$("#txtUNick").val().trim();
						localStorage.userdesc=$("#txtDescr").val().trim();

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

			localStorage.email="";
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