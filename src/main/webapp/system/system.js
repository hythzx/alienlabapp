/**
 * Created by 橘 on 2016/8/5.
 */
(function(){
    'use strict'
    var alienlabapp=angular.module("alienlab",["alienlab.user","alienlab.menu","alienlab.role","ngMaterial"]);
    var userapp=angular.module("alienlab.user",["alienlab.role"]);
    var menuapp=angular.module("alienlab.menu",["alienlab.menu"]);
    var roleapp=angular.module("alienlab.role",[]);

    userapp.factory("userinstance",function(){return {}});
    userapp.controller("usermanageController",["$scope","userservice","$uibModal","userinstance",function($scope,userservice,$uibModal,userinstance){
        $scope.title = "用户管理";
        $scope.users = [];
        $scope.shops = [];


        userservice.getusers(function(data){
            console.log("users",data);
            if(data == "" || data == null) {

            } else {
                $scope.users = data;
                for(var i=0; i<$scope.users.length; i++) {
                    for(var j=0; j<$scope.shops.length; j++) {
                        if($scope.users[i].purview == $scope.shops[j].shopNo) {
                            $scope.users[i].purview = $scope.shops[j].shopName;
                        }
                    }

                }
            }
        });


        $scope.deleteuser = function deleteuser(userid) {
            console.log("userid",userid);
            var promitInstance = $uibModal.open({
                animation: true,
                templateUrl: 'system/common/promit.html',
                controller:function($scope,$uibModalInstance){
                    $scope.title="操作确认";
                    $scope.text="确认执行该用户的删除操作吗？";
                    $scope.cancel=function(){
                        $uibModalInstance.dismiss('cancel');
                    }
                    $scope.save=function(){
                        $uibModalInstance.close("ok");
                    }
                },
                backdrop:true
            });

            promitInstance.result.then(function(){
                userservice.deleteuser(userid,function(data){
                    if(data.result > 0){
                        for(var i=0;i<$scope.users.length;i++){
                            if($scope.users[i].userid == userid) {
                                //刷新客户页面
                                $scope.users.splice(i,1);
                                break;
                            }
                        }
                    }
                });
            });
        }

        $scope.selectall = function selectall() {
            for(var i=0; i<$scope.users.length; i++) {
                if($scope.$isselectall) {
                    $scope.users[i].$isselected = true;
                } else {
                    $scope.users[i].$isselected = !$scope.users[i].$isselected;
                }
            }
        }

        $scope.getSelects = function() {
            var selects = [];
            for(var i=0; i<$scope.users.length; i++) {
                if($scope.users[i].$isselected) {
                    selects.push($scope.users[i]);
                }
            }
            return selects;
        }

        $scope.deleteusers = function deleteusers() {
            if($scope.getSelects().length == 0) {
                return;
            }
            var promitInstance = $uibModal.open({
                animation: true,
                templateUrl: 'system/common/promit.html',
                controller:function($scope,$uibModalInstance){
                    $scope.title="操作确认";
                    $scope.text="确认对所选用户执行删除操作吗？";
                    $scope.cancel=function(){
                        $uibModalInstance.dismiss('cancel');
                    }
                    $scope.save=function(){
                        $uibModalInstance.close("ok");
                    }
                },
                backdrop:true
            });

            promitInstance.result.then(function(){
                var bjs = [];
                for(var i=0; i<$scope.users.length; i++) {
                    if($scope.users[i].$isselected) {
                        userservice.deleteuser($scope.users[i].userid,function(data,userid){
                            if(data.result > 0) {
                                bjs.push(userid);
                                for(var k=0; k<$scope.users.length; k++) {
                                    for(var j=0; j<bjs.length; j++) {
                                        if($scope.users[k].userid == bjs[j]) {
                                            $scope.users.splice(k,1);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }

            });
        }

        $scope.searchuser = function searchuser(searchkey) {
            console.log("searchkey",searchkey);
            userservice.searchUser(searchkey,function(data) {
                if(data == "" || data == null) {

                } else {
                    $scope.users = data;
                }
            });
        }

        $scope.adduser = function adduser() {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'system/addUser.html',
                controller: 'addUserController',
                bindToController: true,
                size: "lg",
                backdrop: false
            });

            modalInstance.result.then(function(data) {
                //添加保存成功
                if(data.result > 0) {
                    var user = data.data;
                    for(var i=0; i<$scope.shops.length; i++) {
                        if(user.purview == $scope.shops[i].shopNo) {
                            user.purview = $scope.shops[i].shopName;
                        }
                    }
                    var userdata = {
                        userid:user.userid,
                        loginname:user.loginname,
                        password:user.password,
                        username:user.username,
                        createtime:user.createtime,
                        lastlogin:user.lastlogin,
                        purview:user.purview,
                        status:user.status
                    }
                    $scope.users.push(userdata);

                } else {

                }
            },function(flag) {
                if(flag.indexOf("back") >= 0) {
                    return false;
                }
            });
        }

        $scope.updateuser = function updateuser(user) {
            userinstance.modify = user;
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'unail/user/addUser.html',
                controller: 'modifyUserController',
                bindToController: true,
                size: "lg",
                backdrop: false
            });

            modalInstance.result.then(function(data) {
                if(data.result >0) {

                } else {

                }
            }, function(flag) {
                if(flag.indexOf("back") >= 0) {
                    return false;
                }
            });
        }

    }]);

    userapp.controller("addUserController",["$scope","userservice","$uibModalInstance","userinstance",function($scope,userservice,$uibModalInstance,userinstance){
        $scope.ModTitle = "添加用户";
        $scope.shops = [];
        $scope.shops = userinstance.shops;

        $scope.save = function save(userform) {
            userservice.addUser($scope.form,function(data) {
                console.log(data);
                if(data.result > 0) {
                    $uibModalInstance.close(data);

                } else {
                    $scope.error = {
                        haserror: true,
                        errormsg: "添加失败，您可以再试一次！"
                    }
                }
            });
        }

        $scope.cancel = function cancel(flag){
            $uibModalInstance.dismiss('cancel');
        }

    }]);

    userapp.controller("modifyUserController",["$scope","userservice","$uibModalInstance","userinstance",function($scope,userservice,$uibModalInstance,userinstance){
        $scope.ModTitle = "修改用户";
        $scope.shops = [];
        $scope.shops = userinstance.shops;
        $scope.form = userinstance.modify;

        $scope.save = function save(userform) {
            if($scope.form.userstatus == "正常") {
                $scope.form.userstatus = "1";
            } else if($scope.form.userstatus == "异常") {
                $scope.form.userstatus = "0";
            }
            for(var i=0; i<$scope.shops.length; i++) {
                if($scope.form.purview == $scope.shops[i].shopName) {
                    $scope.form.purview = $scope.shops[i].shopNo;
                }
            }
            $scope.form.createtime = userinstance.modify.createtime;
            $scope.form.lastlogin = userinstance.modify.lastlogin;
            userservice.updateUser($scope.form,function(data) {
                if(data.result >0) {
                    for(var i=0; i<$scope.shops.length; i++) {
                        if($scope.form.purview == $scope.shops[i].shopNo) {
                            $scope.form.purview = $scope.shops[i].shopName;
                        }
                    }
                    $uibModalInstance.close(data);
                } else {
                    $scope.error = {
                        haserror: true,
                        errormsg : "修改失败，您可以再试一次！"
                    }
                }
            });
        }

        $scope.cancel = function cancel(flag){
            $uibModalInstance.dismiss('cancel');
        }
    }]);

    userapp.service("userservice",function userservice($http){
        this.getusers = function(callback){
            $http({
                url:'system/user/users/',
                method:"GET"
            }).then(function(response){
                callback(response.data);
            });
        }

        /*		this.getroles = function(callback) {
         $http({
         url:'user/roles/',
         method:"GET"
         }).then(function(response){
         callback(response.data);
         });
         }*/

        this.deleteuser = function(userid,callback) {
            $http({
                url:'system/user/delete/'+userid,
                method:'DELETE'
            }).then(function(response) {
                console.log("user/delete",response);
                callback(response.data,userid);
            });
        }

        this.searchUser = function(searchkey,callback) {
            if(searchkey == "" || searchkey == null) {
                var url = 'system/user/users'
            }
            var url = 'system/user/users/'+searchkey;
            $http({
                url:url,
                method:"GET"
            }).then(function(response) {
                callback(response.data);
            });
        }

        this.addUser = function(user,callback) {
            $http({
                url:'system/user/add',
                method:'POST',
                data:user
            }).then(function(response) {
                callback(response.data);

            },function(response){

            });
        }

        this.updateUser = function(user,callback) {
            $http({
                url:'system/user/update',
                method:'POST',
                data:user
            }).then(function(response){
                callback(response.data);

            },function(response){

            });
        }

    });

})();